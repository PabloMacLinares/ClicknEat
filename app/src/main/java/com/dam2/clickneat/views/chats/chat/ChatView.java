package com.dam2.clickneat.views.chats.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dam2.clickneat.R;

public class ChatView extends AppCompatActivity implements ChatContract.View{

    private ChatContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        presenter = new ChatPresenter(this);
    }
}
