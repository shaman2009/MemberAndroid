package com.dandelion.memberandroid.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.util.DeviceUtil;
import com.dandelion.memberandroid.util.Md5;
import com.dandelion.memberandroid.volley.MemberappApi;

import org.json.JSONException;

public class RegisterActivity extends Activity {


    //UI
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mAliasView;
    private View mRegisterFormView;
    private View mRegisterStatusView;
    private TextView mRegisterStatusMessageView;


    //VALUES
    private String registerEmail;
    private String registerPassword;
    private String registerAlias;
    private int accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Show the Up button in the action bar.
        setupActionBar();
        initWidget();


        findViewById(R.id.register_merchant_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptRegister(WebserviceConstant.ACCOUNT_TYPE_MERCHANT);
                    }
                });
        findViewById(R.id.register_member_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptRegister(WebserviceConstant.ACCOUNT_TYPE_MEMBER);
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


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mRegisterStatusView.setVisibility(View.VISIBLE);
            mRegisterStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRegisterStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mRegisterFormView.setVisibility(View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRegisterFormView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {


            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mRegisterStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void initWidget() {
        mEmailView = (EditText) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mAliasView = (EditText) findViewById(R.id.register_alias);

        mRegisterFormView = findViewById(R.id.register_form);
        mRegisterStatusView = findViewById(R.id.register_status);
        mRegisterStatusMessageView = (TextView) findViewById(R.id.register_status_message);

    }

    private void merchantLogin() {
        try {
            MemberappApi.login(registerEmail, registerPassword, WebserviceConstant.PACKAGE_NAME, DeviceUtil.getWIFIMacAddress(this), loginListener, loginErrorListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loginSuccess(String response) {
        AccountService accountService = new AccountService(this);
        accountService.loginSuccessService(response);
        Class c;
        if(WebserviceConstant.ACCOUNT_TYPE_MERCHANT == accountType) {
            c = MerchantRegisterActivity.class;
        } else {
            c = SlidingmenuActivity.class;
        }
        Intent intent = new Intent(this, c);
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
            showProgress(false);
            loginSuccess(response);
        }
    };

    Response.ErrorListener registerErrorListener = new ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            showProgress(false);
            registerFailed();
        }
    };


    Response.ErrorListener loginErrorListener = new ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            showProgress(false);
            registerFailed();
        }
    };

    public void registerFailed() {
        Toast.makeText(this, R.string.toast_message_register_failed,
                Toast.LENGTH_LONG).show();
    }

    private void attemptRegister(int accountType) {
        this.accountType = accountType;
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mAliasView.setError(null);
        // Store values at the time of the register attempt.
        registerEmail = mEmailView.getText().toString();
        registerPassword = mPasswordView.getText().toString();
        registerAlias = mAliasView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        // Check for a valid password.
        if (TextUtils.isEmpty(registerPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (registerPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(registerEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!registerEmail.contains("@") || !registerEmail.contains(".com")) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(registerAlias)) {
            mAliasView.setError(getString(R.string.error_field_required));
            focusView = mAliasView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mRegisterStatusMessageView.setText(R.string.register_progress_register);
            showProgress(true);
            try {
                registerPassword = Md5.GetMD5Code(registerPassword);
                MemberappApi.register(registerAlias, registerEmail, registerPassword, accountType, registerListener, registerErrorListener);
                Log.i(LoggerConstant.VOLLEY_REQUEST, "registerAlias :" + registerAlias +
                        " registerPassword :" + registerPassword + " accountType :" + accountType);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
