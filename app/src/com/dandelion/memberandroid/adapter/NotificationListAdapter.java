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
import com.dandelion.memberandroid.dao.auto.Account;
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
            holder.button_accept = (Button) convertView.findViewById(R.id.button_accept);
            holder.button_refuse = (Button) convertView.findViewById(R.id.button_refuse);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the image URL for the current position.
        final NotificationMessagePO notificationMessage = (NotificationMessagePO) getItem(position);
        final long id = notificationMessage.getId();
        String url = notificationMessage.getAvatarUrl();
        boolean isRead = notificationMessage.isRead();
        AccountService service = new AccountService(context);
        final long targetUserId = notificationMessage.getTargetUserId();
        Account account = service.getAuthAccount();
        final String sid = account.getSid();
        final long userId = account.getUsdId();
        holder.text.setText(notificationMessage.getContext());

        holder.button_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> updateNotificationListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                        notifyDataSetChanged();
                    }
                };

                Response.ErrorListener updateNotificationErrorListener = new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                };
                MemberappApi.updateNotificationIsDelete(id, userId, sid, true, updateNotificationListener, updateNotificationErrorListener);


            }
        });

        holder.button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
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

                            Response.Listener<String> updateNotificationListener = new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                                    notificationMessage.setRead(true);
                                    notifyDataSetChanged();
                                }
                            };

                            Response.ErrorListener updateNotificationErrorListener = new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            };
                            MemberappApi.updateNotificationIsRead(id, userId, sid, true, updateNotificationListener, updateNotificationErrorListener);

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
        Picasso.with(context).setDebugging(true);
        if (isRead) {
            holder.button_accept.setEnabled(false);
            holder.button_accept.setText(R.string.accept_join_yet);
            holder.button_refuse.setText(R.string.delete);
        }
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
        Button button_accept;
        Button button_refuse;
    }
}
