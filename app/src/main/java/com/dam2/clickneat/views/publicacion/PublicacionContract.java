package com.dam2.clickneat.views.publicacion;

import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.pojos.Reserva;

import java.util.ArrayList;

/**
 * Created by JAVIER on 18/05/2017.
 */

public interface PublicacionContract {

    interface Model{

        void loadDomiciliosUsuario(int idUsuario);
        void loadReservas(int idPublicacion);

        void addReserva(Reserva reserva);
        void removeReserva(long idReserva);

        void loadPublicacion(int idPublicacion);
        void addPublicacion(Publicacion publicacion);
        void updatePublicacion(Publicacion publicacion);
        void removePublicacion(int idPublicacion);

        void loadIdConversacionFromUsers(int idUsuario1, int idUsuario2);
        void loadPerfilUsuarioPublicacion(int id);
    }

    interface Presenter{

        //Domicilios
        void onLoadDomiciliosUsuario(int idUsuario);
        void onLoadedDomiciliosUsuario(ArrayList<Domicilio> domicilios);
        void onErrorLoad(String error);

        //Reservas
        void onLoadReservas(int idPublicacion);
        void onLoadedReservas(ArrayList<Reserva> reservas);

        void onAddReserva(Reserva reserva);
        void onRemoveReserva(long idReserva);
        void onRemovedReserva();
        void onAddedReserva(long idReserva);

        //Publicacion
        void onLoadPublicacion(int idPublicacion);
        void onAddPublicacion(Publicacion publicacion);
        void onAddedPublicacion(Publicacion publicacion);
        void onUpdatePublicacion(Publicacion publicacion);
        void onRemovePublicacion(int idPublicacion);

        void onNoErrorReceived(String noerror);
        void onLoadConversacionUsers(int idUsuario1, int idUsuario2);
        void onLoadedConversacionUsers(Conversacion conversacion);

        //From Mapa
        void onLoadPerfilUsuarioPublicacion(int id);
        void onLoadedPerfilUsuarioPublicacion(PerfilUsuario perfilUsuario);

    }

    interface View{

        void viewDomiciliosUsuario(ArrayList<Domicilio> domicilios);
        void viewLoadedReservas(ArrayList<Reserva> reservas);
        void viewError(String error);
        void viewNoError(String noerror);
        void viewRemovedReserva();
        void viewAddReserva(long idReserva);
        void viewAddPublicacion(Publicacion publicacion);
        void viewConversacion(Conversacion conversacion);
        void viewPerfilUsuarioPublicacion(PerfilUsuario perfilUsuario);
    }
}
