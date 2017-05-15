package com.dam2.clickneat.views.main;

import com.dam2.clickneat.pojos.Usuario;

/**
 * Created by Pablo on 25/04/2017.
 */

public interface MainContract {

    interface Model{

        void loginUsuario(Usuario usuario);
        void updateUserElement(Usuario usuario, String atribute);
    }

    interface Presenter{

        void onLoginUsuario(Usuario usuario);
        void onErrorLogin(String error);
        void onLoginSuccess(String token);
        void onUpdateUserElement(Usuario usuario, String atribute);

    }

    interface View{

        void errorLogin(String error);
        void loginSuccess(String token);
    }

}
