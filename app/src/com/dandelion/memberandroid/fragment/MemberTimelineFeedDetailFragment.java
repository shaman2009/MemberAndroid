package com.dandelion.memberandroid.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dandelion.memberandroid.R;
import com.squareup.picasso.Picasso;

/**
 * Created by FengxiangZhu on 13-12-30.
 */
public class MemberTimelineFeedDetailFragment extends Fragment {
    public static final String FEED_MERCHANT_NAME = "feed:merchantName";
    public static final String FEED_TITLE = "feed:title";
    public static final String FEED_IMAGE_URL = "feed:imageName";
    public static final String FEED_CONTENT = "feed:content";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity activity = getActivity();

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.fragment_member_timeline_feed_detail, container, false);

        TextView merchantNameView = (TextView) view.findViewById(R.id.text_timeline_feed_detail_merchant_name);
        TextView titleView = (TextView) view.findViewById(R.id.text_timeline_feed_detail_title);
        TextView contentView = (TextView) view.findViewById(R.id.text_timeline_feed_detail_content);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_timeline_feed_detail);
        //TODO
        Button shareButton = (Button) view.findViewById(R.id.button_timeline_share_info_detail);

        Bundle arguments = getArguments();
        final String feedTitle = arguments.getString(FEED_TITLE);
        final String feedContent = arguments.getString(FEED_CONTENT);
        final String feedMerchantName = arguments.getString(FEED_MERCHANT_NAME);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(feedMerchantName + " - " + feedTitle + " - " + feedContent);
            }
        });

        merchantNameView.setText(feedMerchantName);
        titleView.setText(feedTitle);
        contentView.setText(feedContent);





        Picasso.with(activity)
                .load(arguments.getString(FEED_IMAGE_URL))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .resizeDimen(R.dimen.list_detail_big_image_size, R.dimen.list_detail_big_image_size)
                .centerInside()
                .into(imageView);
        Picasso.with(activity).setDebugging(true);
        return view;
    }

    public void share(String text) {
        String subject = "分享";
        /*
		 * Android的机制： 每次安装一个新的app的时候 app会向系统注册 我是谁我能干什么事儿 我可以提供哪些接口
		 * 然后这边这个方法就向系统请求 哪些app有ACTION_SEND这个功能，然后系统会把有这些功能的app返给我
		 * 就是点击share以后产生的界面 都是系统已经实现好的功能了
		 */
        Intent intent = new Intent(Intent.ACTION_SEND);
        // 这里传入的subject就是对应edittext里面的文本 作为点击以后新对话框的标题
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setType("text/plain");
        // 这里传入的text就是对应edittext里面的文本 对应调用传入的参数
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 启动一个新的activity 就是看到的 成功调用别的app了
        getActivity().startActivity(Intent.createChooser(intent, subject));
    }



}
