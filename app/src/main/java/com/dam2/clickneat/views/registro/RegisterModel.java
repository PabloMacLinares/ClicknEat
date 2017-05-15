package com.dam2.clickneat.views.registro;

import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.client.handlers.UsuarioHandler;
import com.dam2.clickneat.pojos.Usuario;

import java.util.List;

/**
 * Created by Pablo on 25/04/2017.
 */

public class RegisterModel implements RegisterContract.Model {

    private RegisterContract.Presenter presenter;
    private UsuarioHandler usuarioHandler;


    public RegisterModel(RegisterContract.Presenter presenter){

        this.presenter      = presenter;
        this.usuarioHandler = new UsuarioHandler(new DataReceiver<Usuario>() {
            @Override
            public void onListReceived(List<Usuario> list) {

            }

            @Override
            public void onElementReceived(Usuario list) {

            }

            @Override
            public void onDataItemInsertedReceived(int id) {

            }

            @Override
            public void onDataNoErrorReceived(String noerror) {
                RegisterModel.this.presenter.onRegisterSuccess(noerror);
            }

            @Override
            public void onDataErrorReceived(String error) {

                RegisterModel.this.presenter.onErrorRegisterUser(error);
            }

            @Override
            public void onLoginReceived(String token) {

            }
        });
    }

    @Override
    public void registerUser(Usuario usuario) {

        usuarioHandler.insertElement(usuario);
    }
}
