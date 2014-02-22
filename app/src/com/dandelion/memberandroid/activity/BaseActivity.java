package com.dandelion.memberandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.MenuItem;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.fragment.JoinMemberFragment;
import com.dandelion.memberandroid.fragment.MemberTimelineFragment;
import com.dandelion.memberandroid.fragment.MyMembersFragment;
import com.dandelion.memberandroid.fragment.MyMerchantsFragment;
import com.dandelion.memberandroid.fragment.MyPostFragment;
import com.dandelion.memberandroid.fragment.NotificationFragment;
import com.dandelion.memberandroid.fragment.PostFeedFragment;
import com.dandelion.memberandroid.fragment.SlidingFragment;
import com.dandelion.memberandroid.service.AccountService;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends SlidingFragmentActivity implements ActionBar.TabListener {

    private AccountService accountService;
    private Account authAccount;

    private int mTitleRes;
    protected ListFragment mFrag;

    public BaseActivity(int titleRes) {
        mTitleRes = titleRes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountService = new AccountService(this);
        authAccount = accountService.getAuthAccount();

        setTitle(mTitleRes);
        // set the Behind View
        setBehindContentView(R.layout.menu_frame);
        if ((ListFragment) this.getSupportFragmentManager().findFragmentById(R.id.menu_frame) == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            mFrag = new SlidingFragment();
            t.replace(R.id.menu_frame, mFrag);
            t.commit();
        } else {
            mFrag = (ListFragment) this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
        }


        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);


        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (int i = 0; i <= 2; i++) {
            List<String> list = initTabNames();

            ActionBar.Tab tab = getSupportActionBar().newTab();
            tab.setText(list.get(i));
            tab.setTabListener(this);
            getSupportActionBar().addTab(tab);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toggle();
        return true;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        int x = tab.getPosition();
        int accountType = authAccount.getAccountType();
        Fragment f = new NotificationFragment();
        if (WebserviceConstant.ACCOUNT_TYPE_MERCHANT == accountType) {
            if (x == 0) {
                f = new NotificationFragment();
            } else if (x == 1) {
                f = new PostFeedFragment();
            } else if (x == 2) {
                f = new MyMembersFragment();
            }
        } else {
            if (WebserviceConstant.ACCOUNT_TYPE_MEMBER == accountType) {
                if (x == 0) {
                    f = new MemberTimelineFragment();
                } else if (x == 1) {
                    f = new JoinMemberFragment();
                } else if (x == 2) {
                    f = new MyMerchantsFragment();
                }
            }
        }


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, f)
                .commit();
        getSlidingMenu().showContent();
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        int x = tab.getPosition();
        int accountType = authAccount.getAccountType();
        Fragment f = new NotificationFragment();
        if (WebserviceConstant.ACCOUNT_TYPE_MERCHANT == accountType) {
            if (x == 0) {
                f = new NotificationFragment();
            } else if (x == 1) {
                f = new MyPostFragment();
            } else if (x == 2) {
                f = new MyMembersFragment();
            }
        } else {
            if (WebserviceConstant.ACCOUNT_TYPE_MEMBER == accountType) {
                if (x == 0) {
                    f = new MemberTimelineFragment();
                } else if (x == 1) {
                    f = new JoinMemberFragment();
                } else if (x == 2) {
                    f = new MyMerchantsFragment();
                }
            }
        }


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, f)
                .commit();
        getSlidingMenu().showContent();
    }

    public List<String> initTabNames() {
        int accountType = authAccount.getAccountType();
        List<String> list = new ArrayList<String>();
        if (WebserviceConstant.ACCOUNT_TYPE_MERCHANT == accountType) {
            list.add(getString(R.string.tab_system_nofitication));
            list.add(getString(R.string.tab_post));
            list.add(getString(R.string.tab_my_members));
        } else if (WebserviceConstant.ACCOUNT_TYPE_MEMBER == accountType) {
            list.add(getString(R.string.tab_timeline));
            list.add(getString(R.string.tab_join_member));
            list.add(getString(R.string.tab_my_merchant));
        }

        return list;
    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getSupportMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
}