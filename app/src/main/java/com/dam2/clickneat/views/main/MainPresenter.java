package com.dam2.clickneat.views.main;

import com.dam2.clickneat.pojos.Usuario;

/**
 * Created by Pablo on 25/04/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainContract.Model model;

    public MainPresenter(MainContract.View view){
        this.view = view;
        model = new MainModel(this);
    }

    @Override
    public void onLoginUsuario(Usuario usuario) {

        this.model.loginUsuario(usuario);
    }

    @Override
    public void onErrorLogin(String error) {
        this.view.errorLogin(error);
    }

    @Override
    public void onLoginSuccess(String token) {
        this.view.loginSuccess(token);
    }

    @Override
    public void onUpdateUserElement(Usuario usuario, String atribute) {
        this.model.updateUserElement(usuario, atribute);
    }

}
