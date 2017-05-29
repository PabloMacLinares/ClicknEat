package com.dam2.clickneat.views.perfilUsuario;

import com.dam2.clickneat.pojos.Comentario;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Usuario;

/**
 * Created by JAVIER on 18/05/2017.
 */

public class PerfilUsuarioPresenter implements PerfilUsuarioContract.Presenter {

    private PerfilUsuarioContract.View view;
    private PerfilUsuarioContract.Model model;

    public PerfilUsuarioPresenter(PerfilUsuarioContract.View view) {

        this.view   = view;
        this.model  = new PerfilUsuarioModel(this);
    }

    @Override
    public void onLoadPerfilUsuario(int idUsuario) {
        this.model.loadPerfilUsuario(idUsuario);
    }

    @Override
    public void onLoadedPerfilUsuario(Usuario usuario) {
        this.view.viewPerfilUsuario(usuario);
    }

    @Override
    public void onUpdatePerfilUsuarioElement(PerfilUsuario perfil, String variable) {
        this.model.updatePerfilUsuarioElement(perfil, variable);
    }

    @Override
    public void onUpdatedPerfilUsuario(String noerror) {
        this.view.viewUpdatePerfilUsuario(noerror);
    }

    @Override
    public void onErrorReceived(String error) {
        this.view.viewError(error);
    }

    @Override
    public void onAddDomicilioUsuario(Domicilio domicilio) {
        this.model.addDomicilioUsuario(domicilio);
    }

    @Override
    public void onLoadDomicilioUsuario(Domicilio domicilio) {
        this.view.viewDomicilio(domicilio);
    }

    @Override
    public void onAddComentarioUsuario(Comentario comentario) {
        this.model.addComentarioUsuario(comentario);
    }

    @Override
    public void onLoadComentarioUsuario(Comentario comentario) {
        this.view.viewComentario(comentario);
    }
}
