package com.dandelion.memberandroid.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.activity.ClusteringDemoActivity;
import com.dandelion.memberandroid.activity.RegisterActivity;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.dandelion.memberandroid.fragment.MemberMyRecordFragment;
import com.dandelion.memberandroid.fragment.MerchantPostFragment;
import com.dandelion.memberandroid.model.MemberDataResponse;
import com.dandelion.memberandroid.model.MyMembersPO;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FengxiangZhu on 13-12-28.
 */
public class MyMembersListAdapter extends BaseAdapter {
    private Context context;
    private List<MyMembersPO> myMembersData = new ArrayList<MyMembersPO>();
    private FragmentManager fm;
    private AlertDialog.Builder scoreDialogBuilder;
    private ViewGroup container;
    private Dialog scoreDialog;
    private TextView scoreView;

    private AlertDialog.Builder merchantBuilder;
    private Dialog merchantDialog;
    private ProgressDialog mDialog;

    //value
    private String sid;

    public MyMembersListAdapter(Context context) {
        this.context = context;
        myMembersData = new ArrayList<MyMembersPO>();
    }

    public MyMembersListAdapter(Context context, FragmentManager fm) {
        this.context = context;
        this.fm = fm;
        myMembersData = new ArrayList<MyMembersPO>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            container = parent;
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
            holder.count_click = convertView.findViewById(R.id.count_click);
            holder.progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the image URL for the current position.
        final MyMembersPO myMember = (MyMembersPO) getItem(position);
        String url = myMember.getAvatarUrl();
        boolean isMember = myMember.isMember();
        final long score = myMember.getScore();
        final long friendId = myMember.getFriendId();
        final Long totalCosts = myMember.getMemberTotalCosts();
        final Long totalTimes = myMember.getMemberTotalTimes();

        holder.text_my_members_name.setText(myMember.getName());
        if (isMember) {
            holder.text_my_members_total_cost_value.setVisibility(View.GONE);
            holder.text_my_members_total_times_value.setVisibility(View.GONE);
            holder.text_my_members_total_cost.setVisibility(View.GONE);
            holder.text_my_members_total_times.setVisibility(View.GONE);
            holder.text_my_members_total_score.setText(context.getString(R.string.my_members_total_score) + " : " + score);

            holder.progressBar.setVisibility(View.GONE);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_score, null);
                    scoreDialogBuilder = new AlertDialog.Builder(context).setView(view)
                            .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TextView scoreView = (TextView) view.findViewById(R.id.score);
                                    final long finalscore = score + Long.valueOf(scoreView.getText().toString());
                                    AccountService service = new AccountService(context);
                                    sid = service.getAuthAccount().getSid();
                                    MemberDataResponse memberDataResponse = new MemberDataResponse();
                                    memberDataResponse.setScore(finalscore);
                                    memberDataResponse.setAmount(0L);
                                    memberDataResponse.setAmountcount(0L);
                                    Response.Listener<String> updateMyMemberInfoListener = new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                                            myMember.setScore(finalscore);
                                            notifyDataSetChanged();
                                            Toast.makeText(context, R.string.dialog_submit_success, Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    Response.ErrorListener updateMyMemberInfoErrorListener = new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context, R.string.dialog_network_error, Toast.LENGTH_SHORT).show();
                                        }

                                    };
                                    try {
                                        MemberappApi.updateMemberInfo(friendId, sid, memberDataResponse, updateMyMemberInfoListener, updateMyMemberInfoErrorListener);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton(R.string.account_logout_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    scoreDialog = scoreDialogBuilder.show();
                }
            });
        } else {
            long amount = myMember.getAmountrequired();
            long amountCount = myMember.getAmountcountrequired();
            long actualAmount = totalCosts;
            long actualAmountCount = totalTimes;
            int max = holder.progressBar.getMax();
            if(amount != 0 && amountCount != 0) {
                int a = Math.round(Float.valueOf(actualAmount)/amount * max);
                int b = Math.round(Float.valueOf(actualAmountCount)/amountCount *max);
                if (a > b) {
                    Log.d("actualAmount", String.valueOf(a));
                    holder.progressBar.setProgress(a);
                } else {
                    Log.d("actualAmountCount", String.valueOf(b));
                    holder.progressBar.setProgress(b);
                }
            }

//            holder.button.setVisibility(View.INVISIBLE);

            holder.text_my_members_total_cost_value.setText(totalCosts.toString());
            holder.text_my_members_total_times_value.setText(totalTimes.toString());
            holder.text_my_members_total_score.setText(context.getString(R.string.my_members_applying));

            holder.button.setText(R.string.my_members_add_amount);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_score, null);
                    TextView scoreView = (TextView) view.findViewById(R.id.score);
                    scoreView.setHint(R.string.add_amount_dialog);
                    scoreDialogBuilder = new AlertDialog.Builder(context).setView(view)
                            .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TextView scoreView = (TextView) view.findViewById(R.id.score);

                                    final long finalTotalCosts = totalCosts + Long.valueOf(scoreView.getText().toString());
                                    final long finalTotalTimes = totalTimes + 1;
                                    AccountService service = new AccountService(context);
                                    sid = service.getAuthAccount().getSid();
                                    MemberDataResponse memberDataResponse = new MemberDataResponse();
                                    memberDataResponse.setScore(0L);
                                    memberDataResponse.setAmount(finalTotalCosts);
                                    memberDataResponse.setAmountcount(finalTotalTimes);
                                    Response.Listener<String> updateMyMemberInfoListener = new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                                            myMember.setMemberTotalCosts(finalTotalCosts);
                                            myMember.setMemberTotalTimes(finalTotalTimes);
                                            notifyDataSetChanged();
                                            Toast.makeText(context, R.string.dialog_submit_success, Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    Response.ErrorListener updateMyMemberInfoErrorListener = new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context, R.string.dialog_network_error, Toast.LENGTH_SHORT).show();
                                        }

                                    };
                                    try {
                                        MemberappApi.updateMemberInfo(friendId, sid, memberDataResponse, updateMyMemberInfoListener, updateMyMemberInfoErrorListener);
                                        //holder.text_my_members_total_score.setText(context.getString(R.string.my_members_total_score) + " : " + finalscore);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton(R.string.account_logout_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    scoreDialog = scoreDialogBuilder.show();
                }
            });

        }
        if (myMember.isMerchantOrMember()) {
            holder.count_click.setOnClickListener(null);
            final Long userId = myMember.getMerchantUserId();
            final Long merchantId = myMember.getMerchantId();
            holder.button.setVisibility(View.VISIBLE);
            holder.button.setText(R.string.merchant_detail);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showMerchantInfo(userId);
                    showMerchantDetails(merchantId);

                }
            });
        } else {
            holder.progressBar.setVisibility(View.INVISIBLE);
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

    private void showMerchantDetails(long targetMerchantId) {
        AccountService service = new AccountService(context);
        sid = service.getAuthAccount().getSid();

        Response.Listener<String> getMerchantListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                merchantBuilder = new AlertDialog.Builder(context).setView(callMerchantDetailDialog(response));
                merchantDialog = merchantBuilder.show();
                showLoading(false);

            }
        };
        Response.ErrorListener getMerchantErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LoggerConstant.VOLLEY_REQUEST, error.toString());
                showLoading(false);
                new AlertDialog.Builder(context)
                        .setMessage(context.getString(R.string.dialog_merchant_search_error))
                        .setNeutralButton(context.getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        };
        showLoading(true);
        MemberappApi.getMerchantInfoByMerchantId(targetMerchantId, sid, getMerchantListener, getMerchantErrorListener);
    }

    private void showMerchantInfo(long userId) {
        fm.beginTransaction()
                .replace(R.id.content_frame, new MerchantPostFragment(userId))
                .addToBackStack(null)
                .commit();
    }

    public View callMerchantDetailDialog(String response) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_merchant_detail_info, container, false);


        ImageView avatarView = (ImageView) view.findViewById(R.id.imageView_dialog_merchant_detail_avator);
        TextView merchantNoView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_no);
        TextView merchantNameView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_merchant_name);
        TextView merchantMobileView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_mobile);
        TextView addressView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_address);
        TextView emailView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_email);
        TextView contentView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_content);
        TextView contentNeedView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_need);
        Button recordRegisterButton = (Button) view.findViewById(R.id.button_dialog_merchant_detail_record_register);
        Button applyButton = (Button) view.findViewById(R.id.button_dialog_merchant_detail_applying);
        recordRegisterButton.setVisibility(View.INVISIBLE);
        applyButton.setText(R.string.map_info);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMapView();
            }
        });
        try {
            JSONObject json = new JSONObject(response).getJSONArray("merchantList").getJSONObject(0);
            String imageUrl = json.getString("avatarurl");
            Picasso.with(context)
                    .load(QiNiuConstant.getImageDownloadURL(imageUrl))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerInside()
                    .into(avatarView);
            merchantNoView.setText(json.getString("id"));
            merchantNameView.setText(json.getString("name"));
            merchantMobileView.setText(json.getString("phone"));
            addressView.setText(json.getString("address"));
            emailView.setText(json.getString("email"));
            long amountcountrequired = json.getLong("amountcountrequired");
            long amountrequired = json.getLong("amountrequired");
            if (!(amountrequired == 0 && amountcountrequired == 0)) {
                String need = context.getString(R.string.join_amount_head);
                need += amountcountrequired + context.getString(R.string.join_amount_mid)
                        + amountrequired + " HKD " + context.getString(R.string.join_amount_foot);
                contentNeedView.setText(need);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
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
    public void showLoading(final boolean show) {
        if (show) {
            mDialog = new ProgressDialog(context);
            mDialog.setMessage(context.getString(R.string.progress_loading));
            mDialog.setCancelable(false);
            mDialog.show();
        } else {
            if(mDialog != null)
                mDialog.dismiss();
        }
    }


    public void getMapView() {
        Intent intent = new Intent(context, ClusteringDemoActivity.class);
        context.startActivity(intent);
    }
    static class ViewHolder {
        ImageView image;
        TextView text_my_members_name;
        TextView text_my_members_total_score;
        TextView text_my_members_total_cost_value;
        TextView text_my_members_total_times_value;
        TextView text_my_members_total_cost;
        TextView text_my_members_total_times;
        View count_click;
        Button button;
        ProgressBar progressBar;
    }
}
