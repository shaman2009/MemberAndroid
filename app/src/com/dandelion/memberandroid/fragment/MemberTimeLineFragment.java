package com.dandelion.memberandroid.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.activity.MemberTimelineFeedDetailActivity;
import com.dandelion.memberandroid.activity.SlidingmenuActivity;
import com.dandelion.memberandroid.adapter.MemberTimelineListAdapter;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.model.MemberTimelineFeedPO;
import com.dandelion.memberandroid.util.DeviceUtil;
import com.dandelion.memberandroid.volley.MemberappApi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FengxiangZhu on 13-12-29.
 */
public class MemberTimelineFragment extends Fragment {





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //UI
    private View mListView;
    private View mLoadingStatusView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_timeline_list, container, false);
    }

    @Override
    public void onStart() {
        initWidget();
        showProgress(true);
        getData();
        super.onStart();
    }





    private void initWidget() {
        mListView = getActivity().findViewById(R.id.list_member_timeline_feed);
        mLoadingStatusView = getActivity().findViewById(R.id.timeline_loading_progress);
    }


    public void getData() {
        try {
            MemberappApi.login("001@qq.com", "34555", WebserviceConstant.PACKAGE_NAME, DeviceUtil.getWIFIMacAddress(getActivity()), loginListener, loginErrorListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mLoadingStatusView.setVisibility(View.VISIBLE);
            mLoadingStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoadingStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mListView.setVisibility(View.VISIBLE);
            mListView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mListView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoadingStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mListView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public List<MemberTimelineFeedPO> fakeData() {
        List<MemberTimelineFeedPO> fakeNotificationData = new ArrayList<MemberTimelineFeedPO>();
        MemberTimelineFeedPO memberTimelineFeedPO1 = new MemberTimelineFeedPO();
        MemberTimelineFeedPO memberTimelineFeedPO2 = new MemberTimelineFeedPO();
        memberTimelineFeedPO1.setMerchantAvatarUrl("http://img.hb.aicdn.com/c6bdf215e18df7579beab8bff2dd30e08a45e97eba9c-iCGe36_fw658");
        memberTimelineFeedPO1.setFeedTitle("会员 10% OFF");
        memberTimelineFeedPO1.setMerchantName("自家點心");
        memberTimelineFeedPO1.setFeedContent("我以为，我就是你的天下。这一句话，有多么的失落，就曾有过多么的执着。我最后的期许，你有没有听出来？我最后的奢求，你有没有一丝心软？你就那样生生甩开我的手，远赴你所谓的江湖。你转身迈步的那一瞬，我不信你没有丝毫的留恋，我不信，江湖难道只剩下恩怨和名利，我不信，不信。\n" +
                "终于，我们针锋相对。你打不过我，可是我却下不去手。银线金针擅长远攻，近身，便可破。你记住了我的叮嘱，可是却转身用来对付我，多么讽刺。因为爱，我不惜将自己的弱点尽数告知，可是你，因为江湖，却要用这弱点亲手取我性命。");
        fakeNotificationData.add(memberTimelineFeedPO1);
        memberTimelineFeedPO2.setMerchantAvatarUrl("http://img.hb.aicdn.com/86ff3a78aa9863f2e8947da6764d9dc5e1a8eb1110f93-xMNJMr_fw236");
        memberTimelineFeedPO2.setFeedTitle("新產品 會員優先試用");
        memberTimelineFeedPO2.setMerchantName("精記蛋捲");
        memberTimelineFeedPO2.setFeedContent("当身体不收控制的撞向那截竹子，我已经心如死灰。而你，却在最后一刻，抓住了我的手。这一刻，时光停滞，我不是杀人如麻东方不败，你也不是背负恩怨的刀客，我们只不过是相爱着的两个人。不需要再解释，也不需要再掩饰，因为爱就在彼此手中，眼中有泪花翻涌，你爱我，这就够了。\n" +
                "你爱我，你舍不得我死，而我爱你，我送你一个江湖。\n" +
                "爱如繁花，只需一眼，便是天涯……");
        fakeNotificationData.add(memberTimelineFeedPO2);
        return fakeNotificationData;
    }


    Response.Listener<String> loginListener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.d(LoggerConstant.VOLLEY_REQUEST, response);
            List<MemberTimelineFeedPO> data = fakeData();
//            notifyDataSetChanged();

            final MemberTimelineListAdapter memberTimelineListAdapter = new MemberTimelineListAdapter(getActivity(), data);
            ListView listView = (ListView) getActivity().findViewById(R.id.list_member_timeline_feed);
            listView.setAdapter(memberTimelineListAdapter);

            listView.setFastScrollEnabled(true);
            showProgress(false);

        }
    };


    Response.ErrorListener loginErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
        }
    };

}