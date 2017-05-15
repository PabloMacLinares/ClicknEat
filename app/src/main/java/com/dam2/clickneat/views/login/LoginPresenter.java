package com.dam2.clickneat.views.login;

import com.dam2.clickneat.pojos.Usuario;

/**
 * Created by Pablo on 25/04/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginContract.Model model;

    public LoginPresenter(LoginContract.View view){
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public void onLoginUser(Usuario usuario) {
        this.model.loginUser(usuario);
    }

    @Override
    public void onErrorLogin(String error) {

        this.view.viewErrorLogin(error);
    }

    @Override
    public void onLoginSuccess(String token) {

        this.view.viewLoginSuccess(token);
    }
}
