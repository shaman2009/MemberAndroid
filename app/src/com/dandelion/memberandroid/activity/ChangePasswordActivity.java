package com.dandelion.memberandroid.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.util.Md5;
import com.dandelion.memberandroid.volley.MemberappApi;

/**
 * Created by ouroc on 3/28/14.
 */
public class ChangePasswordActivity extends Activity {


    private TextView text_old_password;
    private TextView text_new_password;
    private Button button_change;

    private String oldPassword;
    private String newPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_change_password);


        initWidget();
        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptChange();
            }
        });
        // Show the Up button in the action bar.
        setupActionBar();
    }


    private void initWidget() {
        text_old_password = (TextView) this.findViewById(R.id.old_password);
        text_new_password = (TextView) this.findViewById(R.id.new_password);
        button_change = (Button) this.findViewById(R.id.action_change_password);
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    public void attemptChange() {
        // Reset errors.
        text_old_password.setError(null);
        text_new_password.setError(null);
        // Store values at the time of the login attempt.
        oldPassword = Md5.GetMD5Code(text_old_password.getText().toString());
        newPassword = Md5.GetMD5Code(text_new_password.getText().toString());

        AccountService service = new AccountService(this);
        Account account = service.getAuthAccount();
        final String sid = account.getSid();
        final long userId = account.getUsdId();


        Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(LoggerConstant.VOLLEY_REQUEST, response);
                changeSuccess();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                changeFailed();
            }
        };
        MemberappApi.changePassword(userId, oldPassword, newPassword, sid, listener, errorListener);

    }
    public void changeFailed() {
        Toast.makeText(this, R.string.toast_message_change_password_failed,
                Toast.LENGTH_LONG).show();
        text_old_password.setError(getString(R.string.error_incorrect_password));
        text_old_password.requestFocus();
    }
    public void changeSuccess() {
        new AlertDialog.Builder(this)
                .setMessage(this.getString(R.string.toast_message_change_password_success))
                .setNeutralButton(this.getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
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


}