package com.dam2.clickneat.views.perfilUsuario;

import com.dam2.clickneat.pojos.Comentario;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Usuario;

/**
 * Created by JAVIER on 18/05/2017.
 */

public interface PerfilUsuarioContract {

    interface Model {

        void loadPerfilUsuario(int idUsuario);
        void addDomicilioUsuario(Domicilio domicilio);
        void addComentarioUsuario(Comentario comentario);
        void updatePerfilUsuarioElement(PerfilUsuario perfil, String variable);

    }

    interface Presenter {

        void onLoadPerfilUsuario(int idUsuario);
        void onLoadedPerfilUsuario(Usuario usuario);
        void onErrorReceived(String error);

        void onUpdatePerfilUsuarioElement(PerfilUsuario perfil, String variable);
        void onUpdatedPerfilUsuario();

        void onAddDomicilioUsuario(Domicilio domicilio);
        void onLoadDomicilioUsuario(Domicilio domicilio);

        void onAddComentarioUsuario(Comentario comentario);
        void onLoadComentarioUsuario(Comentario comentario);
    }

    interface View {

        void viewPerfilUsuario(Usuario usuario);
        void viewError(String error);
        void viewDomicilio(Domicilio domicilio);
        void viewComentario(Comentario comentario);
        void viewUpdatePerfilUsuario();
    }
}
