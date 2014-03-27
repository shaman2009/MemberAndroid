package com.dandelion.memberandroid.activity;

/**
 * Created by FengxiangZhu on 2014/3/27 0027.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.astuetz.PagerSlidingTabStrip;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.fragment.JoinMemberFragment;
import com.dandelion.memberandroid.fragment.MemberTimelineFragment;
import com.dandelion.memberandroid.fragment.MyMerchantsFragment;
import com.dandelion.memberandroid.fragment.SuperAwesomeCardFragment;
import com.dandelion.memberandroid.service.AccountService;

public class ListViewActivity extends SherlockFragmentActivity
        implements ActionBar.OnNavigationListener {
    private TextView mSelected;
    private String[] mLocations;

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    private AccountService accountService;
    private Account authAccount;
    private int accountType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sherlock_Light);
        super.onCreate(savedInstanceState);

        accountService = new AccountService(this);
        authAccount = accountService.getAuthAccount();
        accountType = authAccount.getAccountType();

        setContentView(R.layout.list_navigation);
        mSelected = (TextView) findViewById(R.id.text);
        mLocations = getResources().getStringArray(R.array.locations);
        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context,
                R.array.locations, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        MerchantAdapter merchantAdapter;
        MemberAdapter memberAdapter;
        merchantAdapter = new MerchantAdapter(getSupportFragmentManager());
        memberAdapter = new MemberAdapter(getSupportFragmentManager());
        if (WebserviceConstant.ACCOUNT_TYPE_MERCHANT == accountType) {
            pager.setAdapter(merchantAdapter);
        } else {
            pager.setAdapter(memberAdapter);
        }

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        mSelected.setText("Selected: " + mLocations[itemPosition]);
        return true;
    }
    public void switchContent(Fragment fragment) {
    }
    public class MemberAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = {"優惠資訊", "加入會員", "我的商戶"};

        public MemberAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new MemberTimelineFragment();
            }
            if (position == 1) {
                return new JoinMemberFragment();
            }
            if (position == 2) {
                return new MyMerchantsFragment();
            }
            return SuperAwesomeCardFragment.newInstance(position);
        }
    }

    public class MerchantAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = {"系統通知", "我要發佈", "我的會員"};

        public MerchantAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new MemberTimelineFragment();
            }
            if (position == 1) {
                return new JoinMemberFragment();
            }
            if (position == 2) {
                return new MyMerchantsFragment();
            }
            return SuperAwesomeCardFragment.newInstance(position);
        }
    }
}
