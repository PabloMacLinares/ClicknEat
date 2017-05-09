package com.dam2.clickneat.views.chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.recyclerview.adapters.conversacion.ConversacionAdapter;
import com.dam2.clickneat.views.BaseActivity;
import com.dam2.clickneat.views.chats.chat.ChatView;

import java.util.ArrayList;

public class ChatsView extends BaseActivity implements ChatsContract.View {

    private ChatsContract.Presenter presenter;
    private RecyclerView rvConversaciones;
    private ConversacionAdapter adapterConversaciones;

    //Para ahorrar peticiones almacenamos cuando nos logueamos el identificador del usuario en las SP
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        presenter = new ChatsPresenter(this);
        this.init();
    }

    @Override
    protected void onResume() {

        super.onResume();
        this.presenter.onLoadChats(this.idUsuario);
    }

    private void init() {

        //Obtenemos el identificador del Usuario
        Preferences preferences = new Preferences(this);

        idUsuario               = preferences.getInteger(getString(R.string.preferences_id_user));
        rvConversaciones        = (RecyclerView) findViewById(R.id.activity_chats_rv);
        adapterConversaciones   = new ConversacionAdapter(this);

        adapterConversaciones.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int position                   = rvConversaciones.getChildAdapterPosition(v);
                ConversacionMetadata metadata  = adapterConversaciones.getConversacionMetadata(position);

                Intent intent       = new Intent(ChatsView.this, ChatView.class);
                intent.putExtra("metadata", metadata);
                intent.putExtra(getString(R.string.preferences_id_user), idUsuario);

                startActivity(intent);

            }
        });

        rvConversaciones.setAdapter(adapterConversaciones);
        rvConversaciones.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void viewUpdateConversacion(ConversacionMetadata metadata) {

        this.adapterConversaciones.updateConversacionMetadata(metadata);
    }

    @Override
    public void viewChats(ArrayList<ConversacionMetadata> conversaciones) {

        this.adapterConversaciones.setConversaciones(conversaciones);
    }

    @Override
    public void viewError(String error) {

        //Visualizamos el error por parte del servidor
        System.err.println(error);
    }
}
