package com.dam2.clickneat.utils;

import com.auth0.android.jwt.JWT;

/**
 * Created by ferna on 12/05/2017.
 */

public class JwtHelper {


    public static Object getElementFromToken( String token, String field, Class classe ) {

        JWT jwt;

        try {

            jwt = new JWT(token);

            switch ( classe.getSimpleName() ) {

                case "String": {

                    return jwt.getClaim(field).asString();
                }

                case "Integer": {

                    return jwt.getClaim(field).asInt();
                }

                case "Double": {

                    return jwt.getClaim(field).asDouble();
                }

                case "Date": {

                    return jwt.getClaim(field).asDate();
                }

                default: {

                    return null;
                }

            }

        }
        catch( Exception exception ){}

        return null;
    }
}
