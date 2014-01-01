package com.dandelion.memberandroid.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.dandelion.memberandroid.constant.WebserviceConstant;

import org.json.JSONException;
import org.json.JSONObject;

public class MemberappApi {



	public static void register(String alias, String email, String password, int accountType, Response.Listener<String> listener, Response.ErrorListener errorListener) throws JSONException {
		JSONObject j = new JSONObject();
		j.put("alias", alias);
		j.put("email", email);
		j.put("password", password);
		j.put("accountType", accountType);
		HttpRequestViaVolley.httpRequest(WebserviceConstant.REGISTER_URI, WebserviceConstant.REGISTER_METHOD, j.toString(), listener, errorListener);
	}

    public static void login(String email, String password, String packageName, String identifier,Response.Listener<String> listener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("packageName", packageName);
        j.put("email", email);
        j.put("password", password);
        j.put("identifier", identifier);
        HttpRequestViaVolley.httpRequest(WebserviceConstant.LOGIN_URI, WebserviceConstant.LOGIN_METHOD, j.toString(), listener, errorListener);
    }

    public static void follow(Long targetUserId, String sid, Response.Listener<String> listener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("sid", sid);
        HttpRequestViaVolley.httpRequest(WebserviceConstant.FRIENDS_URI + "/" + targetUserId, Request.Method.PUT, j.toString(), listener, errorListener);
    }


    public static void getMerchantInfoByUserId(Long targetUserId,String sid, Response.Listener<String> listener, Response.ErrorListener errorListener){
        JSONObject j = new JSONObject();
        try {
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpGet(WebserviceConstant.MERCHANT_URI + "/" + targetUserId, j.toString(), listener, errorListener);
    }
    public static void getMerchantInfoByMerchantId(Long merchantId,String sid, Response.Listener<String> listener, Response.ErrorListener errorListener){
        JSONObject j = new JSONObject();
        try {
            j.put("merchantId", merchantId);
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpGet(WebserviceConstant.MERCHANT_URI, j.toString(), listener, errorListener);
    }

    public static void postFeed(String content, String title, String imageURL,String sid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        JSONObject j = new JSONObject();
        try {
            j.put("content", content);
            j.put("title", title);
            j.put("imageURL", imageURL);
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpRequest(WebserviceConstant.FEED_URI, Request.Method.POST, j.toString(), listener, errorListener);
    }
    public static void getTimeline(Long id, String sid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        JSONObject j = new JSONObject();
        try {
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpGet(WebserviceConstant.TIMELINE_URI + "/Accounts/" + id, j.toString(), listener, errorListener);
    }
}
