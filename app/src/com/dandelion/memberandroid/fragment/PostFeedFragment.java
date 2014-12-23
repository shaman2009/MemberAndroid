package com.dandelion.memberandroid.fragment;

import android.app.AlertDialog;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.activity.HistoryPostAcitvity;
import com.dandelion.memberandroid.constant.LoggerConstant;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.dandelion.memberandroid.service.AccountService;
import com.dandelion.memberandroid.volley.MemberappApi;
//import com.qiniu.auth.JSONObjectRet;
//import com.qiniu.io.IO;
//import com.qiniu.io.PutExtra;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by FengxiangZhu on 13-12-28.
 */
public class PostFeedFragment extends Fragment {
    private EditText titleEditText;
    private EditText contentEditText;
    private Button postButton;
    private Button uploadButton;
    private Button historyButton;
    private ImageView imageView;

    //
    private Uri imageData;
    private String title;
    private String content;
    private String sid;
    private String imageKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_post_feed, container, false);
    }

    @Override
    public void onStart() {
        initWidget();
        popButtons();
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, QiNiuConstant.PICK_PICTURE_RESUMABLE);
                return;
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyFeed();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptPost();
            }
        });
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageKey = UUID.randomUUID().toString();
        imageData = data.getData();
        Picasso.with(getActivity()).load(imageData).into(imageView);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initWidget() {
        titleEditText = (EditText) getActivity().findViewById(R.id.post_title);
        contentEditText = (EditText) getActivity().findViewById(R.id.post_content);
        postButton = (Button) getActivity().findViewById(R.id.button_submit);
        uploadButton = (Button) getActivity().findViewById(R.id.buttton_upload_post_image);
        historyButton = (Button) getActivity().findViewById(R.id.button_history);
        imageView = (ImageView) getActivity().findViewById(R.id.imageView_post_feed);
    }
    public void historyFeed() {
        Intent intent = new Intent(getActivity(), HistoryPostAcitvity.class);
        getActivity().startActivity(intent);
    }

    public void popButtons() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        in.showSoftInput(contentEditText, InputMethodManager.SHOW_FORCED);
    }

    public void attemptPost() {
        // Reset errors.
        titleEditText.setError(null);
        contentEditText.setError(null);
        // Store values at the time of the login attempt.
        title = titleEditText.getText().toString();
        content = contentEditText.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(title)) {
            titleEditText.setError(getString(R.string.error_field_required));
            focusView = titleEditText;
            cancel = true;
        }
        if (TextUtils.isEmpty(content)) {
            contentEditText.setError(getString(R.string.error_field_required));
            focusView = contentEditText;
            cancel = true;
        }
        if (TextUtils.isEmpty(imageKey)) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(getActivity().getString(R.string.alert_post_null_image_error))
                    .setNeutralButton(getActivity().getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
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
            MemberappApi.postFeed(content, title, imageKey, sid, postListener, postErrorListener);
            doUpload(imageData);
        }
    }


    Response.Listener<String> postListener = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.d(LoggerConstant.VOLLEY_REQUEST, response);
            new AlertDialog.Builder(getActivity())
                    .setMessage(getActivity().getString(R.string.alert_post_success))
                    .setNeutralButton(getActivity().getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    };


    Response.ErrorListener postErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(getActivity().getString(R.string.alert_post_error))
                    .setNeutralButton(getActivity().getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    };
    boolean uploading = false;

    /**
     * 普通上傳文件
     *
     * @param uri
     */
    private void doUpload(Uri uri) {
//        if (uploading) {
//            return;
//        }
//
//        uploading = true;
//
//        // 上传文件名
//        PutExtra extra = new PutExtra();
//        extra.checkCrc = PutExtra.AUTO_CRC32;
//        extra.params.put("x:arg", "value");
//        IO.putFile(getActivity(), QiNiuConstant.UP_TOKEN, imageKey, uri, extra, new JSONObjectRet() {
//            @Override
//            public void onSuccess(JSONObject resp) {
//                uploading = false;
//                String redirect = QiNiuConstant.getImageDownloadURL(imageKey);
//                Log.d("QINIU_UPLOAD", "redirect : " + redirect);
//            }
//
//            @Override
//            public void onFailure(Exception ex) {
//                uploading = false;
//                new AlertDialog.Builder(getActivity())
//                        .setMessage(getActivity().getString(R.string.alert_post_upload_image_error))
//                        .setNeutralButton(getActivity().getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        }).show();
//            }
//        });
    }

}