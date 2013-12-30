package com.dandelion.memberandroid.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.activity.MemberTimelineFeedDetailActivity;
import com.dandelion.memberandroid.fragment.MemberTimelineFeedDetailFragment;
import com.dandelion.memberandroid.model.MemberTimelineFeedPO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FengxiangZhu on 13-12-29.
 */
public class MemberTimelineListAdapter extends BaseAdapter {
    private Context context;
    private List<MemberTimelineFeedPO> notificationData = new ArrayList<MemberTimelineFeedPO>();


    static class ViewHolder {
        ImageView image;
        TextView timeline_merchant_name;
        TextView timeline_content;
    }

    public MemberTimelineListAdapter(Context context, List<MemberTimelineFeedPO> data) {
        this.context = context;
        //TODO
        notificationData = data;
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_member_timeline_feed, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image_timeline_merchant_avatar);
            holder.timeline_merchant_name = (TextView) convertView.findViewById(R.id.timeline_merchant_name);
            holder.timeline_content = (TextView) convertView.findViewById(R.id.timeline_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        // Get the image URL for the current position.
        MemberTimelineFeedPO memberTimelineFeedPO = (MemberTimelineFeedPO) getItem(position);
        final String feedImageUrl = memberTimelineFeedPO.getMerchantAvatarUrl();
        final String feedTitle = memberTimelineFeedPO.getFeedTitle();
        final String feedMerchantName= memberTimelineFeedPO.getMerchantName();
        final String feedContent = memberTimelineFeedPO.getFeedContent();


        Button btn = (Button) convertView.findViewById(R.id.timeline_get_details);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                arguments.putString(MemberTimelineFeedDetailFragment.FEED_MERCHANT_NAME, feedMerchantName);
                arguments.putString(MemberTimelineFeedDetailFragment.FEED_TITLE, feedTitle);
                arguments.putString(MemberTimelineFeedDetailFragment.FEED_CONTENT, feedContent);
                arguments.putString(MemberTimelineFeedDetailFragment.FEED_IMAGE_URL, feedImageUrl);

                showDetails(arguments);
//                new AlertDialog.Builder(parent.getContext()).
//                        setView(funtion("Hello World!", parent))
//                        .show();
            }
        });


        holder.timeline_merchant_name.setText(feedMerchantName);
        holder.timeline_content.setText(feedTitle);
        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context)
                .load(feedImageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .into(holder.image);
        return convertView;
    }

    @Override
    public int getCount() {
        return notificationData.size();
    }


    @Override
    public Object getItem(int position) {
        return notificationData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private void showDetails(Bundle arguments) {

        Intent intent = new Intent(context, MemberTimelineFeedDetailActivity.class);
        intent.putExtras(arguments);
        context.startActivity(intent);

    }

    public View funtion(String title, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_member_timeline_feed, parent, false);
        return view;
    }


}
