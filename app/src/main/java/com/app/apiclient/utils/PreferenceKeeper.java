package com.app.apiclient.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.apiclient.App;

/**
 * Class is used to save user data in preference.
 */
public class PreferenceKeeper {

    private static PreferenceKeeper keeper;
    private SharedPreferences prefs;

    private PreferenceKeeper(Context context) {
        if (context != null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceKeeper getInstance() {
        if (keeper == null) {
            keeper = new PreferenceKeeper(App.getInstance());
        }
        return keeper;
    }

    public String getAccessToken() {
        String accessToken = prefs.getString(AppConstant.PKN.ACCESS_TOKEN, "");
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        prefs.edit().putString(AppConstant.PKN.ACCESS_TOKEN, accessToken).apply();
    }
}
