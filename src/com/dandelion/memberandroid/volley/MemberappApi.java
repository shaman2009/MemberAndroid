package com.dandelion.memberandroid.volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dandelion.memberandroid.constant.WebserviceConstant;

public class MemberappApi {
	public static void register(String alias, String email, String password, int accountType, Response.Listener<String> listener, Response.ErrorListener errorListener) throws JSONException {
		JSONObject j = new JSONObject();
		j.put("alias", alias);
		j.put("email", email);
		j.put("password", password);
		j.put("accountType", accountType);
		HttpRequestViaVolley.httpRequest(WebserviceConstant.REGISTER_URI, WebserviceConstant.REGISTER_METHOD, j.toString(), listener, errorListener);
	}
}
