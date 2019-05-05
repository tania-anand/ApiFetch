package com.myapplication.networkutil;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myapplication.interfaces.MyResponseParse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkCall {
    private static final String TAG = "NetworkCall";
    private RequestQueue requestQueue;

    static NetworkCall db = new NetworkCall();

    private NetworkCall() {
    }

    public void initRequestQueue(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static NetworkCall getMyDB(){
        return db;
    }


    public void processRequest(int method, String url, JSONObject jsonObject, final MyResponseParse myResponseParse) {
        VolleyLog.DEBUG = false;

        Log.i(TAG, "in process request: "+jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {


                Log.i(TAG, "onResponse: "+jsonObject);
                VolleyLog.DEBUG = false;

                try {
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("TABLE_DATA"));
                    ArrayList arrayList = new ParseResponse().getUserList(jsonObject1);
                    Log.d(TAG,"response array "+arrayList.toString());
                    myResponseParse.onMyResponseParser(arrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    myResponseParse.onFailure();
                }


            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError paramAnonymousVolleyError) {
                VolleyLog.DEBUG = false;
                myResponseParse.onFailure();

                Log.i(TAG,"Server Error: "+paramAnonymousVolleyError.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(120*1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Log.e(TAG, "processRequest: "+jsonObjectRequest.toString() );
        if (jsonObject!=null)
            Log.e(TAG, "processRequest: "+jsonObject.toString() );

        requestQueue.add(jsonObjectRequest);
    }
}
