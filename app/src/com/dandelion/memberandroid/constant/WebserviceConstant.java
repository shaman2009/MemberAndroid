package com.dandelion.memberandroid.constant;

import com.android.volley.Request.Method;

public class WebserviceConstant {
	public static final String HOST = "http://memberappwebservice.duapp.com";
	
	
	public static final String REGISTER_URI = HOST + "/Register";
	public static final int REGISTER_METHOD = Method.POST;
	
	
	public static final String LOGIN_URI = HOST + "/Login";
	public static final int LOGIN_METHOD = Method.POST;

    public static final String MERCHANT_URI = HOST + "/Merchants";

    public static final String FRIENDS_URI = HOST + "/Friends";

    public static final String FEED_URI = HOST + "/Feeds";

    public static final String TIMELINE_URI = HOST + "/Timeline";

    public static final String NOTIFICATION_URI = HOST + "/Notifications";

    public static String PACKAGE_NAME = "com.dandelion.memberandroid";






	public static final int ACCOUNT_TYPE_MERCHANT = 1;
	public static final int ACCOUNT_TYPE_MEMBER = 0;



    public static final String STAR = "*";
}
