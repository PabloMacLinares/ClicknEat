package com.dam2.clickneat.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        //Peticiones a la API atraves del modo especial
        if ( value.contains("?") ) {

            value  = value.split("\\?")[0];
        }

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

    /**
     * @param date Fecha para formatear a dia-mes-anio
     * @return String
     */
    public static String dateToString(Date date) {

        SimpleDateFormat formatter;

        Calendar calendar = Calendar.getInstance();
        Calendar now      = Calendar.getInstance();

        calendar.setTime(date);

        if ( Integer.compare(calendar.get(Calendar.DAY_OF_YEAR), now.get(Calendar.DAY_OF_YEAR) ) != 0 ) {

            formatter  = new SimpleDateFormat("dd-MM-yyyy");
        }
        else {

            formatter  = new SimpleDateFormat("HH:mm");
        }
        return formatter.format(date);
    }

    /**
     * @param date Fecha para formatear a h-m-s
     * @return String
     */
    public static String hourToString(Date date) {

        SimpleDateFormat formatter  = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * @param date Fecha para formatear a dia-mes-anio h-m-s
     * @return String
     */
    public static String dateFullToString(Date date) {

        SimpleDateFormat formatter  = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public static String dateToStringPublicacion(Date date) {

        SimpleDateFormat formatter  = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    public static boolean isStringUrl( String url ) {

        try {

            URL myUrl = new URL(url);

            return true;

        } catch (MalformedURLException e) {

            return false;
        }
    }
}
