package com.dam2.clickneat.views.reservas;

import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.pojos.Reserva;

import java.util.ArrayList;

/**
 * Created by ferna on 25/05/2017.
 */

public class ReservasContract {

    interface Model {

        void loadPublicacionReservasUsuario(int idUsuario);
        void loadPerfilUsuario(int idUsuario);
    }

    interface Presenter {

        void onLoadPublicacionReservasUsuario(int idUsuario);
        void onLoadedPublicacionReservasUsuario(ArrayList<Publicacion> reservas);
        void onLoadPerfilUsuario(int idUsuario);
        void onLoadedPerfilUsuario(PerfilUsuario perfil);
    }

    interface View {

        void viewReservas(ArrayList<Publicacion> reservas);
        void viewPerfilUsuario(PerfilUsuario perfil);

    }
}
