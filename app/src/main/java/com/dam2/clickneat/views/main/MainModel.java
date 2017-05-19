package com.dam2.clickneat.views.main;

import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.client.handlers.UsuarioHandler;
import com.dam2.clickneat.pojos.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Pablo on 25/04/2017.
 */

public class MainModel implements MainContract.Model {

    private MainContract.Presenter presenter;
    private UsuarioHandler usuarioHandler;


    public MainModel(final MainContract.Presenter presenter){
        this.presenter          = presenter;
        this.usuarioHandler     = new UsuarioHandler(new DataReceiver<Usuario>() {
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

            }

            @Override
            public void onDataErrorReceived(String error) {

                //Error en la comprobacion
                MainModel.this.presenter.onErrorLogin(error);
            }

            @Override
            public void onLoginReceived(String token) {

                //Token del usuario para consultar a la API
                MainModel.this.presenter.onLoginSuccess(token);
            }
        });
    }

    @Override
    public void loginUsuario(Usuario usuario) {

        usuarioHandler.loginUser(usuario);
    }

    @Override
    public void updateUserElement(Usuario usuario, String attribute) {
        usuarioHandler.updateVariableElement(usuario, attribute);
    }


}
