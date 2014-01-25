package com.dandelion.memberandroid.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.adapter.NotificationListAdapter;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.dandelion.memberandroid.model.NotificationDataResponse;
import com.dandelion.memberandroid.model.NotificationMessagePO;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment{

    private Gson gson;
    private NotificationListAdapter notificationListAdapter;
    private ListView listView;
    private ProgressDialog mDialog;


    //VALUE
    private String sid;
    private Long userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_notification_list, container, false);
    }
	@Override
	public void onStart() {

        gson = new Gson();
        notificationListAdapter = new NotificationListAdapter(getActivity());
        listView = (ListView)getActivity().findViewById(R.id.notification_list);
		listView.setAdapter(notificationListAdapter);
		listView.setFastScrollEnabled(true);

        getNotificationData();
		super.onStart();
	}


    private void getNotificationData() {
        AccountService service = new AccountService(getActivity());
        sid = service.getAuthAccount().getSid();
        userId = service.getAuthAccount().getUsdId();
        Response.Listener<String> notificationListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                List<NotificationMessagePO> data = new ArrayList<NotificationMessagePO>();
                try {
                    JSONObject responseJson = new JSONObject(response);
                    JSONArray responseJsonArray = responseJson.getJSONArray("notificationList");
                    for (int i = 0; i < responseJsonArray.length(); i++) {
                        NotificationDataResponse notificationDataResponse;
                        NotificationMessagePO notificationMessagePO = new NotificationMessagePO();
                        notificationDataResponse = gson.fromJson(
                                responseJsonArray.get(i).toString(), NotificationDataResponse.class);
                        notificationMessagePO.setId(notificationDataResponse.getId());
                        notificationMessagePO.setContext(
                                notificationDataResponse.getMember().getName() + " 想要成為你的會員");
                        notificationMessagePO.setAvatarUrl(
                                QiNiuConstant.getImageDownloadURL(
                                        notificationDataResponse.getMember().getAvatarurl()));
                        notificationMessagePO.setTargetUserId(notificationDataResponse.getFromuseridfk());
                        notificationMessagePO.setRead(notificationDataResponse.getIsread());
                        data.add(notificationMessagePO);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                notificationListAdapter.swapItems(data);
                showLoading(false);
            }
        };
        Response.ErrorListener notificationErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showLoading(false);
                Toast.makeText(getActivity(), R.string.dialog_network_error, Toast.LENGTH_SHORT).show();
            }
        };
        showLoading(true);
        MemberappApi.getNotification(userId, sid, notificationListener, notificationErrorListener);
    }


    public void showLoading(final boolean show) {
        if (show) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage(getActivity().getString(R.string.progress_loading));
            mDialog.setCancelable(false);
            mDialog.show();
        } else {
            if(mDialog != null) {
                mDialog.dismiss();
            }

        }
        listView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

}