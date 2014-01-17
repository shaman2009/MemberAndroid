package com.dandelion.memberandroid.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.dandelion.memberandroid.constant.WebserviceConstant;
import com.dandelion.memberandroid.dao.auto.MemberInfo;
import com.dandelion.memberandroid.dao.auto.MerchantInfo;
import com.dandelion.memberandroid.model.MemberDataResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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
        HttpRequestViaVolley.httpRequest(WebserviceConstant.FRIENDS_URI + "/" + targetUserId, Request.Method.POST, j.toString(), listener, errorListener);
    }

    public static void updateMemberInfo(Long friendId, String sid, MemberDataResponse memberDataResponse, Response.Listener<String> listener, Response.ErrorListener errorListener) throws JSONException {
        long score = memberDataResponse.getScore();
        long amount = memberDataResponse.getAmount();
        long amountcount = memberDataResponse.getAmountcount();
        JSONObject j = new JSONObject();
        j.put("sid", sid);
        if (score != 0) {
            j.put("score", score);
        }
        if (amount != 0) {
            j.put("amount", amount);
        }
        if (amountcount != 0) {
            j.put("amountcount", amountcount);
        }
        HttpRequestViaVolley.httpRequest(WebserviceConstant.FRIENDS_URI + "/" + friendId, Request.Method.PUT, j.toString(), listener, errorListener);
    }

    //Merchant
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


    public static void updateMerchantInfoByUserId(MerchantInfo merchantInfo,String sid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        JSONObject j = new JSONObject();
        String avatarUrl = merchantInfo.getAvatarurl();
        String name = merchantInfo.getName();
        String address = merchantInfo.getAddress();
        String phone = merchantInfo.getPhone();
        String email = merchantInfo.getEmail();
        String merchantType = merchantInfo.getMerchanttype();
        String introduction = merchantInfo.getIntroduction();
        boolean nameRequired = merchantInfo.getNamerequired();
        boolean sexRequired = merchantInfo.getSexrequired();
        boolean phoneRequired = merchantInfo.getPhonerequired();
        boolean addressRequired = merchantInfo.getAddressrequired();
        boolean emailRequired = merchantInfo.getEmailrequired();
        boolean birthdayRequired = merchantInfo.getBirthdayrequired();
        boolean memberSetting = merchantInfo.getMembersetting();
        boolean scorePlan = merchantInfo.getScoreplan();
        int amountRequired = merchantInfo.getAmountrequired();
        int amountCountRequired = merchantInfo.getAmountcountrequired();
        long userId = merchantInfo.getUseridfk();
        //TODO BACKGROUNDURL
        try {
            if (!"".equals(avatarUrl) && avatarUrl != null) {
                j.put("avatarUrl", avatarUrl);
            }
            if (!"".equals(name) && name != null) {
                j.put("name", name);
            }
            if (!"".equals(address) && address != null) {
                j.put("address", address);
            }
            if (!"".equals(phone) && phone != null) {
                j.put("phone", phone);
            }
            if (!"".equals(email) && email != null) {
                j.put("email", email);
            }
            if (!"".equals(merchantType) && merchantType != null) {
                j.put("merchantType", merchantType);
            }
            if (!"".equals(introduction) && introduction != null) {
                j.put("introduction", introduction);
            }
            if (amountRequired != 0) {
                j.put("amountRequired", amountRequired);
            }
            if (amountCountRequired != 0) {
                j.put("amountCountRequired", amountCountRequired);
            }
            j.put("nameRequired", nameRequired);
            j.put("sexRequired", sexRequired);
            j.put("phoneRequired", phoneRequired);
            j.put("addressRequired", addressRequired);
            j.put("emailRequired", emailRequired);
            j.put("birthdayRequired", birthdayRequired);
            j.put("memberSetting", memberSetting);
            j.put("scorePlan", scorePlan);
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpRequest(WebserviceConstant.MERCHANT_URI + "/" + userId, Request.Method.PUT, j.toString(), listener, errorListener);
    }
    //Member
    public static void getMemberInfoByUserId(Long targetUserId,String sid, Response.Listener<String> listener, Response.ErrorListener errorListener){
        JSONObject j = new JSONObject();
        try {
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpGet(WebserviceConstant.MEMBER_URI + "/" + targetUserId, j.toString(), listener, errorListener);
    }
    public static void updateMemberInfoByUserId(MemberInfo memberInfo, String sid, Response.Listener<String> listener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject j = new JSONObject();
        String avatarUrl = memberInfo.getAvatarurl();
        String name = memberInfo.getName();
        String address = memberInfo.getAddress();
        String phone = memberInfo.getPhone();
        long birthday = memberInfo.getBirthday().getTime();
        int sex = memberInfo.getSex();
        long userId = memberInfo.getUserId();
        if (!"".equals(avatarUrl) && avatarUrl != null) {
            j.put("avatarUrl", avatarUrl);
        }
        if (!"".equals(name) && name != null) {
            j.put("name", name);
        }
        if (!"".equals(address) && address != null) {
            j.put("address", address);
        }
        if (!"".equals(phone) && phone != null) {
            j.put("phone", phone);
        }
        if (!"".equals(name) && name != null) {
            j.put("name", name);
        }
        if (sex != 0) {
            j.put("sex", sex);
        }
        if (birthday != 0) {
            j.put("birthday", birthday);
        }
        j.put("sid", sid);
        HttpRequestViaVolley.httpRequest(WebserviceConstant.MEMBER_URI + "/" + userId, Request.Method.PUT, j.toString(), listener, errorListener);

    }

    //Feed
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
        HttpRequestViaVolley.httpGet(WebserviceConstant.TIMELINE_URI, j.toString(), listener, errorListener);
    }
    public static void getMyPosts(String sid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        JSONObject j = new JSONObject();
        try {
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpGet(WebserviceConstant.MY_POSTS_URI, j.toString(), listener, errorListener);
    }


    //NotificationDataResponse

    public static void getNotification(Long id, String sid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        JSONObject j = new JSONObject();
        try {
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpGet(WebserviceConstant.NOTIFICATION_URI + "/Accounts/" + id, j.toString(), listener, errorListener);
    }
    public static void getMyMembers(String sid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        JSONObject j = new JSONObject();
        try {
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpGet(WebserviceConstant.MY_MEMBERS_URI, j.toString(), listener, errorListener);
    }
    public static void getMyMerchants(String sid, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        JSONObject j = new JSONObject();
        try {
            j.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestViaVolley.httpGet(WebserviceConstant.MY_MERCHANTS_URI, j.toString(), listener, errorListener);
    }


}
