package com.dam2.clickneat.views.registro;

import com.dam2.clickneat.pojos.Usuario;

/**
 * Created by Pablo on 25/04/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private RegisterContract.Model model;

    public RegisterPresenter(RegisterContract.View view){
        this.view = view;
        model = new RegisterModel(this);
    }

    @Override
    public void onRegisterUser(Usuario usuario) {
        this.model.registerUser(usuario);
    }

    @Override
    public void onErrorRegisterUser(String error) {

        this.view.registerError(error);
    }

    @Override
    public void onRegisterSuccess(String token) {

        this.view.registerSuccess(token);
    }

}
