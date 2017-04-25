package com.dam2.clickneat.views.chats.chat;

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
}
