package com.dandelion.memberandroid.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.adapter.MemberTimelineListAdapter;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.dandelion.memberandroid.model.MemberTimelineFeedPO;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ouroc on 3/30/14.
 */
public class HistoryPostAcitvity extends FragmentActivity{

    //UI
    private ListView listView;
    private ProgressDialog mDialog;

    private MemberTimelineListAdapter memberTimelineListAdapter;


    //VALUE
    private String sid;
    private Long userId;

    private Activity activity;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        setContentView(R.layout.fragment_my_post);
        memberTimelineListAdapter = new MemberTimelineListAdapter(activity);
        listView = (ListView) activity.findViewById(R.id.my_post_list);
        listView.setAdapter(memberTimelineListAdapter);
        listView.setFastScrollEnabled(true);
        getMyPostsData();


        Button postButton = (Button) activity.findViewById(R.id.button_my_post);
        postButton.setVisibility(View.INVISIBLE);

        // Show the Up button in the action bar.
        setupActionBar();
    }





    public void getMyPostsData() {
        AccountService service = new AccountService(activity);
        sid = service.getAuthAccount().getSid();
        userId = service.getAuthAccount().getUsdId();
        Response.Listener<String> timelineListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                List<MemberTimelineFeedPO> data = new ArrayList<MemberTimelineFeedPO>();
                try {
                    JSONObject responseJson = new JSONObject(response);
                    JSONArray responseJsonArray = responseJson.getJSONArray("feedList");
                    for (int i = 0; i < responseJsonArray.length(); i++) {
                        MemberTimelineFeedPO memberTimelineFeedPO = new MemberTimelineFeedPO();
                        JSONObject feedJson = responseJsonArray.getJSONObject(i);
                        JSONObject merchantJson = feedJson.getJSONObject("merchantDetailInfoResponse");
                        memberTimelineFeedPO.setFeedimageUrl(QiNiuConstant.getImageDownloadURL(feedJson.getString("imageURL")));
                        memberTimelineFeedPO.setFeedTitle(feedJson.getString("title"));
                        memberTimelineFeedPO.setFeedContent(feedJson.getString("content"));
                        memberTimelineFeedPO.setUserId(feedJson.getLong("userId"));
                        memberTimelineFeedPO.setFeedId(feedJson.getLong("id"));
                        memberTimelineFeedPO.setMerchantId(merchantJson.getLong("merchantId"));
                        memberTimelineFeedPO.setMerchantName(merchantJson.getString("name"));
                        //TODO
//                    memberTimelineFeedPO.setMerchantTel(Long.valueOf(merchantJson.getString("phone")));
                        memberTimelineFeedPO.setMerchantAddress(merchantJson.getString("address"));
                        memberTimelineFeedPO.setMerchantEmail(merchantJson.getString("email"));
                        memberTimelineFeedPO.setMerchantAvatarUrl(QiNiuConstant.getImageDownloadURL(feedJson.getString("imageURL")));
                        memberTimelineFeedPO.setMember(true);
                        data.add(memberTimelineFeedPO);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                memberTimelineListAdapter.swapItems(data);
                showLoading(false);
            }
        };
        Response.ErrorListener timelineErrorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                showLoading(false);
                Toast.makeText(activity, R.string.dialog_network_error, Toast.LENGTH_SHORT).show();
            }
        };
        showLoading(true);
        MemberappApi.getMyPosts(sid, timelineListener, timelineErrorListener);

    }



    public void showLoading(final boolean show) {
        if (show) {
            mDialog = new ProgressDialog(activity);
            mDialog.setMessage(activity.getString(R.string.progress_loading));
            mDialog.setCancelable(false);
            mDialog.show();
        } else {
            if(mDialog != null){
                mDialog.dismiss();
            }
        }
        listView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
