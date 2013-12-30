package com.dandelion.memberandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dandelion.memberandroid.R;
import com.squareup.picasso.Picasso;

/**
 * Created by FengxiangZhu on 13-12-30.
 */
public class MemberTimelineFeedDetailFragment extends Fragment {
    public static final String FEED_MERCHANT_NAME = "feed:merchantName";
    public static final String FEED_TITLE = "feed:title";
    public static final String FEED_IMAGE_URL = "feed:imageName";
    public static final String FEED_CONTENT = "feed:content";

//    public MemberTimelineFeedDetailFragment () {
//        getActivity().getE
//        MemberTimelineFeedDetailFragment fragment = new MemberTimelineFeedDetailFragment();
//        fragment.setArguments(arguments);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity activity = getActivity();

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.fragment_member_timeline_feed_detail, container, false);

        TextView merchantNameView = (TextView) view.findViewById(R.id.text_timeline_feed_detail_merchant_name);
        TextView titleView = (TextView) view.findViewById(R.id.text_timeline_feed_detail_title);
        TextView contentView = (TextView) view.findViewById(R.id.text_timeline_feed_detail_content);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_timeline_feed_detail);
        //TODO
        Button shareButton = (Button) view.findViewById(R.id.button_timeline_share_info_detail);


        Bundle arguments = getArguments();
        merchantNameView.setText(arguments.getString(FEED_MERCHANT_NAME));
        titleView.setText(arguments.getString(FEED_TITLE));
        contentView.setText(arguments.getString(FEED_CONTENT));


        Picasso.with(activity)
                .load(arguments.getString(FEED_IMAGE_URL))
                .fit()
                .into(imageView);

        return view;
    }


}
