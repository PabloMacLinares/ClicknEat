package com.dam2.clickneat.utils;

import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by ferna on 28/04/2017.
 */

public class BufferHelper {

    /**
     *
     * @param input Buffer de entrada del cual queremos obtener la informacion
     * @return String
     */
    public static String readInputStream(InputStream input) {

        try {

            //Generamos un buffer donde almacenamos los bytes que queremos leer
            InputStream in          = new BufferedInputStream(input);
            //Generamos el lector que nos permitira leer los datos del buffer
            BufferedReader reader   = new BufferedReader(new InputStreamReader(in));

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();
            in.close();

            return result.toString();

        } catch( IOException e){}

        return null;
    }

    /**
     *
     * @param output Buffer de salida donde volcaremos la informacion almacenada en la variable data
     * @param data String
     */
    public static void writeOutputStream(OutputStream output, String data){


        try {

            OutputStream out = new BufferedOutputStream(output);

            out.write(data.getBytes());
            out.flush();
            out.close();

        } catch ( IOException io){
            System.out.println(io.getMessage());
        }
    }
}
