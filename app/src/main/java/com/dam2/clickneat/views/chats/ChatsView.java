package com.dam2.clickneat.views.chats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dam2.clickneat.R;

public class ChatsView extends AppCompatActivity implements ChatsContract.View {

    private ChatsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        presenter = new ChatsPresenter(this);
    }
}
