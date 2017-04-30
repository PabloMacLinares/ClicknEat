package com.dam2.clickneat.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ferna on 29/04/2017.
 */

public class DateJsonSerializer extends AbstractJsonSerializer {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        try {

            String date = json.getAsString();

            if ( date != null ) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                return formatter.parse(date);
            }

        } catch( ParseException ex ){}


        return null;
    }

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {

        if ( src != null && src instanceof Date ) {

            SimpleDateFormat formatter  = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date                 = formatter.format((Date)src);

            return new JsonPrimitive(date);
        }
        return null;
    }
}
