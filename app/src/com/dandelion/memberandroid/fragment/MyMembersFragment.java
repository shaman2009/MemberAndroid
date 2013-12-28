package com.dandelion.memberandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.adapter.MyMembersListAdapter;

public class MyMembersFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_members, container, false);
    }

    @Override
    public void onStart() {
        final MyMembersListAdapter myMembersListAdapter = new MyMembersListAdapter(getActivity());
        ListView listView = (ListView) getActivity().findViewById(R.id.my_members_list);
        listView.setAdapter(myMembersListAdapter);
        listView.setFastScrollEnabled(true);


        super.onStart();
    }

}
