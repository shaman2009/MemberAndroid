package com.dandelion.memberandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.adapter.MyPostListAdapter;

public class MyPostFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_post, container, false);
    }

    @Override
    public void onStart() {
        final MyPostListAdapter myPostListAdapter = new MyPostListAdapter(getActivity());
        ListView listView = (ListView) getActivity().findViewById(R.id.my_post_list);
        listView.setAdapter(myPostListAdapter);
        listView.setFastScrollEnabled(true);

        Button postButton = (Button) getActivity().findViewById(R.id.button_my_post);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFeed();
            }
        });
        super.onStart();
    }


    public void postFeed() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new PostFeedFragment())
                .addToBackStack(null)
                .commit();
    }
}
