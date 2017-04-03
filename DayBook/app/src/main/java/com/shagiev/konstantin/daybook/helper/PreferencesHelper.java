package com.shagiev.konstantin.daybook.helper;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

    public static final String SPLASH_IS_INVISIBLE = "splash_is_invisible";

    private static PreferencesHelper sInstance;

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    public PreferencesHelper() {
    }

    public static PreferencesHelper getInstance(){
        if(sInstance == null){
            sInstance = new PreferencesHelper();
        }
        return sInstance;
    }

    public void init(Context context){
        mContext = context;
        mSharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, boolean value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key){
        return mSharedPreferences.getBoolean(key, false);
    }
}
