package com.dandelion.memberandroid.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.dao.auto.MerchantInfo;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.qiniu.auth.JSONObjectRet;
import com.qiniu.io.IO;
import com.qiniu.io.PutExtra;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class MerchantMyRecordFragment extends Fragment {

    //UI
	private Button btnUpload;
	private TextView hint;
	private ImageView imageView;

    private TextView merchantIdView;
    private TextView nameView;
    private TextView phoneView;
    private TextView addressView;
    private TextView emailView;
    private Spinner merchantTypeSpinner;
    private TextView introductionView;
    private CheckBox nameRequiredVIew;
    private CheckBox sexRequiredView;
    private CheckBox phoneRequiredView;
    private CheckBox addressRequiredView;
    private CheckBox emailRequiredView;
    private CheckBox birthdayRequiredView;
    private ToggleButton memberSettingView;
    private ToggleButton scorePlanView;
    private TextView memberCostView;
    private TextView memberTimesView;
    private Button registerButton;

    //VALUES
    private String imagekey;

    private Long merchantId;
    private String name;
    private String phone;
    private String address;
    private String email;
    private int merchantType;
    private String introduction;
    private boolean isNameRequired;
    private boolean isSexRequired;
    private boolean isPhoneRequired;
    private boolean isAddressRequired;
    private boolean isEmailRequired;
    private boolean isBirthdayRequired;
    private boolean isMemberSetting;
    private boolean isScorePlan;
    private String memberCost;
    private String memberTimes;

    private MerchantInfo merchantInfo;

    private Long userId;
    private String sid;

    private AccountService accountservice;
    private Account authAccount;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_my_record, container, false);
        initWidget(view);
        return view;
	}

	@Override
	public void onStart() {
		btnUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, QiNiuConstant.PICK_PICTURE_RESUMABLE);
				return;
			}
		});
        accountservice = new AccountService(getActivity());
        authAccount = accountservice.getAuthAccount();
        userId = authAccount.getUsdId();
        sid = authAccount.getSid();
        MemberappApi.getMerchantInfoByUserId(userId, sid, getMerchantListener, getMerchantErrorListener);
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptMerchantRecordRegister();
                    }
                });
		super.onStart();
	}



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != SlidingActivity.RESULT_OK) return;

        if (requestCode == QiNiuConstant.PICK_PICTURE_RESUMABLE) {
            doUpload(data.getData());
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    Response.Listener<String> getMerchantListener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.d(LoggerConstant.VOLLEY_REQUEST, response);
            getMerchantInfoSuccess(response);
        }

    };
    Response.ErrorListener getMerchantErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(LoggerConstant.VOLLEY_REQUEST, error.toString());
            new AlertDialog.Builder(getActivity())
                    .setMessage(getActivity().getString(R.string.dialog_network_error))
                    .setNeutralButton(getActivity().getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    };

    public void getMerchantInfoSuccess(String response) {
        try {
            JSONObject merchantJson = new JSONObject(response);
            merchantIdView.setText(String.valueOf(merchantJson.getLong("merchantId")));
            imagekey = merchantJson.getString("avatarurl");
            Picasso.with(getActivity()).load(QiNiuConstant.getImageDownloadURL(imagekey)).into(imageView);
            nameView.setText(merchantJson.getString("name"));
            addressView.setText(merchantJson.getString("address"));
            phoneView.setText(merchantJson.getString("phone"));
            emailView.setText(merchantJson.getString("email"));
//TODO
            merchantTypeSpinner.setSelection(Integer.valueOf(merchantJson.getString("merchanttype")));
            //merchantTypeView.setText(merchantJson.getString("merchanttype"));
            introductionView.setText(merchantJson.getString("introduction"));

            nameRequiredVIew.setChecked(merchantJson.getBoolean("namerequired"));
            sexRequiredView.setChecked(merchantJson.getBoolean("sexrequired"));
            phoneRequiredView.setChecked(merchantJson.getBoolean("phonerequired"));
            addressRequiredView.setChecked(merchantJson.getBoolean("addressrequired"));
            emailRequiredView.setChecked(merchantJson.getBoolean("emailrequired"));
            birthdayRequiredView.setChecked(merchantJson.getBoolean("birthdayrequired"));
            memberSettingView.setChecked(merchantJson.getBoolean("membersetting"));
            memberSettingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!memberSettingView.isChecked()) {
                        memberCostView.setEnabled(false);
                        memberTimesView.setEnabled(false);
                    } else {
                        memberCostView.setEnabled(true);
                        memberTimesView.setEnabled(true);
                    }
                }
            });
            scorePlanView.setChecked(merchantJson.getBoolean("scoreplan"));

            memberCostView.setText(merchantJson.getString("amountrequired"));
            memberTimesView.setText(merchantJson.getString("amountcountrequired"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
	public void initWidget(View view) {
		btnUpload = (Button) view.findViewById(R.id.record_pic_upload);
		hint = (TextView) view.findViewById(R.id.record_pic_upload_status);
		imageView = (ImageView) view.findViewById(R.id.imageView_record_merchant_avator);

        merchantIdView = (TextView) view.findViewById(R.id.edit_text_record_merchant_no);
        nameView = (TextView) view.findViewById(R.id.edit_text_record_merchant_name);
        phoneView = (TextView) view.findViewById(R.id.edit_text_record_mobile);
        addressView = (TextView) view.findViewById(R.id.edit_text_record_address);
        emailView = (TextView) view.findViewById(R.id.edit_text_record_email);
        merchantTypeSpinner = (Spinner) view.findViewById(R.id.planets_spinner_record_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.merchant_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        merchantTypeSpinner.setAdapter(adapter);

        introductionView = (TextView) view.findViewById(R.id.edit_text_record_info);
        nameRequiredVIew = (CheckBox) view.findViewById(R.id.checkbox_record_member_name);
        sexRequiredView = (CheckBox) view.findViewById(R.id.checkbox_record_member_sex);
        phoneRequiredView = (CheckBox) view.findViewById(R.id.checkbox_record_mobile);
        addressRequiredView = (CheckBox) view.findViewById(R.id.checkbox_record_address);
        emailRequiredView = (CheckBox) view.findViewById(R.id.checkbox_record_email);
        birthdayRequiredView = (CheckBox) view.findViewById(R.id.checkbox_record_member_birthday);

        memberSettingView = (ToggleButton) view.findViewById(R.id.togglebutton_record_member_setting);
        scorePlanView = (ToggleButton) view.findViewById(R.id.togglebutton_record_score_plan);
        memberCostView = (TextView) view.findViewById(R.id.edit_text_record_member_cost);
        memberTimesView = (TextView) view.findViewById(R.id.edit_text_record_member_times);


        registerButton = (Button) view.findViewById(R.id.button_record_register);


	}


	boolean uploading = false;
	/**
	 * 普通上傳文件
	 *
	 * @param uri
	 */
	private void doUpload(Uri uri) {
		if (uploading) {
			hint.setText("上傳中，請稍後");
			return;
		}

		uploading = true;
        imagekey = UUID.randomUUID().toString();
		// 上传文件名
		PutExtra extra = new PutExtra();
		extra.checkCrc = PutExtra.AUTO_CRC32;
		extra.params.put("x:arg", "value");
		hint.setText("上傳中...");
		IO.putFile(getActivity(), QiNiuConstant.UP_TOKEN, imagekey, uri, extra, new JSONObjectRet() {
			@Override
			public void onSuccess(JSONObject resp) {
				uploading = false;
				String redirect = QiNiuConstant.getImageDownloadURL(imagekey);
				hint.setText("上傳成功！ ");
				Log.d("QINIU_UPLOAD", "redirect : " + redirect);
				downloadViaPicasso(getActivity(), redirect);
                attemptMerchantRecordRegister();
			}

			@Override
			public void onFailure(Exception ex) {
				uploading = false;
				hint.setText("錯誤: " + ex.getMessage());
			}
		});
	}

	public void downloadViaPicasso(Context context, String path) {
		Picasso.with(context).load(path).into(imageView);
	}

    public void attemptMerchantRecordRegister() {
        accountservice = new AccountService(getActivity());
        Account account = accountservice.getAuthAccount();
        long userId = account.getUsdId();
        String sid = account.getSid();
//        merchantInfo = accountservice.getMerchantInfoByUserId(userId);
        merchantInfo = new MerchantInfo();

        // Reset errors.
        nameView.setError(null);
        phoneView.setError(null);
        addressView.setError(null);
        emailView.setError(null);
        //TODO
//        merchantTypeView.setError(null);
        // Store values at the time of the login attempt.
        name = nameView.getText().toString();
        phone = phoneView.getText().toString();
        address = addressView.getText().toString();
        email = emailView.getText().toString();
        //TODO
        merchantType = merchantTypeSpinner.getSelectedItemPosition();
//        merchantType = merchantTypeView.getText().toString();
        introduction = introductionView.getText().toString();
        memberCost = memberCostView.getText().toString();
        memberTimes = memberTimesView.getText().toString();
        if (nameRequiredVIew.isChecked()) {
            isNameRequired = true;
        }
        if (sexRequiredView.isChecked()) {
            isSexRequired = true;
        }
        if (phoneRequiredView.isChecked()) {
            isPhoneRequired = true;
        }
        if (addressRequiredView.isChecked()) {
            isAddressRequired = true;
        }
        if (emailRequiredView.isChecked()) {
            isEmailRequired = true;
        }
        if (birthdayRequiredView.isChecked()) {
            isBirthdayRequired = true;
        }
        if (memberSettingView.isChecked()) {
            isMemberSetting = true;
        }
        if (scorePlanView.isChecked()) {
            isScorePlan = true;
        }


        boolean cancel = false;
        View focusView = null;
        if (name.equals(WebserviceConstant.STAR)) {
            nameView.setError(getString(R.string.error_field_required));
            focusView = nameView;
            cancel = true;
        }
        if (phone.equals(WebserviceConstant.STAR)) {
            phoneView.setError(getString(R.string.error_field_required));
            focusView = phoneView;
            cancel = true;
        }
        if (address.equals(WebserviceConstant.STAR)) {
            addressView.setError(getString(R.string.error_field_required));
            focusView = addressView;
            cancel = true;
        }
        if (email.equals(WebserviceConstant.STAR)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = phoneView;
            cancel = true;
        } else if (!email.contains("@")) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            merchantInfo.setAvatarurl(imagekey);
            merchantInfo.setName(name);
            merchantInfo.setAddress(address);
            merchantInfo.setPhone(phone);
            merchantInfo.setEmail(email);
            merchantInfo.setMerchanttype(String.valueOf(merchantType));
            merchantInfo.setIntroduction(introduction);
            merchantInfo.setNamerequired(isNameRequired);
            merchantInfo.setSexrequired(isSexRequired);
            merchantInfo.setPhonerequired(isPhoneRequired);
            merchantInfo.setAddressrequired(isAddressRequired);
            merchantInfo.setEmailrequired(isEmailRequired);
            merchantInfo.setBirthdayrequired(isBirthdayRequired);
            merchantInfo.setMembersetting(isMemberSetting);
            merchantInfo.setScoreplan(isScorePlan);
            if (!TextUtils.isEmpty(memberCost)) {
                merchantInfo.setAmountrequired(Integer.valueOf(memberCost));
            } else {
                merchantInfo.setAmountrequired(0);
            }
            if (!TextUtils.isEmpty(memberTimes)) {
                merchantInfo.setAmountcountrequired(Integer.valueOf(memberTimes));
            } else {
                merchantInfo.setAmountcountrequired(0);
            }
            merchantInfo.setUseridfk(userId);
            Response.Listener<String> updateListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                    recordRegisterSuccess();
                }
            };
            Response.ErrorListener updateErrorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            };
            MemberappApi.updateMerchantInfoByUserId(merchantInfo, sid, updateListener, updateErrorListener);
        }
    }

    public void recordRegisterSuccess() {
        new AlertDialog.Builder(getActivity())
                .setMessage(getActivity().getString(R.string.dialog_submit_success))
                .setNeutralButton(getActivity().getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }
}
