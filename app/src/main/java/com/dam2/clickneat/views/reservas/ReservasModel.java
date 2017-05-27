package com.dam2.clickneat.views.reservas;

import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.client.handlers.PerfilUsuarioHandler;
import com.dam2.clickneat.client.handlers.PublicacionHandler;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Publicacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferna on 25/05/2017.
 */

public class ReservasModel implements ReservasContract.Model {

    private ReservasContract.Presenter presenter;
    private PublicacionHandler publicacionHandler;
    private PerfilUsuarioHandler perfilUsuarioHandler;

    public ReservasModel(ReservasContract.Presenter presenter) {

        this.presenter              = presenter;
        this.publicacionHandler     = new PublicacionHandler(new DataReceiverPublicacion());
        this.perfilUsuarioHandler   = new PerfilUsuarioHandler(new DataReceiverPerfilUsuario());

    }
    @Override
    public void loadPublicacionReservasUsuario(int idUsuario) {
        this.publicacionHandler.getPublicacionesReservadasUsuario(idUsuario);
    }

    @Override
    public void loadPerfilUsuario(int idUsuario) {
        this.perfilUsuarioHandler.getPerfilUsuarioByIdUsuario(idUsuario);
    }

    private class DataReceiverPublicacion implements DataReceiver<Publicacion> {

        @Override
        public void onListReceived(List<Publicacion> list) {
            ReservasModel.this.presenter.onLoadedPublicacionReservasUsuario(new ArrayList(list));
        }

        @Override
        public void onElementReceived(Publicacion list) {

        }

        @Override
        public void onDataItemInsertedReceived(int id) {

        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {

        }

        @Override
        public void onLoginReceived(String token) {

        }
    }

    private class DataReceiverPerfilUsuario implements DataReceiver<PerfilUsuario> {

        /*
        *   Debido al modelo de la API utilizado la respuesta generada es un
        *   ArrayList que contiene un elemento, de modo que solo pasaremos
        *   a la vista el primer elemento de dicha lista
        * */
        @Override
        public void onListReceived(List<PerfilUsuario> list) {

            ReservasModel.this.presenter.onLoadedPerfilUsuario(list.get(0));
        }

        @Override
        public void onElementReceived(PerfilUsuario list) {
        }

        @Override
        public void onDataItemInsertedReceived(int id) {

        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {

        }

        @Override
        public void onLoginReceived(String token) {

        }
    }
}
