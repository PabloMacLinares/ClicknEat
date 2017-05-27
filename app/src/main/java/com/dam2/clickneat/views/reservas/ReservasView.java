package com.dam2.clickneat.views.reservas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.recyclerview.adapters.reservas.PublicacionReservasAdapter;
import com.dam2.clickneat.views.BaseActivity;
import com.dam2.clickneat.views.perfilUsuario.PerfilUsuarioView;
import com.dam2.clickneat.views.publicacion.PublicacionView;

import java.util.ArrayList;

/**
 * Created by ferna on 25/05/2017.
 */

public class ReservasView extends BaseActivity implements ReservasContract.View {

    private ReservasContract.Presenter presenter;
    private int idUsuario;
    private boolean canLoad;
    private int positionAdapter;

    //UI Interface
    private RecyclerView rvReservasPublicacion;
    private PublicacionReservasAdapter adapter;
    private ArrayList<Publicacion> publicaciones;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        this.idUsuario          = savedInstanceState != null ? savedInstanceState.getInt(getString(R.string.preferences_id_user))
                                                            : getIntent().getIntExtra(getString(R.string.preferences_id_user), Preferences.DEFAULT_INTEGER);

        this.publicaciones      = savedInstanceState != null ? savedInstanceState.<Publicacion>getParcelableArrayList("publicaciones") : null;

        this.canLoad            = savedInstanceState != null ? savedInstanceState.getBoolean("canLoad") : true;
        this.positionAdapter    = savedInstanceState != null ? savedInstanceState.getInt("positionAdapter") : -1;

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ( this.publicaciones == null ) this.presenter.onLoadPublicacionReservasUsuario(idUsuario);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("publicaciones", publicaciones);
        outState.putInt(getString(R.string.preferences_id_user), idUsuario);
        outState.putBoolean("canLoad", canLoad);
        outState.putInt("positionAdapter", this.positionAdapter);
    }

    @Override
    public void viewReservas(ArrayList<Publicacion> reservas) {

        this.adapter.setReservasPublicaciones(reservas);
    }

    @Override
    public void viewPerfilUsuario(PerfilUsuario perfil) {

        //Intent data
        int typeAction          = PublicacionView.VIEW_ACTION;
        Publicacion publicacion = this.adapter.getPublicacion(positionAdapter);

        Intent i = new Intent(ReservasView.this, PublicacionView.class);
        i.putExtra("typeAction", typeAction);
        i.putExtra("publicacion", publicacion);
        i.putExtra("perfil", perfil);
        i.putExtra(getString(R.string.preferences_id_user), this.idUsuario);
        startActivity(i);

        canLoad = true;
    }


    private void init() {

        this.presenter              = new ReservasPresenter(this);
        this.rvReservasPublicacion  = (RecyclerView) findViewById(R.id.rv_reservas_publicacion);
        this.adapter                = new PublicacionReservasAdapter(this);


        //Agregamos los eventos a los botones
        this.adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( ReservasView.this.canLoad ) {

                    ReservasView.this.positionAdapter = ReservasView.this.rvReservasPublicacion.getChildAdapterPosition(v);

                    ReservasView.this.presenter.onLoadPerfilUsuario(ReservasView.this.adapter.getPublicacion(positionAdapter).getUsuario());
                    ReservasView.this.canLoad = false;
                }
            }
        });

        this.adapter.setClickListenerFab2(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int position            = (Integer)v.getTag();
                Publicacion publicacion = ReservasView.this.adapter.getPublicacion(position);

                Intent intent = new Intent(ReservasView.this, PerfilUsuarioView.class);
                intent.putExtra(getString(R.string.preferences_id_user), publicacion.getUsuario());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
            }
        });


        //Cargamos la informacion

        this.rvReservasPublicacion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if ( publicaciones != null ) this.adapter.setReservasPublicaciones(publicaciones);

        this.rvReservasPublicacion.setAdapter(adapter);
    }

}
