package com.dam2.clickneat.pojos;

import java.util.Date;

/**
 * Created by ferna on 27/04/2017.
 */

public class Mensaje {

    private int id;
    private int usuarioEnvia;
    private int conversacion;
    private String textoMensaje;
    private Date fechaEnvio;

    public Mensaje() {

        this.id             = 0;
        this.usuarioEnvia   = 0;
        this.conversacion   = 0;
        this.textoMensaje   = "";
        this.fechaEnvio     = new Date(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioEnvia() {
        return usuarioEnvia;
    }

    public void setUsuarioEnvia(int usuarioEnvia) {
        this.usuarioEnvia = usuarioEnvia;
    }

    public int getConversacion() {
        return conversacion;
    }

    public void setConversacion(int conversacion) {
        this.conversacion = conversacion;
    }

    public String getTextoMensaje() {
        return textoMensaje;
    }

    public void setTextoMensaje(String textoMensaje) {
        this.textoMensaje = textoMensaje;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}
