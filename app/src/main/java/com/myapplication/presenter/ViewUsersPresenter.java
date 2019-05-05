package com.myapplication.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.myapplication.interfaces.MyResponseConnectivity;
import com.myapplication.interfaces.MyResponseParse;
import com.myapplication.interfaces.ViewUsersContract;
import com.myapplication.model.User;
import com.myapplication.networkutil.NetworkCall;
import com.myapplication.utils.Constants;
import com.myapplication.utils.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewUsersPresenter implements ViewUsersContract.presenter{

    private ViewUsersContract.viewer viewer;
    private Context mContext;


    Helper helper;

    public ViewUsersPresenter(ViewUsersContract.viewer viewer, Context context) {
        this.viewer = viewer;
        mContext = context;
    }


    public void callUrl(){

        NetworkCall db = NetworkCall.getMyDB();
        db.initRequestQueue(mContext);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", Constants.userName);
            jsonObject.put("password", Constants.password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.processRequest(Request.Method.POST, Constants.getUserData, jsonObject, new MyResponseParse() {
            @Override
            public void onMyResponseParser(ArrayList arrayList) {
                ArrayList<User> arrayList1 = (ArrayList<User>)arrayList;
                viewer.setAdapter(arrayList1);
            }

            @Override
            public void onFailure() {
                viewer.onFailure();

            }
        });

    }


    public void callApi(){

        helper = new Helper(new MyResponseConnectivity() {
            @Override
            public void onMyResponseConnectivity(int i) {

                if(Helper.isNetworkConnected(mContext)){
                    callUrl();
                }else{
                    helper.showMsg(mContext);
                }

            }
        });


        if(Helper.isNetworkConnected(mContext)){
            callUrl();
        }else{
            helper.showMsg(mContext);
        }

    }



}
