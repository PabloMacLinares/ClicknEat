package com.dam2.clickneat.views.chats;

/**
 * Created by Pablo on 25/04/2017.
 */

public class ChatsModel implements ChatsContract.Model {

    private ChatsContract.Presenter presenter;

    public ChatsModel(ChatsContract.Presenter presenter){
        this.presenter = presenter;
    }
}
