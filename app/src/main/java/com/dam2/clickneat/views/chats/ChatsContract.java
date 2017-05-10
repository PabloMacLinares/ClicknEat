package com.dam2.clickneat.views.chats;

import com.dam2.clickneat.pojos.ConversacionMetadata;

import java.util.ArrayList;

/**
 * Created by Pablo on 25/04/2017.
 */

public interface ChatsContract {

    interface Model {

        void loadChats(int idUsuario);
    }

    interface Presenter {

        void onLoadChats(int idUsuario);
        void loadChats(ArrayList<ConversacionMetadata> conversciones);
        void loadError(String error);
    }

    interface View {

        void viewUpdateConversacion(ConversacionMetadata metadata);
        void viewChats(ArrayList<ConversacionMetadata> conversaciones);
        void viewError(String error);
    }

}
