package com.dandelion.memberandroid.constant;

public class QiNiuConstant {
	public static final int PICK_PICTURE_RESUMABLE = 0;

	public static String BUCKET_NAME = "membershipapp";
	public static String DOMAIN = BUCKET_NAME + "qiniudn.com";
	// upToken 这里需要自行获取. 当token过期后才再获取一遍
	public static String UP_TOKEN = "7EAZL7bN77bzyRLd8_4aIBIVKC6J45hMQKulx69c:yYfp76zjB2MC10dyx33qn-3NriQ=:eyJzY29wZSI6Im1lbWJlcnNoaXBhcHAiLCJkZWFkbGluZSI6MTQyMzY4MDQ5OH0=";
	public static String DOWNLOAD_DOMAIN = BUCKET_NAME + ".u.qiniudn.com";
}
