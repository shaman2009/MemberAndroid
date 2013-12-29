package com.dandelion.memberandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.model.MemberTimelineFeedPO;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FengxiangZhu on 13-12-29.
 */
public class MemberTimelineListAdapter extends BaseAdapter {
    private Context context;
    private List<MemberTimelineFeedPO> notificationData = new ArrayList<MemberTimelineFeedPO>();


    public MemberTimelineListAdapter(Context context) {
        this.context = context;
        //TODO
        notificationData = fakeData();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        String url = memberTimelineFeedPO.getMerchantAvatarUrl();
        String feedTitile = memberTimelineFeedPO.getFeedTitle();
        String merchantName = memberTimelineFeedPO.getMerchantName();



        holder.timeline_merchant_name.setText(merchantName);
        holder.timeline_content.setText(feedTitile);
        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context)
                .load(url)
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






    public List<MemberTimelineFeedPO> fakeData() {
        List<MemberTimelineFeedPO> fakeNotificationData = new ArrayList<MemberTimelineFeedPO>();
        MemberTimelineFeedPO memberTimelineFeedPO1 = new MemberTimelineFeedPO();
        MemberTimelineFeedPO memberTimelineFeedPO2 = new MemberTimelineFeedPO();
        memberTimelineFeedPO1.setMerchantAvatarUrl("http://img.hb.aicdn.com/111f25fd751d819e22dda0ad53ef9ac7744af58bef2d-FVGRoR_fw236");
        memberTimelineFeedPO1.setFeedTitle("会员 10% OFF");
        memberTimelineFeedPO1.setMerchantName("自家點心");
        fakeNotificationData.add(memberTimelineFeedPO1);
        memberTimelineFeedPO2.setMerchantAvatarUrl("http://img.hb.aicdn.com/86ff3a78aa9863f2e8947da6764d9dc5e1a8eb1110f93-xMNJMr_fw236");
        memberTimelineFeedPO2.setFeedTitle("新產品 會員優先試用");
        memberTimelineFeedPO2.setMerchantName("精記蛋捲");
        fakeNotificationData.add(memberTimelineFeedPO2);
        return fakeNotificationData;
    }

    static class ViewHolder {
        ImageView image;
        TextView timeline_merchant_name;
        TextView timeline_content;
    }
}
