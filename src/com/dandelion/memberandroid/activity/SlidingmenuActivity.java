package com.dandelion.memberandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.IntentConstant;
import com.dandelion.memberandroid.fragment.MemberMenuFragment;
import com.dandelion.memberandroid.fragment.MerchantMenuFragment;
import com.dandelion.memberandroid.fragment.MerchantRegisterFragment;
import com.dandelion.memberandroid.fragment.MyRecordFragment;
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
		// set the Above View
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		}
		boolean b = IntentConstant.MERCHANT.equals(this.getIntent().getExtras().get(IntentConstant.USERTYPE));
		
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

