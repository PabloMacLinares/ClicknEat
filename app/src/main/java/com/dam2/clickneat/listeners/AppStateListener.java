package com.dam2.clickneat.listeners;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *  Clase que nos permite conocer en cualquier momento en que estado se encuentra nuestra app
 *  (incluso desde un servicio). Podemos insertarle listener para que desde un servicio
 *  queramos que se ejecute una determinada accion cuando nuestra app se encuentra activa
 *  (isForeground = true) o cuando nuestra app se va a segundo plano ( isBackground = true)
 */
public class AppStateListener implements Application.ActivityLifecycleCallbacks {

    public static final long CHECK_DELAY = 500;
    public static final String TAG = AppStateListener.class.getName();

    public interface Listener {

        void onBecameForeground();

       void onBecameBackground();

    }

    private static AppStateListener instance;

    private boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList();
    private Runnable check;
    private Context context;

    /**
     * @param application
     * @return an initialised Foreground instance
     */
    public static AppStateListener init(Application application){
        if (instance == null) {
            instance = new AppStateListener();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public static AppStateListener get(Application application){
        if (instance == null) {
            init(application);
        }
        return instance;
    }

    public static AppStateListener get(Context ctx){
        if (instance == null) {
            Context appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application)appCtx);
            }
            throw new IllegalStateException(
                    "Foreground is not initialised and " +
                            "cannot obtain the Application object");
        }
        return instance;
    }

    public static AppStateListener get(){
        if (instance == null) {
            throw new IllegalStateException(
                    "Foreground is not initialised - invoke " +
                            "at least once with parameterised init/get");
        }
        return instance;
    }

    public boolean isForeground(){
        return foreground;
    }

    public boolean isBackground(){
        return !foreground;
    }

    public void addListener(Listener listener){
        listeners.add(listener);
    }

    public void removeListener(Listener listener){
        listeners.remove(listener);
    }

    public void setContext( Context context ) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    @Override
    public void onActivityResumed(Activity activity) {

        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;

        if (check != null)
            handler.removeCallbacks(check);

        if (wasBackground){
            Log.i(TAG, "went foreground");
            for (Listener l : listeners) {
                try {
                    l.onBecameForeground();
                } catch (Exception exc) {
                    Log.e(TAG, "Listener threw exception!", exc);
                }
            }
        } else {
            Log.i(TAG, "still foreground");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

        paused = true;

        if (check != null)
            handler.removeCallbacks(check);

        handler.postDelayed(check = new Runnable(){
            @Override
            public void run() {
                if (foreground && paused) {
                    foreground = false;
                    Log.i(TAG, "went background");
                    for (Listener l : listeners) {
                        try {
                            l.onBecameBackground();
                        } catch (Exception exc) {
                            Log.e(TAG, "Listener threw exception!", exc);
                        }
                    }
                } else {
                    Log.i(TAG, "still foreground");
                }
            }
        }, CHECK_DELAY);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}
}