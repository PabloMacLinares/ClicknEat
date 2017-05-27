package com.dam2.clickneat.views.perfilUsuario;

import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.client.handlers.ComentarioHandler;
import com.dam2.clickneat.client.handlers.DomicilioHandler;
import com.dam2.clickneat.client.handlers.PerfilUsuarioHandler;
import com.dam2.clickneat.client.handlers.UsuarioHandler;
import com.dam2.clickneat.pojos.Comentario;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Usuario;

import java.util.List;

/**
 * Created by JAVIER on 18/05/2017.
 */

public class PerfilUsuarioModel implements PerfilUsuarioContract.Model {

    private PerfilUsuarioContract.Presenter presenter;
    private UsuarioHandler usuarioHandler;
    private DomicilioHandler domicilioHandler;
    private PerfilUsuarioHandler perfilUsuarioHandler;
    private ComentarioHandler comentarioHandler;

    public PerfilUsuarioModel(PerfilUsuarioContract.Presenter presenter ) {

        this.presenter              = presenter;
        this.usuarioHandler         = new UsuarioHandler(new DataUsuarioHandler());
        this.domicilioHandler       = new DomicilioHandler(new DataDomicilioHandler());
        this.perfilUsuarioHandler   = new PerfilUsuarioHandler(new DataPerfilUsuarioHandler());
        this.comentarioHandler      = new ComentarioHandler(new DataComentarioHandler());

    }

    @Override
    public void loadPerfilUsuario(int idUsuario) {
        this.usuarioHandler.getElement(idUsuario);
    }

    @Override
    public void addDomicilioUsuario(Domicilio domicilio) {
        this.domicilioHandler.insertElement(domicilio);
    }

    @Override
    public void addComentarioUsuario(Comentario comentario) {
        this.comentarioHandler.insertElement(comentario);
    }

    @Override
    public void updatePerfilUsuarioElement(PerfilUsuario perfil, String variable) {
        this.perfilUsuarioHandler.updateVariableElement(perfil, variable);
    }

    private class DataUsuarioHandler implements DataReceiver<Usuario> {

        @Override
        public void onListReceived(List<Usuario> list) {

        }

        @Override
        public void onElementReceived(Usuario list) {

            PerfilUsuarioModel.this.presenter.onLoadedPerfilUsuario(list);
        }

        @Override
        public void onDataItemInsertedReceived(int id) {

        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {

            PerfilUsuarioModel.this.presenter.onErrorReceived(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }
    }

    private class DataDomicilioHandler implements DataReceiver<Domicilio> {

        @Override
        public void onListReceived(List<Domicilio> list) {

        }

        @Override
        public void onElementReceived(Domicilio list) {

            PerfilUsuarioModel.this.presenter.onLoadDomicilioUsuario(list);
        }

        @Override
        public void onDataItemInsertedReceived(int id) {

            //Para actualizar la interfaz nos descargamos los datos del nuevo domicilio
            PerfilUsuarioModel.this.domicilioHandler.getElement(id);
        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {

            PerfilUsuarioModel.this.presenter.onErrorReceived(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }
    }

    private class DataPerfilUsuarioHandler implements DataReceiver<PerfilUsuario> {

        @Override
        public void onListReceived(List<PerfilUsuario> list) {

        }

        @Override
        public void onElementReceived(PerfilUsuario list) {

        }

        @Override
        public void onDataItemInsertedReceived(int id) {

        }

        @Override
        public void onDataNoErrorReceived(String noerror) {
            PerfilUsuarioModel.this.presenter.onUpdatedPerfilUsuario();
        }

        @Override
        public void onDataErrorReceived(String error) {

            PerfilUsuarioModel.this.presenter.onErrorReceived(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }
    }

    private class DataComentarioHandler implements DataReceiver<Comentario> {

        @Override
        public void onListReceived(List<Comentario> list) {

        }

        @Override
        public void onElementReceived(Comentario list) {

            PerfilUsuarioModel.this.presenter.onLoadComentarioUsuario(list);
        }

        @Override
        public void onDataItemInsertedReceived(int id) {
            PerfilUsuarioModel.this.comentarioHandler.getElement(id);
        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {

            PerfilUsuarioModel.this.presenter.onErrorReceived(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }
    }
}
