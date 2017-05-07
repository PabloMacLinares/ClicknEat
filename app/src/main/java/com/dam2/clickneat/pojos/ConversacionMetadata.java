package com.dam2.clickneat.pojos;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ferna on 03/05/2017.
 */

public class ConversacionMetadata extends BaseClass {

    @Expose
    private int id;
    @Expose
    private String titulo;
    @Expose
    private String textoUltimoMensaje;
    @Expose
    private int mensajesNoLeidos;
    @Expose
    private String foto;
    @Expose
    private Date fechaEnvio;
    @Expose
    private int conversacion;
    @Expose
    private int usuario;

    public ConversacionMetadata() {

        super( ConversacionMetadata.class, new TypeToken<ArrayList<ConversacionMetadata>>(){});

        this.id                     = 0;
        this.titulo                 = "";
        this.textoUltimoMensaje     = "";
        this.mensajesNoLeidos       = 0;
        this.foto                   = "";
        this.fechaEnvio             = new Date(System.currentTimeMillis());
        this.conversacion           = 0;
        this.usuario                = 0;
    }

    protected ConversacionMetadata(Parcel in) {
        super(in);

        this.id                     = in.readInt();
        this.titulo                 = in.readString();
        this.textoUltimoMensaje     = in.readString();
        this.mensajesNoLeidos       = in.readInt();
        this.foto                   = in.readString();
        this.fechaEnvio             = (Date) in.readSerializable();
        this.conversacion           = in.readInt();
        this.usuario                = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getTextoUltimoMensaje() {
        return textoUltimoMensaje;
    }

    public void setTextoUltimoMensaje(String textoUltimoMensaje) {
        this.textoUltimoMensaje = textoUltimoMensaje;
    }

    public int getMensajesNoLeidos() {
        return mensajesNoLeidos;
    }

    public void setMensajesNoLeidos(int mensajesNoLeidos) {
        this.mensajesNoLeidos = mensajesNoLeidos;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public int getConversacion() {
        return conversacion;
    }

    public void setConversacion(int conversacion) {
        this.conversacion = conversacion;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public static final Creator<ConversacionMetadata> CREATOR = new Creator<ConversacionMetadata>() {
        @Override
        public ConversacionMetadata createFromParcel(Parcel in) {
            return new ConversacionMetadata(in);
        }

        @Override
        public ConversacionMetadata[] newArray(int size) {
            return new ConversacionMetadata[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeString(this.titulo);
        dest.writeString(this.textoUltimoMensaje);
        dest.writeInt(this.mensajesNoLeidos);
        dest.writeString(this.foto);
        dest.writeSerializable(this.fechaEnvio);
        dest.writeInt(this.conversacion);
        dest.writeInt(this.usuario);
    }
}
