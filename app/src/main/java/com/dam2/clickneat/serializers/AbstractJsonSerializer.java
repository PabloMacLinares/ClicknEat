package com.dam2.clickneat.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by ferna on 29/04/2017.
 */

public abstract class AbstractJsonSerializer implements JsonSerializer, JsonDeserializer {

    @Override
    public abstract Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException;

    @Override
    public abstract JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context);

}