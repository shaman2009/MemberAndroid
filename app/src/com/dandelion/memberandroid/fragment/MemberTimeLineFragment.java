package com.dandelion.memberandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.adapter.MemberTimelineListAdapter;

/**
 * Created by FengxiangZhu on 13-12-29.
 */
public class MemberTimelineFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_timeline_list, container, false);
    }

    @Override
    public void onStart() {
        final MemberTimelineListAdapter memberTimelineListAdapter = new MemberTimelineListAdapter(getActivity());
        ListView listView = (ListView) getActivity().findViewById(R.id.list_member_timeline_feed);
        listView.setAdapter(memberTimelineListAdapter);
        listView.setFastScrollEnabled(true);

        super.onStart();
    }


}