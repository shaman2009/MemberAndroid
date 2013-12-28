package com.dandelion.memberandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.squareup.picasso.Picasso;

/**
 * Created by FengxiangZhu on 13-12-28.
 */
public class PostFeedFragment extends Fragment {

    private EditText editText;
    private Button postButton;
    private Button uploadButton;
    private ImageView imageView;
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


        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Picasso.with(getActivity()).load(data.getData()).into(imageView);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initWidget() {
        editText = (EditText)getActivity().findViewById(R.id.post_content);
        postButton = (Button) getActivity().findViewById(R.id.button_submit);
        uploadButton = (Button) getActivity().findViewById(R.id.buttton_upload_post_image);
        imageView = (ImageView) getActivity().findViewById(R.id.imageView_post_feed);
    }
    public void popButtons() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        in.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

}