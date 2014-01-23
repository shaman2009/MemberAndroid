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
import com.dandelion.memberandroid.adapter.MyMembersListAdapter;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.dandelion.memberandroid.model.MemberDataResponse;
import com.dandelion.memberandroid.model.MemberListResponse;
import com.dandelion.memberandroid.model.MyMembersPO;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MyMembersFragment extends Fragment {
    private Gson gson;
    private MyMembersListAdapter myMembersListAdapter;
    private ProgressDialog mDialog;
    private ListView listView;

    //VALUE
    private String sid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_members, container, false);
    }

    @Override
    public void onStart() {
        getMyMembersData();
        gson = new Gson();
        myMembersListAdapter = new MyMembersListAdapter(getActivity());
        listView = (ListView) getActivity().findViewById(R.id.my_members_list);
        listView.setAdapter(myMembersListAdapter);
        listView.setFastScrollEnabled(true);
        showLoading(true);
        super.onStart();
    }

    public void getMyMembersData() {
        AccountService service = new AccountService(getActivity());
        sid = service.getAuthAccount().getSid();
        Response.Listener<String> getMyMembersListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                List<MyMembersPO> data = new ArrayList<MyMembersPO>();
                List<MemberDataResponse> memberList =
                        gson.fromJson(response, MemberListResponse.class).getMemberList();
                for (MemberDataResponse memberDataResponse : memberList) {
                    MyMembersPO member = new MyMembersPO();
                    member.setAvatarUrl(
                            QiNiuConstant.getImageDownloadURL(memberDataResponse.getAvatarurl()));
                    member.setMember(memberDataResponse.isIsmember());
                    member.setScore(memberDataResponse.getScore());
                    member.setMemberTotalTimes(memberDataResponse.getAmountcount());
                    member.setMemberTotalCosts(memberDataResponse.getAmount());
                    member.setName(memberDataResponse.getName());
                    member.setFriendId(memberDataResponse.getFriendId());
                    data.add(member);
                }
                myMembersListAdapter.swapItems(data);
                showLoading(false);
            }
        };
        Response.ErrorListener getMyMembersErrorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                showLoading(false);
                Toast.makeText(getActivity(), R.string.dialog_network_error, Toast.LENGTH_SHORT).show();
            }

        };
        MemberappApi.getMyMembers(sid, getMyMembersListener, getMyMembersErrorListener);
    }



    public void showLoading(final boolean show) {
        if (show) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage(getActivity().getString(R.string.progress_loading));
            mDialog.setCancelable(false);
            mDialog.show();
        } else {
            if(mDialog != null)
                mDialog.dismiss();
        }
        listView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
