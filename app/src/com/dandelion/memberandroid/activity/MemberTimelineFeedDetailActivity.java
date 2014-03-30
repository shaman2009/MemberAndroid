package com.dandelion.memberandroid.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dandelion.memberandroid.R;
import com.squareup.picasso.Picasso;

/**
 * Created by FengxiangZhu on 13-12-30.
 */
public class MemberTimelineFeedDetailActivity extends FragmentActivity {

    public static final String FEED_MERCHANT_NAME = "feed:merchantName";
    public static final String FEED_TITLE = "feed:title";
    public static final String FEED_IMAGE_URL = "feed:imageName";
    public static final String FEED_CONTENT = "feed:content";

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.fragment_member_timeline_feed_detail);


        TextView merchantNameView = (TextView) activity.findViewById(R.id.text_timeline_feed_detail_merchant_name);
        TextView titleView = (TextView) activity.findViewById(R.id.text_timeline_feed_detail_title);
        TextView contentView = (TextView) activity.findViewById(R.id.text_timeline_feed_detail_content);
        ImageView imageView = (ImageView) activity.findViewById(R.id.image_timeline_feed_detail);
        //TODO
        Button shareButton = (Button) activity.findViewById(R.id.button_timeline_share_info_detail);

        Bundle arguments = this.getIntent().getExtras();
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
        // set the Above View

        setupActionBar();

    }




    public void share(String text) {
        String subject = "����";
        /*
		 * Android�Ļ��ƣ� ÿ�ΰ�װһ���µ�app��ʱ�� app����ϵͳע�� ����˭���ܸ�ʲô�¶� �ҿ����ṩ��Щ�ӿ�
		 * Ȼ����������������ϵͳ���� ��Щapp��ACTION_SEND������ܣ�Ȼ��ϵͳ�������Щ���ܵ�app������
		 * ���ǵ��share�Ժ�����Ľ��� ����ϵͳ�Ѿ�ʵ�ֺõĹ�����
		 */
        Intent intent = new Intent(Intent.ACTION_SEND);
        // ���ﴫ���subject���Ƕ�Ӧedittext������ı� ��Ϊ����Ժ��¶Ի���ı���
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.setType("text/plain");
        // ���ﴫ���text���Ƕ�Ӧedittext������ı� ��Ӧ���ô���Ĳ���
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // ����һ���µ�activity ���ǿ����� �ɹ����ñ��app��
        activity.startActivity(Intent.createChooser(intent, subject));
    }




    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
