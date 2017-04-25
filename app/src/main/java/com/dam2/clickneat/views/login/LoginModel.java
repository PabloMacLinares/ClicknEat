package com.dam2.clickneat.views.login;

/**
 * Created by Pablo on 25/04/2017.
 */

public class LoginModel implements LoginContract.Model {

    private LoginContract.Presenter presenter;

    public LoginModel(LoginContract.Presenter presenter){
        this.presenter = presenter;
    }
}
