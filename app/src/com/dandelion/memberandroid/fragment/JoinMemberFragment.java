package com.dandelion.memberandroid.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by FengxiangZhu on 13-12-29.
 */
public class JoinMemberFragment extends Fragment {
    //UI
    private Button joinButton;
    private EditText searchEditText;
    private ViewGroup container;
    private Button applyButton;
    private Button recordRegisterButton;
    private AlertDialog.Builder merchantBuilder;
    private Dialog merchantDialog;
    private ProgressDialog mDialog;

    //VALUE
    private String sid;
    private Long targetUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        return inflater.inflate(R.layout.fragment_join_member, container, false);
    }

    @Override
    public void onStart() {
        initWidget();
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptGetMerchant();
                showLoading(true);
            }
        });
        super.onStart();
    }

    public void initWidget() {
        joinButton = (Button) getActivity().findViewById(R.id.join_merchant_button);
        searchEditText = (EditText) getActivity().findViewById(R.id.join_merchant_input);
    }
    public void showLoading(final boolean show) {
        if (show) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage(getActivity().getString(R.string.progress_searching));
            mDialog.setCancelable(false);
            mDialog.show();
        } else {
            if(mDialog != null)
                mDialog.dismiss();
        }
    }

    public void attemptGetMerchant() {
        // Reset errors.
        searchEditText.setError(null);
        // Store values at the time of the login attempt.
        String key = searchEditText.getText().toString();
        boolean cancel = false;
        View focusView = null;
        // Check for a valid password.
        if (TextUtils.isEmpty(key)) {
            searchEditText.setError(getString(R.string.error_field_required));
            focusView = searchEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            AccountService service = new AccountService(getActivity());
            sid = service.getAuthAccount().getSid();
            long targetMerchantId = Long.valueOf(key);
            Response.Listener<String> getMerchantListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                    merchantBuilder = new AlertDialog.Builder(getActivity()).setView(callMerchantDetailDialog(response));
                    merchantDialog = merchantBuilder.show();
                    showLoading(false);

                }
            };
            Response.ErrorListener getMerchantErrorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(LoggerConstant.VOLLEY_REQUEST, error.toString());
                    showLoading(false);
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getActivity().getString(R.string.dialog_merchant_search_error))
                            .setNeutralButton(getActivity().getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
            };
            MemberappApi.getMerchantInfoByMerchantId(targetMerchantId, sid, getMerchantListener, getMerchantErrorListener);
        }

    }


    public View callMerchantDetailDialog(String response) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_merchant_detail_info, container, false);


        ImageView avatarView = (ImageView) view.findViewById(R.id.imageView_dialog_merchant_detail_avator);
        TextView merchantNoView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_no);
        TextView merchantNameView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_merchant_name);
        TextView merchantMobileView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_mobile);
        TextView addressView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_address);
        TextView emailView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_email);
        TextView contentView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_content);
        TextView contentNeedView = (TextView) view.findViewById(R.id.edit_dialog_merchant_detail_need);
        recordRegisterButton = (Button) view.findViewById(R.id.button_dialog_merchant_detail_record_register);
        applyButton = (Button) view.findViewById(R.id.button_dialog_merchant_detail_applying);

        try {
            JSONObject json = new JSONObject(response).getJSONArray("merchantList").getJSONObject(0);
            String imageUrl = json.getString("avatarurl");
            Picasso.with(getActivity())
                    .load(QiNiuConstant.getImageDownloadURL(imageUrl))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerInside()
                    .into(avatarView);
            //{"id":2001,"avatarurl":"","name":"cc","address":"","phone":"","email":"","merchanttype":"","introduction":"",
            // "namerequired":false,"sexrequired":false,"phonerequired":false,"addressrequired":false,"emailrequired":false,
            // "birthdayrequired":false,"membersetting":false,"amountrequired":0,
            // "amountcountrequired":0,"scoreplan":false,"createddate":1388546653000,"modifieddate":1388547235000,"useridfk":248,"backgroundurl":""}
            merchantNoView.setText(json.getString("id"));
            merchantNameView.setText(json.getString("name"));
            merchantMobileView.setText(json.getString("phone"));
            addressView.setText(json.getString("address"));
            emailView.setText(json.getString("email"));
            long amountcountrequired = json.getLong("amountcountrequired");
            long amountrequired = json.getLong("amountrequired");
            if (!(amountrequired == 0 && amountcountrequired == 0)) {
                String need = getActivity().getString(R.string.join_amount_head);
                need += amountcountrequired + getActivity().getString(R.string.join_amount_mid)
                        + amountrequired + " HKD " + getActivity().getString(R.string.join_amount_foot);
                contentNeedView.setText(need);
            }


            //TODO
            targetUserId = json.getLong("useridfk");
            boolean namerequired = json.getBoolean("namerequired");
            boolean sexrequired = json.getBoolean("sexrequired");
            boolean phonerequired = json.getBoolean("phonerequired");
            boolean addressrequired = json.getBoolean("addressrequired");
            boolean emailrequired = json.getBoolean("emailrequired");
            boolean birthdayrequired = json.getBoolean("birthdayrequired");
            String content = getActivity().getString(R.string.join_apply_need_head);
            boolean contentIsNull = true;
            if (namerequired) {
                content += " 姓名 ";
                contentIsNull = false;
            }
            if (sexrequired) {
                content += " 性別 ";
                contentIsNull = false;
            }
            if (phonerequired) {
                content += " 電話 ";
                contentIsNull = false;
            }
            if (addressrequired) {
                content += " 地址 ";
                contentIsNull = false;
            }
            if (emailrequired) {
                content += " 電郵 ";
                contentIsNull = false;
            }
            if (birthdayrequired) {
                content += " 生日 ";
                contentIsNull = false;
            }
            if (!contentIsNull) {
                contentView.setText(content);
                String[] strs = content.split(":");
                final String message = strs[1];
                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle(getActivity().getString(R.string.join_alert_title))
                                .setMessage(message)
                                .setNegativeButton(getActivity().getString(R.string.cancel), null)
                                .setNeutralButton(getActivity().getString(R.string.agree), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        followMerchant();
                                    }
                                }).show();

                    }
                });
            } else {
                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        followMerchant();

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recordRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMemberRecord();
            }
        });
        return view;
    }


    public void registerMemberRecord() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new MemberMyRecordFragment())
                .addToBackStack(null)
                .commit();
        merchantDialog.dismiss();
    }

    public void followMerchant() {
        try {
            Response.Listener<String> followListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getActivity().getString(R.string.dialog_merchant_alert_apply_success))
                            .setNeutralButton(getActivity().getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    merchantDialog.dismiss();
                                }
                            }).show();
                    applyButton.setText(R.string.dialog_merchant_applyed);
                    applyButton.setOnClickListener(null);
                }
            };
            Response.ErrorListener followErrorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getActivity().getString(R.string.dialog_merchant_alert_apply_error))
                            .setNeutralButton(getActivity().getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
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


}
