package com.dam2.clickneat.views.chats;

import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.client.handlers.ConversacionHandler;
import com.dam2.clickneat.client.handlers.ConversacionMetadataHandler;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 25/04/2017.
 */

public class ChatsModel implements ChatsContract.Model, DataReceiver<ConversacionMetadata> {

    private ChatsContract.Presenter presenter;
    private ConversacionMetadataHandler conversacionMetadataHandler;

    public ChatsModel(ChatsContract.Presenter presenter){

        this.presenter                      = presenter;
        this.conversacionMetadataHandler    = new ConversacionMetadataHandler(this);
    }

    @Override
    public void loadChats(int idUsuario) {

        this.conversacionMetadataHandler.getAllElementsByUserId(idUsuario);
    }

    @Override
    public void onListReceived(List<ConversacionMetadata> list) {
        this.presenter.loadChats(new ArrayList(list));
    }

    @Override
    public void onElementReceived(ConversacionMetadata list) {

    }

    @Override
    public void onDataItemInsertedReceived(int id) {

    }

    @Override
    public void onDataNoErrorReceived(String noerror) {

    }

    @Override
    public void onDataErrorReceived(String error) {

        this.presenter.loadError(error);
    }

    @Override
    public void onLoginReceived(String token) {

    }

}
