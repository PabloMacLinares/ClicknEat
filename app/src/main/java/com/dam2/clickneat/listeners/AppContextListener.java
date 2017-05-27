package com.dam2.clickneat.listeners;

import android.app.Application;
import android.content.Context;

/**
 * Created by ferna on 21/05/2017.
 */

public class AppContextListener extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        AppContextListener.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AppContextListener.context;
    }


}
