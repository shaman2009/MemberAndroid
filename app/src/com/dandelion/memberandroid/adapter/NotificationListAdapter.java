package com.dandelion.memberandroid.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.model.MemberTimelineFeedPO;
import com.dandelion.memberandroid.model.NotificationMessagePO;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class NotificationListAdapter extends BaseAdapter {
    private Context context;
    private List<NotificationMessagePO> notificationData = new ArrayList<NotificationMessagePO>();


    public NotificationListAdapter(Context context) {
        this.context = context;
        //TODO
        notificationData = new ArrayList<NotificationMessagePO>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image_notification);
            holder.text = (TextView) convertView.findViewById(R.id.text_notification);
            holder.button = (Button) convertView.findViewById(R.id.button_accept);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the image URL for the current position.
        NotificationMessagePO notificationMessage = (NotificationMessagePO) getItem(position);
        String url = notificationMessage.getAvatarUrl();
        AccountService service = new AccountService(context);
        final long targetUserId = notificationMessage.getTargetUserId();
        final String sid = service.getAuthAccount().getSid();
        holder.text.setText(notificationMessage.getContext());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MemberappApi.follow(targetUserId, sid, followListener, followErrorListener);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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


    public void swapItems(List<NotificationMessagePO> data) {
        notificationData = data;
        notifyDataSetChanged();
    }


    Response.Listener<String> followListener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.d(LoggerConstant.VOLLEY_REQUEST, response);
            new AlertDialog.Builder(context)
                    .setMessage(context.getString(R.string.dialog_merchant_alert_accept_success))
                    .setNeutralButton(context.getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    };


    Response.ErrorListener followErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getString(R.string.dialog_network_error))
                    .setNeutralButton(context.getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    };

    public List<NotificationMessagePO> fakeData() {
        List<NotificationMessagePO> fakeNotificationData = new ArrayList<NotificationMessagePO>();
        NotificationMessagePO notificationMessagePO1 = new NotificationMessagePO();
        NotificationMessagePO notificationMessagePO2 = new NotificationMessagePO();
        notificationMessagePO1.setAvatarUrl("http://img.hb.aicdn.com/dd36a7f1de13a98fb19d52374f8dd0581fc223da3b2f-kGpGMz_fw658");
        notificationMessagePO1.setContext("Shaman 想要加入成為你的會員");
        fakeNotificationData.add(notificationMessagePO1);
        notificationMessagePO2.setAvatarUrl("http://img.hb.aicdn.com/70c88c4d3f3f19df2f14ff33f1fe5219312564266b82-CFNhBE_fw658");
        notificationMessagePO2.setContext("Daniel 發佈了更新");
        fakeNotificationData.add(notificationMessagePO2);
        return fakeNotificationData;
    }

    static class ViewHolder {
        ImageView image;
        TextView text;
        Button button;
    }
}
