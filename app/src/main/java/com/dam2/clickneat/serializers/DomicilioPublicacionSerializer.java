package com.dam2.clickneat.serializers;

import com.dam2.clickneat.pojos.Domicilio;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ferna on 29/04/2017.
 */

/**
 * Serializador especifico para evitar errores en la actualizacion o insercion de publicaciones
 * en el servidor
 */
public class DomicilioPublicacionSerializer implements JsonSerializer<Domicilio> {

    @Override
    public JsonElement serialize(Domicilio src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getId());
    }

}
