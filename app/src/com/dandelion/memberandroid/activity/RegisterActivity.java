package com.dandelion.memberandroid.activity;

import java.util.Date;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.IntentConstant;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.dao.MemberDao;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.util.DeviceUtil;
import com.dandelion.memberandroid.util.Md5;
import com.dandelion.memberandroid.volley.MemberappApi;

public class RegisterActivity extends Activity {


    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mAliasView;

    String registerEmail;
    String registerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Show the Up button in the action bar.
        setupActionBar();
        initWidget();


        registerEmail = mEmailView.getText().toString();
        registerPassword = Md5.GetMD5Code(mPasswordView.getText().toString());
        final String registerAlias = mAliasView.getText().toString();


        findViewById(R.id.register_merchant_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            MemberappApi.register(registerAlias, registerEmail, registerPassword, WebserviceConstant.ACCOUNT_TYPE_MERCHANT, registerListener, registerErrorListener);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        findViewById(R.id.register_member_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptRegister(IntentConstant.MEMBER);
                    }
                });
        //register_merchant_button
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void initWidget() {
        mEmailView = (EditText) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mAliasView = (EditText) findViewById(R.id.register_alias);


        mEmailView.setText(new Random().nextInt() + "test_android@gmail.com");
        mPasswordView.setText("feifeifei");
        mAliasView.setText("Feifei");

    }

    private void merchantLogin() {
        try {
            MemberappApi.login(registerEmail, registerPassword, WebserviceConstant.PACKAGE_NAME, DeviceUtil.getWIFIMacAddress(this), loginListener, loginErrorListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loginSuccess(String response) {
        Account account = new Account();
        MemberDao memberDao = new MemberDao(this);
        try {
            JSONObject json = new JSONObject(response);
            account.setSid(json.getString("sid"));
            account.setSkey(json.getLong("skey"));
            account.setAccountType(json.getInt("accountType"));
            account.setUsdId(json.getLong("userId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Date d = new Date();
        account.setCreatedDate(d);
        account.setModifyDate(d);


        memberDao.insertAccount(account);

        Intent intent = new Intent(this, MerchantRegisterActivity.class);
        startActivity(intent);
        finish();

    }

    Response.Listener<String> registerListener = new Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.d(LoggerConstant.VOLLEY_REQUEST, response);
            merchantLogin();
        }
    };


    Response.Listener<String> loginListener = new Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.d(LoggerConstant.VOLLEY_REQUEST, response);
            loginSuccess(response);
        }
    };

    Response.ErrorListener registerErrorListener = new ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            registerFailed();
        }
    };


    Response.ErrorListener loginErrorListener = new ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            registerFailed();
        }
    };

    public void registerFailed() {
        Toast.makeText(this, R.string.toast_message_register_failed,
                Toast.LENGTH_LONG).show();
    }

    private void attemptRegister(String str) {
        Intent intent = new Intent(this, SlidingmenuActivity.class);
        intent.putExtra(IntentConstant.USERTYPE, str);
        startActivity(intent);
        finish();
    }


}
