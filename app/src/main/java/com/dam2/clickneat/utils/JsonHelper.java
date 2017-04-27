package com.dam2.clickneat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ferna on 27/04/2017.
 */

public class JsonHelper {

    public static String toJson(Object o) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(o);
    }
}
