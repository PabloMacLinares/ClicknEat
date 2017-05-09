package com.dam2.clickneat.views.chats.chat;

import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.pojos.Mensaje;

/**
 * Created by Pablo on 25/04/2017.
 */

public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View view;
    private ChatContract.Model model;

    public ChatPresenter(ChatContract.View view){
        this.view = view;
        model = new ChatModel(this);
    }

    @Override
    public void onLoadMensajes(int idConversacion) {
        this.model.loadConversacion(idConversacion);
    }

    @Override
    public void onReadMensajes(ConversacionMetadata metadata) {
        this.model.readMensajes(metadata);
    }

    @Override
    public void onSendMensaje(Mensaje mensaje) {
        this.model.sendMensaje(mensaje);
    }

    @Override
    public void onCreateConversacion(Conversacion conversacion) {
        this.model.createConversacion(conversacion);
    }

    @Override
    public void loadConversacion(Conversacion conversacion) {
        this.view.viewConversacion(conversacion);
    }

    @Override
    public void loadError(String error) {

        this.view.viewError(error);
    }
}
