package com.dandelion.memberandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.model.MyMembersPO;
import com.dandelion.memberandroid.model.NotificationMessagePO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FengxiangZhu on 13-12-28.
 */
public class MyMembersListAdapter extends BaseAdapter {
    private Context context;
    private List<MyMembersPO> myMembersData = new ArrayList<MyMembersPO>();


    public MyMembersListAdapter(Context context) {
        this.context = context;
        //TODO
        myMembersData = new ArrayList<MyMembersPO>();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_member, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image_my_member);
            holder.button = (Button) convertView.findViewById(R.id.my_member_button_add_score);
            holder.text_my_members_name = (TextView) convertView.findViewById(R.id.my_member_name);
            holder.text_my_members_total_score = (TextView) convertView.findViewById(R.id.my_member_total_score);
            holder.text_my_members_total_cost_value = (TextView) convertView.findViewById(R.id.my_members_total_cost_value);
            holder.text_my_members_total_times_value = (TextView) convertView.findViewById(R.id.my_members_total_times_value);
            holder.text_my_members_total_cost = (TextView) convertView.findViewById(R.id.my_members_total_cost);
            holder.text_my_members_total_times = (TextView) convertView.findViewById(R.id.my_members_total_times);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the image URL for the current position.
        MyMembersPO myMember = (MyMembersPO) getItem(position);
        String url = myMember.getAvatarUrl();
        boolean isMember = myMember.isMember();
        holder.text_my_members_name.setText(myMember.getName());
        if (isMember) {
            holder.text_my_members_total_cost_value.setVisibility(View.GONE);
            holder.text_my_members_total_times_value.setVisibility(View.GONE);
            holder.text_my_members_total_cost.setVisibility(View.GONE);
            holder.text_my_members_total_times.setVisibility(View.GONE);
            holder.text_my_members_total_score.setText(context.getString(R.string.my_members_total_score) + " : " + myMember.getScore().toString());

        } else {
            holder.button.setVisibility(View.GONE);

            holder.text_my_members_total_cost_value.setText(myMember.getMemberTotalCosts().toString());
            holder.text_my_members_total_times_value.setText(myMember.getMemberTotalTimes().toString());
            holder.text_my_members_total_score.setText(context.getString(R.string.my_members_applying));

        }


        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .into(holder.image);
        Picasso.with(context).setDebugging(true);
        return convertView;
    }

    @Override
    public int getCount() {
        return myMembersData.size();
    }


    @Override
    public Object getItem(int position) {
        return myMembersData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void swapItems(List<MyMembersPO> data) {
        myMembersData = data;
        notifyDataSetChanged();
    }




    public List<MyMembersPO> fakeData() {
        List<MyMembersPO> fakeNotificationData = new ArrayList<MyMembersPO>();
        MyMembersPO myMemberPO1 = new MyMembersPO();
        MyMembersPO myMemberPO2 = new MyMembersPO();
        MyMembersPO myMemberPO3 = new MyMembersPO();
        MyMembersPO myMemberPO4 = new MyMembersPO();

        myMemberPO1.setAvatarUrl("http://img.hb.aicdn.com/da2f9fbc0fd07565ed15d0cd3480d05983337ac8e6b8-n5npKr_fw658");
        myMemberPO1.setMember(true);
        myMemberPO1.setScore(1000L);
        myMemberPO1.setName("Rogue");

        myMemberPO2.setAvatarUrl("http://img.hb.aicdn.com/b4f49d0155edf776ec8968285dc26b0cc27736cc1bdb6-29reUQ_fw658");
        myMemberPO2.setMember(false);
        myMemberPO2.setMemberTotalCosts(820L);
        myMemberPO2.setMemberTotalTimes(5L);
        myMemberPO2.setName("Shaman");


        myMemberPO3.setAvatarUrl("http://img.hb.aicdn.com/4a1b1875124b325f289733aa08098bd2020bb5012daa-dP44w6_fw658");
        myMemberPO4.setAvatarUrl("http://img.hb.aicdn.com/3036608e2f2e60db1f3b3424f80b1b2e53c2fbc4bb3d-AK3J9E_fw658");



        /**
         private boolean isMember;
         private Long score;
         private String name;
         private Long memberTotalCosts;
         private Long memberTotalTimes;
         */
        fakeNotificationData.add(myMemberPO1);
        fakeNotificationData.add(myMemberPO2);
        return fakeNotificationData;
    }

    static class ViewHolder {
        ImageView image;
        TextView text_my_members_name;
        TextView text_my_members_total_score;
        TextView text_my_members_total_cost_value;
        TextView text_my_members_total_times_value;
        TextView text_my_members_total_cost;
        TextView text_my_members_total_times;
        Button button;
    }
}
