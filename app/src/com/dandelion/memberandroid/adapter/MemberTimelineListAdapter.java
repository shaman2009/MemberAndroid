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
        Button button_timeline_share_info;
        Button button_timeline_get_details;
    }

    public MemberTimelineListAdapter(Context context) {
        this.context = context;
        //TODO
        notificationData = new ArrayList<MemberTimelineFeedPO>();
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
            holder.button_timeline_get_details = (Button) convertView.findViewById(R.id.timeline_get_details);
            holder.button_timeline_share_info = (Button) convertView.findViewById(R.id.timeline_share_info);
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

        holder.button_timeline_share_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(v, feedMerchantName + " - " + feedTitle + " - " + feedContent);
            }
        });

        holder.button_timeline_get_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                arguments.putString(MemberTimelineFeedDetailFragment.FEED_MERCHANT_NAME, feedMerchantName);
                arguments.putString(MemberTimelineFeedDetailFragment.FEED_TITLE, feedTitle);
                arguments.putString(MemberTimelineFeedDetailFragment.FEED_CONTENT, feedContent);
                arguments.putString(MemberTimelineFeedDetailFragment.FEED_IMAGE_URL, feedImageUrl);

                showDetails(arguments);

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

    public void swapItems(List<MemberTimelineFeedPO> data) {
        notificationData = data;
        notifyDataSetChanged();
    }

    private void showDetails(Bundle arguments) {

        Intent intent = new Intent(context, MemberTimelineFeedDetailActivity.class);
        intent.putExtras(arguments);
        context.startActivity(intent);

    }


    public void share(View view, String text) {
        String subject = "分享";
		/*
		 * Android的机制： 每次安装一个新的app的时候 app会向系统注册 我是谁我能干什么事儿 我可以提供哪些接口
		 * 然后这边这个方法就向系统请求 哪些app有ACTION_SEND这个功能，然后系统会把有这些功能的app返给我
		 * 就是点击share以后产生的界面 都是系统已经实现好的功能了
		 */
        Intent intent = new Intent(Intent.ACTION_SEND);
        // 这里传入的subject就是对应edittext里面的文本 作为点击以后新对话框的标题
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setType("text/plain");
        // 这里传入的text就是对应edittext里面的文本 对应调用传入的参数
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 启动一个新的activity 就是看到的 成功调用别的app了
        context.startActivity(Intent.createChooser(intent, subject));
    }

}
