package com.example.forhad.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Smartnjazzy on 8/30/2015.
 */
public class SharePref {

    public static final String PREF = "pref";

    public static final String FIRST_USER = "first_user";



    public static final String PREVIOUS_USER = "previous_user";
    public static final String LOGGED_IN = "logged_in";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ALERTSHOWN = "alertshown";


    SharedPreferences settings;


    public SharePref(Context mContext) {
        settings = mContext.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }


    public int getshareprefdata(String KEY) {
        return settings.getInt(KEY, 160);
    }

    public void setshareprefdata(String KEY, int value) {

        settings.edit().putInt(KEY, value).commit();
    }


    public String getshareprefdatastring(String KEY) {
        return settings.getString(KEY, "");
    }

    public void setshareprefdatastring(String KEY, String values) {

        settings.edit().putString(KEY, values).commit();
    }

    public Boolean getshareprefdataBoolean(String KEY) {
        return settings.getBoolean(KEY, false);
    }

    public void setshareprefdataBoolean(String KEY, Boolean values) {

        settings.edit().putBoolean(KEY, values).commit();
    }
}
