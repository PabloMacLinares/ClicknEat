package com.dam2.clickneat.views;


import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.dam2.clickneat.R;
import com.dam2.clickneat.listeners.AppStateListener;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.firebase.receivers.FirebaseDataReceiver;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by ferna on 03/05/2017.
 */
/**
 * Activity utilizada para registrar el estado de nuestra app y conocer en que estado se encuentra.
 * Por otro lado tambien registra el broadcast utilizado para notificar a nuestra app desde el servicio de firebase.
 * Para conocer que activity se encuentra iniciada almacenamos en SP el nombre de la activity y su estado para
 * comprobar desde el servicio si se encuentra activa o no.
 *
 * Comprueba los permisos necesarios para utilizar nuestra app
 *
 * Nota: Si se necesita conocer el estado de la app o el estado de una activity, nuestra activity debe heredar
 * de esta
 */
public class BaseActivity extends AppCompatActivity {

    private FirebaseDataReceiver firebaseReceiver;
    private ArrayList<String> permissions;
    private static final int PERMISSION_ALL = 1;
    protected GoogleSignInOptions gso;
    protected GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Registramos nuestra app para poder conocer en que estado se encuentra
        AppStateListener.init(getApplication());

        //Inicializamos nuestro broadcast
        this.firebaseReceiver = new FirebaseDataReceiver();

        //Comprobamos los permisos que necesita nuestra app
        permissions = new ArrayList();
        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        permissions.add(Manifest.permission.INTERNET);

        if ( !hasPermissions() ) {
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), PERMISSION_ALL);
        }

        //Inicializamos los servicios de google

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        System.err.println(connectionResult.getErrorMessage());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    protected void onResume() {
        super.onResume();

        AppStateListener.get().setContext(this);
        registerReceiver(this.firebaseReceiver, new IntentFilter(FirebaseDataReceiver.DATA_RECEIVED));

    }

    @Override
    protected void onStart() {
        super.onStart();

        //En SP almacenamos el nombre de la clase que se encuentra activa para pasarle datos a traves del servicio de FCM
        Preferences p = new Preferences(this);
        p.setBoolean(this.getClass().getCanonicalName(), true);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //En SP almacenamos el nombre de la clase que se encuentra activa para pasarle datos a traves del servicio de FCM
        Preferences p = new Preferences(this);
        p.setBoolean(this.getClass().getCanonicalName(), false);

    }

    @Override
    protected void onPause() {
        super.onPause();

        AppStateListener.get().setContext(null);
        unregisterReceiver(this.firebaseReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public boolean hasPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
