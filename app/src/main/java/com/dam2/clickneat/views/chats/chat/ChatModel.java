package com.dam2.clickneat.views.chats.chat;

/**
 * Created by Pablo on 25/04/2017.
 */

public class ChatModel implements ChatContract.Model {

    private ChatContract.Presenter presenter;

    public ChatModel(ChatContract.Presenter presenter){
        this.presenter = presenter;
    }
}
