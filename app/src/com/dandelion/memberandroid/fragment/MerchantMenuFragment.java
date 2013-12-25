package com.dandelion.memberandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.activity.SlidingmenuActivity;

public class MerchantMenuFragment extends ListFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] colors = getResources().getStringArray(R.array.merchant_menu_names);
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, colors);
		setListAdapter(colorAdapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new MyRecordFragment();
			break;
		case 1:
			newContent = new NotificationFragment();
			break;
		case 2:
			newContent = new ColorFragment(android.R.color.white);
			break;
		case 3:
			newContent = new ColorFragment(android.R.color.white);
			break;
		case 4:
			newContent = new ColorFragment(android.R.color.white);
			break;
		case 5:
			newContent = new ColorFragment(android.R.color.white);
			break;
		case 6:
			newContent = new ColorFragment(android.R.color.white);
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof SlidingmenuActivity) {
			SlidingmenuActivity fca = (SlidingmenuActivity) getActivity();
			fca.switchContent(fragment);
		} 
	}
}
