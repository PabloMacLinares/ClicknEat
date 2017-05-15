package com.dam2.clickneat.views.login;

import com.dam2.clickneat.pojos.Usuario;

/**
 * Created by Pablo on 25/04/2017.
 */

public interface LoginContract {

    interface Model{

        void loginUser(Usuario usuario);

    }

    interface Presenter{

        void onLoginUser(Usuario usuario);
        void onErrorLogin(String error);
        void onLoginSuccess(String token);

    }

    interface View{

        void viewErrorLogin(String error);
        void viewLoginSuccess(String token);

    }

}
