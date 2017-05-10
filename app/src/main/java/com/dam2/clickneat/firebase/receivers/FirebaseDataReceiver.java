package com.dam2.clickneat.firebase.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dam2.clickneat.listeners.AppStateListener;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.pojos.Mensaje;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.views.chats.ChatsView;
import com.dam2.clickneat.views.chats.chat.ChatView;

import java.util.HashMap;

/**
 * Created by ferna on 05/05/2017.
 */

/*
*   Broadcast que es llamado cuando la aplicacion se encuentra en primer plano y nos
*   permite actualizar la vista de la activity que se encuentra activa, siempre que
*   sea la que nosotros queramos
* */
public class FirebaseDataReceiver extends BroadcastReceiver {

    public static String DATA_RECEIVED              = "com.dam2.clickneat.firebase.receivers.DATA_RECEIVED";
    private static final String PACKAGE_VIEWS_NAME  = "com.dam2.clickneat.views";
    private HashMap<String, String> viewsName;

    @Override
    public void onReceive(Context context, Intent intent) {

        viewsName = new HashMap();
        viewsName.put("conversacion", PACKAGE_VIEWS_NAME + ".chats.ChatsView");
        viewsName.put("mensaje", PACKAGE_VIEWS_NAME + ".chats.chat.ChatView");
        /*viewsName.put("reserva", PACKAGE_VIEWS_NAME + ".chats.ChatsView");
        viewsName.put("comentario", PACKAGE_VIEWS_NAME + ".chats.ChatsView");*/

        try {

            String type     = intent.getExtras().getString("tipo").toLowerCase();

            //Este es un caso especifico para los mensajes ya que puede interactuar con las vistas de chats y de chat
            if ( type.contains("conversacion") ) {

                String[] typesConversacion = type.split("-");
                for ( String typeConversacion : typesConversacion ) {

                    checkActivityIsRunning(context, intent, typeConversacion);
                }
            }
            else {

                checkActivityIsRunning(context, intent, type);
            }



        }
        catch( Exception e ) {
            System.err.println(e.getMessage());
        }

    }

    private void checkActivityIsRunning( Context context,  Intent intent, String type) {

        if ( viewsName.containsKey(type) ) {

            Preferences preferences = new Preferences(context);
            String className        = viewsName.get(type);

            //Comprobamos si la activity esta activa
            boolean isActive        = preferences.getBoolean(className);
            Context contextActivity = AppStateListener.get().getContext();

            if ( isActive ) {

                switch ( className ) {

                    case PACKAGE_VIEWS_NAME + ".chats.ChatsView" : {

                        ConversacionMetadata metadata = intent.getExtras().getParcelable("conversacion");
                        ((ChatsView)contextActivity).viewUpdateConversacion(metadata);

                        break;
                    }

                    case PACKAGE_VIEWS_NAME + ".chats.chat.ChatView": {

                        ConversacionMetadata metadata = intent.getExtras().getParcelable("conversacion");
                        Mensaje mensaje = intent.getExtras().getParcelable("mensaje");

                        ((ChatView)contextActivity).viewMensaje(mensaje);
                        ((ChatView)contextActivity).viewConversacionMetadata(metadata);

                        break;
                    }
                }
            }
        }
    }
}
