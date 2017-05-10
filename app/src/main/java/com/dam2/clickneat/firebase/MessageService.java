package com.dam2.clickneat.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.dam2.clickneat.R;
import com.dam2.clickneat.firebase.receivers.FirebaseDataReceiver;
import com.dam2.clickneat.listeners.AppStateListener;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.pojos.Mensaje;
import com.dam2.clickneat.utils.JsonHelper;
import com.dam2.clickneat.views.chats.ChatsView;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by ferna on 01/05/2017.
 */
/**
 *  Servicio de Firebase que obtiene el mensaje enviado desde nuestro servidor.
 *  A partir del mismo obtiene la informacion y genera la notificacion y accion
 *  correspondiente!
 */
public class MessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> messageReceived = remoteMessage.getData();

        String title        = messageReceived.get("title");
        String littleText   = messageReceived.get("littleText");
        String bigText      = messageReceived.get("bigText");
        String type         = messageReceived.get("type");
        String data         = messageReceived.get("data");

        Intent i            = null;
        Bundle bundle       = new Bundle();
        Integer icon        = 0;

        //El intent siempre tiene cargado el tipo
        bundle.putString("tipo", type);

        switch ( type ) {

            case "Conversacion-Mensaje": {

                i = new Intent(this, ChatsView.class);

                if ( !data.trim().isEmpty() ) {

                    Type convType = new TypeToken<ConversacionMetadata>(){}.getType();
                    Type mensType = new TypeToken<Mensaje>(){}.getType();

                    try {

                        JSONObject jsonObject = new JSONObject(data);

                        ConversacionMetadata metadata = (ConversacionMetadata) JsonHelper.fromJson(jsonObject.getString("conversacion"), convType);
                        Mensaje mensaje               = (Mensaje)JsonHelper.fromJson(jsonObject.getString("mensaje"), mensType);

                        bundle.putParcelable("conversacion", metadata);
                        bundle.putParcelable("mensaje", mensaje);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                icon = R.drawable.ic_chat_white_24px;

                break;
            }

            case "Reserva": {

                icon = R.drawable.ic_restaurant_menu_white_24px;

                break;
            }

            case "Comentario": {

                icon = R.drawable.ic_comment_white_24px;

                break;
            }
        }

        sendNotification(i, title, littleText, bigText, icon);

        //Comprobamos si la app esta activa y si es así llamamos a nuestro broadcast
        if ( AppStateListener.get().isForeground() ) {

            Intent intentBroadcast = new Intent(FirebaseDataReceiver.DATA_RECEIVED);
            intentBroadcast.putExtras(bundle);
            getApplicationContext().sendBroadcast(intentBroadcast);
        }
    }


    private void sendNotification(Intent intent, String title, String littleBody, String bigBody, int icon) {

        //Debemos de generar la notificacion
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        //En la prioridad de activities colocamos la nuestra la primera
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri         = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Aspectos comunes
        notificationBuilder.setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setSmallIcon(icon)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(littleBody)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigBody));

        Notification notification = notificationBuilder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

}
