package com.dandelion.memberandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.ActionBar;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.fragment.MemberMenuFragment;
import com.dandelion.memberandroid.fragment.MemberTimelineFragment;
import com.dandelion.memberandroid.fragment.MerchantMenuFragment;
import com.dandelion.memberandroid.fragment.NotificationFragment;
import com.dandelion.memberandroid.service.AccountService;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

public class SlidingmenuActivity extends BaseActivity {

    private Fragment mContent;


    private AccountService accountService;
    private Account authAccount;


    public SlidingmenuActivity() {
        super(R.string.app_name);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the Above View
        if (savedInstanceState != null) {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        }


        accountService = new AccountService(this);
        authAccount = accountService.getAuthAccount();
        int accountType = authAccount.getAccountType();

        if (mContent == null) {
            if (WebserviceConstant.ACCOUNT_TYPE_MERCHANT == accountType) {
                mContent = new NotificationFragment();
            } else {
                mContent = new MemberTimelineFragment();
            }

        }

        setContentView(R.layout.content_frame);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mContent)
                .commit();

        if (WebserviceConstant.ACCOUNT_TYPE_MERCHANT == accountType) {
            // set the Behind View
            setBehindContentView(R.layout.menu_frame);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.menu_frame, new MerchantMenuFragment())
                    .commit();
        } else {
            setBehindContentView(R.layout.menu_frame);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.menu_frame, new MemberMenuFragment())
                    .commit();
        }
        // customize the SlidingMenu
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }

    public void switchContent(Fragment fragment) {
        mContent = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        getSlidingMenu().showContent();


//        if (fragment instanceof NotificationFragment || fragment instanceof MemberTimelineFragment) {
//            getSupportActionBar().setDisplayShowHomeEnabled(false);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//            if (getSupportActionBar().getTabCount() != 3) {
//                for (int i = 0; i <= 2; i++) {
//                    List<String> list = initTabNames();
//                    ActionBar.Tab tab = getSupportActionBar().newTab();
//                    tab.setText(list.get(i));
//                    tab.setTabListener(this);
//                    getSupportActionBar().addTab(tab);
//                }
//            }
//        } else {
//            getSupportActionBar().setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE);
//            getSupportActionBar().removeAllTabs();
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(true);
//            setTitle(R.string.app_name);
//        }


    }


}

