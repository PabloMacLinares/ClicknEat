package com.dam2.clickneat.utils;

/**
 * Created by ferna on 29/04/2017.
 */

public class StringHelper {

    /**
     * @param value String para convertir al formato CamelCase
     * @return String
     */
    public static String stringToCamelCase(String value){

        if ( value == null ) return null;

        //En Primer lugar nos aseguramos que todas las letras estan en minusculas
        value = value.toLowerCase();

        //Eliminamos los caracteres raros y los sustituimos por espacios
        value = value.replaceAll("[^a-zA-Z0-9]", " ");

        StringBuilder str = new StringBuilder();

        for ( String word : value.split(" ")) {

            String fLetter = word.substring(0, 1).toUpperCase();
            String lLetter = word.substring(1);

            str.append(fLetter).append(lLetter);
        }

        return str.toString();
    }
}
