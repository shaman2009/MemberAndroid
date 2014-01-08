package com.dandelion.memberandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.activity.SlidingmenuActivity;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.dao.auto.MerchantInfo;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.util.DeviceUtil;
import com.dandelion.memberandroid.util.Md5;
import com.dandelion.memberandroid.volley.MemberappApi;

import org.json.JSONException;

import java.util.List;

public class MerchantRegisterFragment extends Fragment {
    //UI
    private TextView nameView;
    private TextView phoneView;
    private TextView addressView;
    private TextView emailView;
    private TextView merchantTypeView;
    private TextView introductionView;
    private CheckBox nameRequiredVIew;
    private CheckBox sexRequiredView;
    private CheckBox phoneRequiredView;
    private CheckBox addressRequiredView;
    private CheckBox emailRequiredView;
    private CheckBox birthdayRequriredView;
    private ToggleButton memberSettingView;
    private ToggleButton scorePlanView;
    private TextView memberCostView;
    private TextView memberTimesView;
    private Button registerButton;

    //VALUES
    private String name;
    private String phone;
    private String address;
    private String email;
    private String merchantType;
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


    private AccountService accountservice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_register, container, false);
        initWidget(view);
        return view;
    }

    @Override
    public void onStart() {
        nameView.setText(WebserviceConstant.STAR);
        phoneView.setText(WebserviceConstant.STAR);
        addressView.setText(WebserviceConstant.STAR);
        emailView.setText(WebserviceConstant.STAR);
        merchantTypeView.setText(WebserviceConstant.STAR);


        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO  backgroundurl?
                        attemptMerchantRecordRegister();
                    }
                });


        super.onStart();
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
        merchantTypeView.setError(null);
        // Store values at the time of the login attempt.
        name = nameView.getText().toString();
        phone = phoneView.getText().toString();
        address = addressView.getText().toString();
        email = emailView.getText().toString();
        merchantType = merchantTypeView.getText().toString();

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
        if (birthdayRequriredView.isChecked()) {
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

        if (merchantType.equals(WebserviceConstant.STAR)) {
            merchantTypeView.setError(getString(R.string.error_field_required));
            focusView = merchantTypeView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            merchantInfo.setName(name);
            merchantInfo.setAddress(address);
            merchantInfo.setPhone(phone);
            merchantInfo.setEmail(email);
            merchantInfo.setMerchanttype(merchantType);
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

            MemberappApi.updateMerchantInfoByUserId(merchantInfo, sid, updateListener, updateErrorListener);
        }


    }


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

    public void recordRegisterSuccess() {
        Intent intent = new Intent(getActivity(), SlidingmenuActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

    private void initWidget(View view) {
        nameView = (TextView) view.findViewById(R.id.edit_text_record_merchant_name);
        phoneView = (TextView) view.findViewById(R.id.edit_text_record_mobile);
        addressView = (TextView) view.findViewById(R.id.edit_text_record_address);
        emailView = (TextView) view.findViewById(R.id.edit_text_record_email);
        merchantTypeView = (TextView) view.findViewById(R.id.edit_text_record_type);
        introductionView = (TextView) view.findViewById(R.id.edit_text_record_info);
        nameRequiredVIew = (CheckBox) view.findViewById(R.id.checkbox_record_member_name);
        sexRequiredView = (CheckBox) view.findViewById(R.id.checkbox_record_member_sex);
        phoneRequiredView = (CheckBox) view.findViewById(R.id.checkbox_record_member_sex);
        addressRequiredView = (CheckBox) view.findViewById(R.id.checkbox_record_address);
        emailRequiredView = (CheckBox) view.findViewById(R.id.checkbox_record_email);
        birthdayRequriredView = (CheckBox) view.findViewById(R.id.checkbox_record_member_birthday);

        memberSettingView = (ToggleButton) view.findViewById(R.id.togglebutton_record_member_setting);
        scorePlanView = (ToggleButton) view.findViewById(R.id.togglebutton_record_score_plan);
        memberCostView = (TextView) view.findViewById(R.id.edit_text_record_member_cost);
        memberTimesView = (TextView) view.findViewById(R.id.edit_text_record_member_times);

        registerButton = (Button) view.findViewById(R.id.button_record_register);
    }

}
