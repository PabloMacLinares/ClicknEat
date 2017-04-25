package com.dam2.clickneat.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pablo on 25/04/2017.
 */

public class Preferences {

    public static final int DEFAULT_INTEGER = Integer.MIN_VALUE;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Preferences(Activity activity){
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setInteger(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInteger(String key){
        return sharedPreferences.getInt(key, DEFAULT_INTEGER);
    }
}
