package com.dam2.clickneat.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ferna on 27/04/2017.
 */

public class Comentario extends BaseClass {

    @Expose
    private int id;
    @Expose
    private int usuarioDa;
    @Expose
    private int usuarioRecibe;
    @Expose
    private String valoracion;
    @Expose
    private int puntuacion;
    @Expose
    private Date fechaComentario;

    public Comentario() {

        super( Comentario.class, new TypeToken<ArrayList<Comentario>>(){});
        this.id                 = 0;
        this.usuarioDa          = 0;
        this.usuarioRecibe      = 0;
        this.valoracion         = "";
        this.puntuacion         = 0;
        this.fechaComentario    = new Date(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioDa() {
        return usuarioDa;
    }

    public void setUsuarioDa(int usuarioDa) {
        this.usuarioDa = usuarioDa;
    }

    public int getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(int usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }
}
