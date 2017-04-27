package com.dam2.clickneat.pojos;

import java.util.ArrayList;

/**
 * Created by ferna on 27/04/2017.
 */

public class Conversacion {

    private int id;
    private ArrayList<Mensaje> mensajes;
    private int usuarioInicia;
    private int usuarioRecibe;

    public Conversacion() {

        this.id             = 0;
        this.mensajes       = new ArrayList();
        this.usuarioInicia  = 0;
        this.usuarioRecibe  = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public void addMensaje(Mensaje mensaje){
        this.mensajes.add(mensaje);
    }

    public int getUsuarioInicia() {
        return usuarioInicia;
    }

    public void setUsuarioInicia(int usuarioInicia) {
        this.usuarioInicia = usuarioInicia;
    }

    public int getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(int usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }
}
