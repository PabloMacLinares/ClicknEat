package com.dam2.clickneat.views.chats.chat;

import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.pojos.Mensaje;
import java.util.ArrayList;

/**
 * Created by Pablo on 25/04/2017.
 */

public interface ChatContract {

    interface Model{

        void loadConversacion(int idConversacion);
        void readMensajes(ConversacionMetadata metadata);
        void sendMensaje(Mensaje mensaje);
        void createConversacion(Conversacion conversacion);
    }

    interface Presenter{

        void onLoadMensajes(int idConversacion);
        void onReadMensajes(ConversacionMetadata metadata);
        void onSendMensaje(Mensaje mensaje);
        void onCreateConversacion(Conversacion conversacion);
        void loadConversacion(Conversacion conversacion);
        void loadError(String error);

    }

    interface View{

        void viewConversacion(Conversacion conversacion);
        void viewMensaje(Mensaje mensaje);
        void viewConversacionMetadata(ConversacionMetadata conversacionMetadata);
        void viewError(String error);
    }

}
