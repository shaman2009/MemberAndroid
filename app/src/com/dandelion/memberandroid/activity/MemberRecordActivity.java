package com.dandelion.memberandroid.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.dao.auto.MemberInfo;
import com.dandelion.memberandroid.model.MemberInfoDataResponse;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.qiniu.auth.JSONObjectRet;
import com.qiniu.io.IO;
import com.qiniu.io.PutExtra;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ouroc on 3/28/14.
 */
public class MemberRecordActivity extends FragmentActivity {
    private Fragment mContent;
    private Activity activity;

    @InjectView(R.id.name)
    EditText mName;
    @InjectView(R.id.no)
    EditText mNo;
    @InjectView(R.id.spinner_sex)
    Spinner mSex;
    @InjectView(R.id.phone)
    EditText mPhone;
    @InjectView(R.id.address)
    EditText mAddress;
    @InjectView(R.id.birthday)
    EditText mBirthday;
    @InjectView(R.id.imageView_record_member_avator)
    ImageView mImageViewRecordMemberAvator;
    @InjectView(R.id.member_record_pic_upload_status)
    TextView mMemberRecordPicUploadStatus;
    @InjectView(R.id.member_record_pic_upload)
    Button mMemberRecordPicUpload;
    @InjectView(R.id.button_record_register)
    Button mButtonRecordRegister;


    //values

    private String avatarUrl;
    private String key;
    private String name;
    private String address;
    private String phone;
    private Long birthday;
    private int sex;

    private MemberInfo member;
    private Long userId;
    private String sid;
    private AccountService accountservice;
    private Account authAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;


        setContentView(R.layout.fragment_member_my_record);;
        ButterKnife.inject(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSex.setAdapter(adapter);

        mBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });
        setupActionBar();

    }


    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }






    @Override
    public void onStart() {
        accountservice = new AccountService(this);
        authAccount = accountservice.getAuthAccount();
        userId = authAccount.getUsdId();
        sid = authAccount.getSid();

        Response.Listener<String> getMemberListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                getMemberInfoSuccess(response);
            }

        };
        Response.ErrorListener getMemberErrorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LoggerConstant.VOLLEY_REQUEST, error.toString());
                new AlertDialog.Builder(activity)
                        .setMessage(activity.getString(R.string.dialog_network_error))
                        .setNeutralButton(activity.getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        };
        MemberappApi.getMemberInfoByUserId(userId, sid, getMemberListener, getMemberErrorListener);

        mMemberRecordPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, QiNiuConstant.PICK_PICTURE_RESUMABLE);
                return;
            }
        });
        mButtonRecordRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptMemberRecordRegister();
            }
        });
        super.onStart();
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        FragmentManager fm = this.getSupportFragmentManager();
        newFragment.show(fm, "timePicker");
    }
    private void attemptMemberRecordRegister() {

        accountservice = new AccountService(this);
        Account account = accountservice.getAuthAccount();
        long userId = account.getUsdId();
        String sid = account.getSid();
        member = new MemberInfo();

        // Reset errors.
        mName.setError(null);
        mPhone.setError(null);
        mAddress.setError(null);
        //TODO
//        mBirthday.setError(null);
        // Store values at the time of the login attempt.
        name = mName.getText().toString();
        phone = mPhone.getText().toString();
        address = mAddress.getText().toString();
        sex = mSex.getSelectedItemPosition();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(name)) {
            mName.setError(getString(R.string.error_field_required));
            focusView = mName;
            cancel = true;
        }
        if (TextUtils.isEmpty(address)) {
            mAddress.setError(getString(R.string.error_field_required));
            focusView = mAddress;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            member.setName(name);
            member.setSex(sex);
            member.setPhone(phone);
            member.setAddress(address);
            member.setBirthday(new Date(birthday));
            if (key != null) {
                member.setAvatarurl(key);
            } else {
                member.setAvatarurl(avatarUrl);
            }

            member.setUserId(userId);

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
            try {
                MemberappApi.updateMemberInfoByUserId(member, sid, updateListener, updateErrorListener);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void recordRegisterSuccess() {
        new AlertDialog.Builder(this)
                .setMessage(this.getString(R.string.dialog_submit_success))
                .setNeutralButton(this.getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();

    }
    private void getMemberInfoSuccess(String response) {
        Gson gson = new Gson();
        MemberInfoDataResponse memberInfoDataResponse = gson.fromJson(response, MemberInfoDataResponse.class);
        mNo.setText(memberInfoDataResponse.getId().toString());
        mName.setText(memberInfoDataResponse.getName());
        mSex.setSelection(memberInfoDataResponse.getSex());
        mPhone.setText(memberInfoDataResponse.getPhone());
        mAddress.setText(memberInfoDataResponse.getAddress());
        birthday = memberInfoDataResponse.getBirthday() * 1000;
        Date b = new Date(birthday);
        String data = b.getYear() + "." + b.getMonth() + "." + b.getDay();
        mBirthday.setText(data);
        avatarUrl = memberInfoDataResponse.getAvatarurl();
        Picasso.with(this).load(QiNiuConstant.getImageDownloadURL(avatarUrl)).into(mImageViewRecordMemberAvator);
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

    boolean uploading = false;

    /**
     * 普通上傳文件
     *
     * @param uri
     */
    private void doUpload(Uri uri) {
        if (uploading) {
            mMemberRecordPicUploadStatus.setText("Uploading...");
            return;
        }

        uploading = true;
        key = UUID.randomUUID().toString();

        PutExtra extra = new PutExtra();
        extra.checkCrc = PutExtra.AUTO_CRC32;
        extra.params.put("x:arg", "value");
        mMemberRecordPicUploadStatus.setText("Uploading...");
        IO.putFile(this, QiNiuConstant.UP_TOKEN, key, uri, extra, new JSONObjectRet() {
            @Override
            public void onSuccess(JSONObject resp) {
                uploading = false;
                String redirect = "http://" + QiNiuConstant.DOWNLOAD_DOMAIN + "/" + key;
                mMemberRecordPicUploadStatus.setText("Upload Success! ");
                Log.d("QINIU_UPLOAD", "redirect : " + redirect);
                downloadViaPicasso(activity, redirect);
                attemptMemberRecordRegister();
            }

            @Override
            public void onFailure(Exception ex) {
                uploading = false;
                mMemberRecordPicUploadStatus.setText("Error: " + ex.getMessage());
            }
        });
    }

    public void downloadViaPicasso(Context context, String path) {
        Picasso.with(context).load(path).into(mImageViewRecordMemberAvator);
    }

    public class TimePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            // Create a new instance of TimePickerDialog and return it
            return new DatePickerDialog(activity, this, year, month,day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            birthday = new Date(year, monthOfYear, dayOfMonth).getTime() / 1000;
            String data = year + "." + monthOfYear + "." + dayOfMonth;
            mBirthday.setText(data);
        }
    }

}
