package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.appketquaxoso.BuildConfig;

public class SharedPreferencesManager {
    private static final String PREF_FIRST_TIME_SETUP = BuildConfig.APPLICATION_ID + ".pref_first_time_setup";
    private static final String PREF_USER="USER";


    private static SharedPreferences sPreferences;

    private SharedPreferencesManager() {
    }

    public static void init(Context context) {
        if (sPreferences == null) {
            sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static void setFirstTimeSetup(boolean isFirstTime) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(PREF_FIRST_TIME_SETUP, isFirstTime);
        editor.apply();
    }

    public static boolean isFirstTimeSetup() {
        return sPreferences.getBoolean(PREF_FIRST_TIME_SETUP, true);
    }
    public static void setPrefUser(boolean isFirstTime) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(PREF_USER, isFirstTime);
        editor.apply();
    }

    public static boolean isPrefUser() {
        return sPreferences.getBoolean(PREF_USER, true);
    }

    public static void setInfoUser(String username){
        SharedPreferences.Editor editor=sPreferences.edit();
        editor.putString("USER",username);
        editor.apply();
    }
    public static String getInfoUser(){
        return sPreferences.getString("USER","");
    }
}
