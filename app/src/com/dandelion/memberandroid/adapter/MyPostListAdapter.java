package com.dandelion.memberandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.model.MyPostPO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FengxiangZhu on 13-12-28.
 */
public class MyPostListAdapter extends BaseAdapter {
    private Context context;
    private List<MyPostPO> notificationData = new ArrayList<MyPostPO>();


    public MyPostListAdapter(Context context) {
        this.context = context;
        //TODO
        notificationData = fakeData();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image_notification);
            holder.text = (TextView) convertView.findViewById(R.id.text_notification);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the image URL for the current position.
        MyPostPO myPostMessage = (MyPostPO) getItem(position);
        String url = myPostMessage.getImageUrl();
        holder.text.setText(myPostMessage.getPostContent());


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






    public List<MyPostPO> fakeData() {
        List<MyPostPO> fakeNotificationData = new ArrayList<MyPostPO>();
        MyPostPO myPostMessagePO1 = new MyPostPO();
        MyPostPO myPostMessagePO2 = new MyPostPO();

        myPostMessagePO1.setImageUrl("http://img.hb.aicdn.com/c46ff78ec3a4013cdba090c690407a2ac106aacf13209-4Zazxz_fw658");
        myPostMessagePO1.setPostContent("五折(⊙o⊙) 亲");
        myPostMessagePO2.setImageUrl("http://img.hb.aicdn.com/a284ebacc7f6fc0fd2a9573d2d22e30146b9195513db5-xeRWvB_fw658");
        myPostMessagePO2.setPostContent("买一只 送一只");

        fakeNotificationData.add(myPostMessagePO1);
        fakeNotificationData.add(myPostMessagePO2);
        return fakeNotificationData;
    }

    static class ViewHolder {
        ImageView image;
        TextView text;
    }
}
