package com.dam2.clickneat.views.chats;

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
}
