package com.dam2.clickneat.views.publicacion;

import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.client.handlers.ConversacionHandler;
import com.dam2.clickneat.client.handlers.DomicilioHandler;
import com.dam2.clickneat.client.handlers.PublicacionHandler;
import com.dam2.clickneat.client.handlers.ReservaHandler;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.pojos.Reserva;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAVIER on 18/05/2017.
 */

public class PublicacionModel implements PublicacionContract.Model {

    private PublicacionContract.Presenter presenter;
    private DomicilioHandler domicilioHandler;
    private ReservaHandler reservaHandler;
    private PublicacionHandler publicacionHandler;
    private ConversacionHandler conversacionHandler;


    public PublicacionModel(PublicacionContract.Presenter presenter) {

        this.presenter           = presenter;
        this.domicilioHandler    = new DomicilioHandler(new DataReceiverDomicilio());
        this.reservaHandler      = new ReservaHandler(new DataReceiverReserva());
        this.publicacionHandler  = new PublicacionHandler(new DataReceiverPublicacion());
        this.conversacionHandler = new ConversacionHandler(new DataReceiverConversacion());
    }

    @Override
    public void loadDomiciliosUsuario(int idUsuario) {
        this.domicilioHandler.getDomiciliosByIdPerfilUsuario(idUsuario);
    }

    @Override
    public void loadReservas(int idPublicacion) {
        this.reservaHandler.getReservasPublicacion(idPublicacion);
    }

    @Override
    public void addReserva(Reserva reserva) {
        this.reservaHandler.insertElement(reserva);
    }

    @Override
    public void removeReserva(long idReserva) {
        this.reservaHandler.deleteElement(idReserva);
    }

    @Override
    public void addPublicacion(Publicacion publicacion) {
        this.publicacionHandler.insertElement(publicacion);
    }

    @Override
    public void updatePublicacion(Publicacion publicacion) {
        this.publicacionHandler.updateElement(publicacion);
    }

    @Override
    public void loadIdConversacionFromUsers(int idUsuario1, int idUsuario2) {
        this.conversacionHandler.getConversacionEntreDosUsuarios(idUsuario1, idUsuario2);
    }


    private class DataReceiverDomicilio implements DataReceiver<Domicilio> {

        @Override
        public void onListReceived(List<Domicilio> list) {
            PublicacionModel.this.presenter.onLoadedDomiciliosUsuario(new ArrayList(list));
        }

        @Override
        public void onElementReceived(Domicilio list) {

        }

        @Override
        public void onDataItemInsertedReceived(int id) {

        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {
            PublicacionModel.this.presenter.onErrorLoad(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }
    }

    private class DataReceiverReserva implements DataReceiver<Reserva> {

        @Override
        public void onListReceived(List<Reserva> list) {
            PublicacionModel.this.presenter.onLoadedReservas(new ArrayList(list));
        }

        @Override
        public void onElementReceived(Reserva list) {

        }

        @Override
        public void onDataItemInsertedReceived(int id) {
            PublicacionModel.this.presenter.onAddedReserva(id);
        }

        @Override
        public void onDataNoErrorReceived(String noerror) {
            PublicacionModel.this.presenter.onRemovedReserva();
        }

        @Override
        public void onDataErrorReceived(String error) {
            PublicacionModel.this.presenter.onErrorLoad(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }
    }

    private class DataReceiverPublicacion implements DataReceiver<Publicacion> {

        @Override
        public void onListReceived(List<Publicacion> list) {

        }

        @Override
        public void onElementReceived(Publicacion list) {
            PublicacionModel.this.presenter.onAddedPublicacion(list);
        }

        @Override
        public void onDataItemInsertedReceived(int id) {
            PublicacionModel.this.publicacionHandler.getElement(id);
        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

            PublicacionModel.this.presenter.onNoErrorReceived(noerror);
        }

        @Override
        public void onDataErrorReceived(String error) {
            PublicacionModel.this.presenter.onErrorLoad(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }
    }

    private class DataReceiverConversacion implements DataReceiver<Conversacion> {


        @Override
        public void onListReceived(List<Conversacion> list) {

        }

        @Override
        public void onElementReceived(Conversacion list) {
            PublicacionModel.this.presenter.onLoadedConversacionUsers(list);
        }

        @Override
        public void onDataItemInsertedReceived(int id) {

        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {
            PublicacionModel.this.presenter.onErrorLoad(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }
    }
}
