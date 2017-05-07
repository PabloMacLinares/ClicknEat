package com.dam2.clickneat.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by Pablo on 25/04/2017.
 */

/**
 * Cada clase que necesite usar Preferencias deberia crear su propio Handler
 */

public class Preferences {

    public static final int DEFAULT_INTEGER = Integer.MIN_VALUE;
    public static final float DEFAULT_FLOAT = Float.MIN_VALUE;
    public static final long DEFAULT_LONG = Long.MIN_VALUE;
    public static final boolean DEFAULT_BOOLEAN = Boolean.FALSE;
    public static final String DEFAULT_STRING = "";
    private static final String SHARED_PREFERENCES = "clickneat";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //Cambiado debido a que el servicio no posee Activity
    public Preferences(Context activity){

        sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setInteger(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInteger(String key){
        return sharedPreferences.getInt(key, DEFAULT_INTEGER);
    }

    public void setFloat(String key, float value){
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getFloat(String key){
        return sharedPreferences.getFloat(key, DEFAULT_FLOAT);
    }

    public void setLong(String key, long value){
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key){
        return sharedPreferences.getLong(key, DEFAULT_LONG);
    }

    public void setBoolean(String key, boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key, DEFAULT_BOOLEAN);
    }

    public void setString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key, DEFAULT_STRING);
    }

    public Map<String, ?> getAll(){
        return sharedPreferences.getAll();
    }

    /**
     * Esta función puede ser peligrosa, evitar su uso D:
     */
    public void clearAll(){
        editor.clear();
        editor.commit();
    }
}
