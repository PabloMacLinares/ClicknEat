package com.dam2.clickneat.views.login;

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
}
