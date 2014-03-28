package com.dandelion.memberandroid.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.fragment.MemberTimelineFeedDetailFragment;

/**
 * Created by FengxiangZhu on 13-12-30.
 */
public class MemberTimelineFeedDetailActivity extends FragmentActivity {
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;
        if (mContent == null) {
            mContent = new MemberTimelineFeedDetailFragment();
            mContent.setArguments(this.getIntent().getExtras());
        }




        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mContent).commit();
        // set the Above View
        setContentView(R.layout.content_frame);
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

}
