package com.dam2.clickneat.utils;

import com.dam2.clickneat.serializers.DateJsonSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by ferna on 27/04/2017.
 */

public class JsonHelper {

    /**
     *
     * @param o Objeto a convertir a JSON
     * @return String
     */
    public static String toJson(Object o) {

        GsonBuilder builder = new GsonBuilder();

        builder.serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Date.class, new DateJsonSerializer()).create();

        Gson gson = builder.create();

        return gson.toJson(o);
    }

    /**
     *
     * @param json JSON
     * @param type Type
     * @return Object
     */
    public static Object fromJson(String json, Type type ) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson           = builder.registerTypeAdapter(Date.class, new DateJsonSerializer()).create();

        return gson.fromJson(json, type);
    }

    /**
     *
     * @param json JSON del cual queremos conocer su clase
     * @return String JSONObject, JSONArray, String, etc...
     */
    public static String getClassNameForJson(String json)  {

        try {

            return new JSONTokener(json).nextValue().getClass().getSimpleName();
        }
        catch ( Exception ex ){}

        return null;

    }


}
