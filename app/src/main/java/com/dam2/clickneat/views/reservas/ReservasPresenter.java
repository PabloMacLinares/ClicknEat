package com.dam2.clickneat.views.reservas;

import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Publicacion;

import java.util.ArrayList;

/**
 * Created by ferna on 25/05/2017.
 */

public class ReservasPresenter implements ReservasContract.Presenter {

    private ReservasContract.View view;
    private ReservasContract.Model model;

    public ReservasPresenter(ReservasContract.View view) {

        this.view   = view;
        this.model  = new ReservasModel(this);

    }

    @Override
    public void onLoadPublicacionReservasUsuario(int idUsuario) {
        this.model.loadPublicacionReservasUsuario(idUsuario);
    }

    @Override
    public void onLoadedPublicacionReservasUsuario(ArrayList<Publicacion> reservas) {
        this.view.viewReservas(reservas);
    }

    @Override
    public void onLoadPerfilUsuario(int idUsuario) {
        this.model.loadPerfilUsuario(idUsuario);
    }

    @Override
    public void onLoadedPerfilUsuario(PerfilUsuario perfil) {
        this.view.viewPerfilUsuario(perfil);
    }
}
