package com.dandelion.memberandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.IntentConstant;
import com.dandelion.memberandroid.fragment.MemberMenuFragment;
import com.dandelion.memberandroid.fragment.MerchantMenuFragment;
import com.dandelion.memberandroid.fragment.MerchantRegisterFragment;
import com.dandelion.memberandroid.fragment.MyRecordFragment;
import com.dandelion.memberandroid.fragment.NotificationFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class SlidingmenuActivity extends BaseActivity {

	private Fragment mContent;

	/**
	 * @param titleRes
	 */
	public SlidingmenuActivity() {
		super(R.string.app_name);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String userType = (String) this.getIntent().getExtras().get(IntentConstant.USERTYPE);
		boolean b = IntentConstant.MERCHANT.equals(userType);
		// set the Above View
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new MyRecordFragment();	
		}
		
		setContentView(R.layout.content_frame);
		
        
        
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		if (b) {
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
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}


}

