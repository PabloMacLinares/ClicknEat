package com.dam2.clickneat.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by ferna on 27/04/2017.
 */

public class Reserva extends BaseClass {

    @Expose
    private int id;
    @Expose
    private int publicacion;
    @Expose
    private int usuario;

    public Reserva() {

        super( Reserva.class, new TypeToken<ArrayList<Reserva>>(){});

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
