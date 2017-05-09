package com.dam2.clickneat.views.chats;

import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;

import java.util.ArrayList;

/**
 * Created by Pablo on 25/04/2017.
 */

public class ChatsPresenter implements ChatsContract.Presenter {

    private ChatsContract.View view;
    private ChatsContract.Model model;

    public ChatsPresenter(ChatsContract.View view){
        this.view = view;
        model = new ChatsModel(this);
    }

    @Override
    public void onLoadChats(int idUsuario) {

        this.model.loadChats(idUsuario);
    }

    @Override
    public void loadChats(ArrayList<ConversacionMetadata> conversciones) {
        this.view.viewChats(conversciones);
    }

    @Override
    public void loadError(String error) {
        this.view.viewError(error);
    }
}
