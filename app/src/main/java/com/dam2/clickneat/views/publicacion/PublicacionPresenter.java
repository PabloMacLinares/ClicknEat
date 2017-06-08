package com.dam2.clickneat.views.publicacion;

import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.pojos.Reserva;

import java.util.ArrayList;

/**
 * Created by JAVIER on 18/05/2017.
 */

public class PublicacionPresenter implements PublicacionContract.Presenter {

    private PublicacionContract.Model model;
    private PublicacionContract.View view;

    public PublicacionPresenter(PublicacionContract.View view) {

        this.view   = view;
        this.model  = new PublicacionModel(this);
    }

    @Override
    public void onLoadDomiciliosUsuario(int idUsuario) {

        this.model.loadDomiciliosUsuario(idUsuario);
    }

    @Override
    public void onLoadedDomiciliosUsuario(ArrayList<Domicilio> domicilios) {

        this.view.viewDomiciliosUsuario(domicilios);
    }

    @Override
    public void onErrorLoad(String error) {

        this.view.viewError(error);
    }

    @Override
    public void onLoadReservas(int idPublicacion) {
        this.model.loadReservas(idPublicacion);
    }

    @Override
    public void onLoadedReservas(ArrayList<Reserva> reservas) {

        this.view.viewLoadedReservas(reservas);
    }

    @Override
    public void onAddReserva(Reserva reserva) {
        this.model.addReserva(reserva);
    }

    @Override
    public void onRemoveReserva(long idReserva) {
        this.model.removeReserva(idReserva);
    }

    @Override
    public void onRemovedReserva() {
        this.view.viewRemovedReserva();
    }

    @Override
    public void onAddedReserva(long idReserva) {
        this.view.viewAddReserva(idReserva);
    }

    @Override
    public void onLoadPublicacion(int idPublicacion) {
        this.model.loadPublicacion(idPublicacion);
    }

    @Override
    public void onAddPublicacion(Publicacion publicacion) {

        this.model.addPublicacion(publicacion);
    }

    @Override
    public void onAddedPublicacion(Publicacion publicacion) {

        this.view.viewAddPublicacion(publicacion);
    }

    @Override
    public void onUpdatePublicacion(Publicacion publicacion) {

        this.model.updatePublicacion(publicacion);
    }

    @Override
    public void onRemovePublicacion(int idPublicacion) {
        this.model.removePublicacion(idPublicacion);
    }

    @Override
    public void onNoErrorReceived(String noerror) {
        this.view.viewNoError(noerror);
    }

    @Override
    public void onLoadConversacionUsers(int idUsuario1, int idUsuario2) {
        this.model.loadIdConversacionFromUsers(idUsuario1, idUsuario2);
    }

    @Override
    public void onLoadedConversacionUsers(Conversacion conversacion) {
        this.view.viewConversacion(conversacion);
    }

    @Override
    public void onLoadPerfilUsuarioPublicacion(int id) {
        this.model.loadPerfilUsuarioPublicacion(id);
    }

    @Override
    public void onLoadedPerfilUsuarioPublicacion(PerfilUsuario perfilUsuario) {
        this.view.viewPerfilUsuarioPublicacion(perfilUsuario);
    }
}
