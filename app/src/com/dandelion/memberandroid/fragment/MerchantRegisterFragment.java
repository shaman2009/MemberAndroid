package com.dandelion.memberandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.activity.SlidingmenuActivity;
import com.dandelion.memberandroid.constant.WebserviceConstant;

public class MerchantRegisterFragment extends Fragment{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_merchant_register, container, false);
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
	
}
