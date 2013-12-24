/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dandelion.memberandroid.volley;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


/**
 * Demonstrates how to send GET and POST parameters.
 * Values of the EditText fields are send to the server, they are added and the sum is returned as result.
 * If you use {@see ExtHttpClientStack} as in {@see Act_NewHttpClient} you may use URIBuilder (which is
 * present only in newer versions of HttpClient)
 * 
 * @author Ognyan Bankov
 * 
 */
public class HttpRequestViaVolley {
	public static void httpGet(String url, String j, Response.Listener<String> listener, Response.ErrorListener errorListener) {
		
        RequestQueue queue = MyVolley.getRequestQueue();

        if (j != null && !"".equals(j)) {
            String uri = String.format("%1$s?j=%2$s", url, j);
            StringRequest myReq = new StringRequest(Method.GET,
                                                    uri,
                                                    listener,
                                                    errorListener);
            queue.add(myReq);
        }
		
	}
     
	public static void httpRequest(String url, int method, final String j, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = MyVolley.getRequestQueue();

        if (j != null && !"".equals(j)) {
            StringRequest myReq = new StringRequest(method,
            										url,
            										listener,
            										errorListener) {
            	@Override
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("j", j);
                    return params;
                };
            };
            queue.add(myReq);
            
        }
		
		
	}
    


    private Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	//TODO
            }
        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	//TODO
            }
        };
    }


}
