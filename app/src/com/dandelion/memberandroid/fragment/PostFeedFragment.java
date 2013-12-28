package com.dandelion.memberandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dandelion.memberandroid.R;

/**
 * Created by FengxiangZhu on 13-12-28.
 */
public class PostFeedFragment extends Fragment {
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
        EditText editText = (EditText)getActivity().findViewById(R.id.post_content);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        in.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        super.onStart();
    }
}