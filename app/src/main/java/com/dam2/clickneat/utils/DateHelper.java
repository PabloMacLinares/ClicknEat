package com.dam2.clickneat.utils;

import com.google.gson.JsonElement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ferna on 24/05/2017.
 */

public class DateHelper {

    public static final int DATE_TYPE = 0;
    public static final int HOUR_TYPE = 1;

    public static Date dateFromString(String date, int type ) {

        String pattern = type == HOUR_TYPE ? "HH:mm" : "dd-MM-yyyy";

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        try {

            return formatter.parse(date);
        }
        catch ( Exception e ){
            System.err.println(e.getMessage());
        }

        return Calendar.getInstance().getTime();
    }

}
