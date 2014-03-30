package com.dandelion.memberandroid.activity;

/**
 * Created by FengxiangZhu on 2014/3/27 0027.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.astuetz.PagerSlidingTabStrip;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.dao.auto.Account;
import com.dandelion.memberandroid.fragment.JoinMemberFragment;
import com.dandelion.memberandroid.fragment.MemberTimelineFragment;
import com.dandelion.memberandroid.fragment.MerchantMyRecordFragment;
import com.dandelion.memberandroid.fragment.MyMembersFragment;
import com.dandelion.memberandroid.fragment.MyMerchantsFragment;
import com.dandelion.memberandroid.fragment.MyPostFragment;
import com.dandelion.memberandroid.fragment.NotificationFragment;
import com.dandelion.memberandroid.fragment.PasswordChangeFragment;
import com.dandelion.memberandroid.fragment.PostFeedFragment;
import com.dandelion.memberandroid.fragment.SuperAwesomeCardFragment;
import com.dandelion.memberandroid.service.AccountService;

import java.lang.reflect.Field;

public class MainActivity extends SherlockFragmentActivity {


    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    private AccountService accountService;
    private Account authAccount;
    private int accountType;

    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sherlock_Light);
        super.onCreate(savedInstanceState);
        activity = this;




        accountService = new AccountService(this);
        authAccount = accountService.getAuthAccount();
        accountType = authAccount.getAccountType();

        setContentView(R.layout.list_navigation);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

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
    public boolean onCreateOptionsMenu(Menu menu) {


        SubMenu subMenuPlus = menu.addSubMenu("Action Item");


        MenuItem subMenuPlusItem = subMenuPlus.getItem();
        subMenuPlusItem.setIcon(R.drawable.app_panel_add_icon);
        subMenuPlusItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        subMenuPlusItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(getBaseContext(), "掃一掃 仍然在開發中 ", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        SubMenu subMenu = menu.addSubMenu("Action Item");
        subMenu.add("我的檔案").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (WebserviceConstant.ACCOUNT_TYPE_MERCHANT == accountType) {
                    merchantRecord();
                } else {
                    memberRecord();
                }


                return false;
            }
        });
        subMenu.add("更改密碼").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                changePassword();
                return false;
            }
        });
        subMenu.add("登出").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                logout();
                return false;
            }
        });

        MenuItem subMenu1Item = subMenu.getItem();
        subMenu1Item.setIcon(R.drawable.abc_ic_menu_moreoverflow_normal_holo_light);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);






        return super.onCreateOptionsMenu(menu);
    }
    private void memberRecord() {
        Intent intent = new Intent(activity, MemberRecordActivity.class);
        activity.startActivity(intent);
    }
    private void merchantRecord() {
        Intent intent = new Intent(activity, MerchantRecordActivity.class);
        activity.startActivity(intent);
    }
    private void changePassword() {
        Intent intent = new Intent(activity, ChangePasswordActivity.class);
        activity.startActivity(intent);
    }
    
    public void logout() {
        new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.account_logout))
                .setMessage(activity.getString(R.string.account_logout_message))
                .setNegativeButton(activity.getString(R.string.account_logout_cancel), null)
                .setNeutralButton(activity.getString(R.string.account_logout_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccountService accountService = new AccountService(activity);
                        accountService.deleteAllAccounts();
                        Intent intent = new Intent(activity, LoginActivity.class);
                        startActivity(intent);
                        activity.finish();
                    }
                }).show();
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
                return new NotificationFragment();
            }
            if (position == 1) {
                return new PostFeedFragment();
            }
            if (position == 2) {
                return new MyMembersFragment();
            }
            return SuperAwesomeCardFragment.newInstance(position);
        }
    }
}
