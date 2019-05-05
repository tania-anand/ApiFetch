package com.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesUtil {

    private static SharedPreferencesUtil instance = null;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static SharedPreferencesUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesUtil();
            String SHARE_USER_INFO = "userinfo";
            preferences = context.getSharedPreferences(SHARE_USER_INFO, Context.MODE_PRIVATE);
            editor = preferences.edit();
            editor.apply();
        }
        return instance;
    }

    private static String isLoggedIn = "isLoggedIn";


    public void setIsLoggedIn(boolean loggedInValue) {
        Log.d("sharedPref","runs...");
        editor.putBoolean(isLoggedIn,loggedInValue);
        editor.commit();
    }
    public boolean getIsLoggedIn() {
        return preferences.getBoolean(isLoggedIn,false);
    }



}
