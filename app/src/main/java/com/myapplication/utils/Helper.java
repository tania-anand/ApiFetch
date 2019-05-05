package com.myapplication.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.myapplication.interfaces.MyResponseConnectivity;

public class Helper {

    private static MyResponseConnectivity myResponseConnectivity;

    public Helper(MyResponseConnectivity listener){
        myResponseConnectivity = listener;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cmgr;
        cmgr = (ConnectivityManager)context .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cmgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void showMsg(final Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("No Internet Connectivity. Please enable and Retry");
        dialog.setCancelable(false);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity)context).finish();
            }
        });
        dialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (isNetworkConnected(context)) {
                    myResponseConnectivity.onMyResponseConnectivity(1);
                } else {
                    myResponseConnectivity.onMyResponseConnectivity(0);
                }
            }
        });
        dialog.create().show();
    }


}
