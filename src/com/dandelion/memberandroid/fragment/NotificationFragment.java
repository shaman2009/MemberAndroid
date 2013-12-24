package com.dandelion.memberandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.activity.SlidingmenuActivity;
import com.dandelion.memberandroid.adapter.NotificationListAdapter;

public class NotificationFragment extends Fragment{
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_notification_list, container, false);
	}
	@Override
	public void onStart() {
		final NotificationListAdapter notificationListAdapter = new NotificationListAdapter(getActivity());
		ListView listView = (ListView)getActivity().findViewById(R.id.notification_list);
		listView.setAdapter(notificationListAdapter);
		listView.setFastScrollEnabled(true);
		
		
		super.onStart();
	}
	
}