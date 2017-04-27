package com.dam2.clickneat.pojos;

/**
 * Created by ferna on 27/04/2017.
 */

public class Reserva {

    private int id;
    private int publicacion;
    private int usuario;

    public Reserva() {

        this.id             = 0;
        this.publicacion    = 0;
        this.usuario        = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(int publicacion) {
        this.publicacion = publicacion;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
}
