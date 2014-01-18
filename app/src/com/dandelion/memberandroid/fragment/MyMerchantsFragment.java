package com.dandelion.memberandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.adapter.MyMembersListAdapter;
import com.dandelion.memberandroid.adapter.MyPostListAdapter;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.dandelion.memberandroid.model.MemberDataResponse;
import com.dandelion.memberandroid.model.MemberListResponse;
import com.dandelion.memberandroid.model.MerchantDataResponse;
import com.dandelion.memberandroid.model.MerchantListResponse;
import com.dandelion.memberandroid.model.MyMembersPO;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by FengxiangZhu on 13-12-29.
 */
public class MyMerchantsFragment extends Fragment {

    private Gson gson;
    private MyMembersListAdapter myMembersListAdapter;


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
        getMyMerchantsData();
        gson = new Gson();
        myMembersListAdapter = new MyMembersListAdapter(getActivity());
        ListView listView = (ListView) getActivity().findViewById(R.id.my_members_list);
        listView.setAdapter(myMembersListAdapter);
        listView.setFastScrollEnabled(true);
        super.onStart();
    }
    public void getMyMerchantsData() {
        AccountService service = new AccountService(getActivity());
        sid = service.getAuthAccount().getSid();
        MemberappApi.getMyMerchants(sid, getMyMembersListener, getMyMembersErrorListener);
    }

    private Response.Listener<String> getMyMembersListener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.d(LoggerConstant.VOLLEY_REQUEST, response);
            List<MyMembersPO> data = new ArrayList<MyMembersPO>();
            List<MerchantDataResponse> merchantList =
                    gson.fromJson(response, MerchantListResponse.class).getMerchantList();
            for (MerchantDataResponse merchantDataResponse : merchantList) {
                MyMembersPO member = new MyMembersPO();
                member.setAvatarUrl(
                        QiNiuConstant.getImageDownloadURL(merchantDataResponse.getAvatarurl()));
                member.setMember(merchantDataResponse.isIsmember());
                member.setScore(merchantDataResponse.getScore());
                member.setMemberTotalTimes(merchantDataResponse.getAmountcount());
                member.setMemberTotalCosts(merchantDataResponse.getAmount());
                member.setName(merchantDataResponse.getName());
                member.setFriendId(merchantDataResponse.getFriendId());
                member.setMerchantOrMember(true);
                data.add(member);
            }
            myMembersListAdapter.swapItems(data);

        }
    };
    private Response.ErrorListener getMyMembersErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
        }
    };
}
