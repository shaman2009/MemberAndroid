package com.dandelion.memberandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.activity.SlidingmenuActivity;
import com.dandelion.memberandroid.constant.WebserviceConstant;

public class MerchantRegisterFragment extends Fragment{
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_merchant_register, container, false);
        initWidget(view);
        return view;
	}
	@Override
	public void onStart() {



        getActivity().findViewById(R.id.button_record_register).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO
                        attemptMerchantRecordRegister();
                    }
                });


		super.onStart();
	}

    public void attemptMerchantRecordRegister() {
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
