package com.dam2.clickneat.views.registro;

import com.dam2.clickneat.pojos.Usuario;

/**
 * Created by Pablo on 25/04/2017.
 */

public interface RegisterContract {

    interface Model{

        void registerUser(Usuario usuario);
    }

    interface Presenter{

        void onRegisterUser(Usuario usuario);
        void onErrorRegisterUser(String error);
        void onRegisterSuccess(String token);

    }

    interface View{

        void registerError(String error);
        void registerSuccess(String token);
    }

}
