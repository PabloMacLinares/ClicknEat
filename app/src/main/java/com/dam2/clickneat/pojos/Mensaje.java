package com.dam2.clickneat.pojos;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ferna on 27/04/2017.
 */

public class Mensaje extends BaseClass {

    @Expose
    private int id;
    @Expose
    private int usuarioEnvia;
    @Expose
    private int conversacion;
    @Expose
    private String textoMensaje;
    @Expose
    private Date fechaEnvio;

    public Mensaje() {

        super( Mensaje.class, new TypeToken<ArrayList<Mensaje>>(){});
        this.id             = 0;
        this.usuarioEnvia   = 0;
        this.conversacion   = 0;
        this.textoMensaje   = "";
        this.fechaEnvio     = new Date(System.currentTimeMillis());
    }

    protected Mensaje(Parcel in) {
        super(in);

        this.id             = in.readInt();
        this.usuarioEnvia   = in.readInt();
        this.conversacion   = in.readInt();
        this.textoMensaje   = in.readString();
        this.fechaEnvio     = (Date) in.readSerializable();
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



    public static final Creator<Mensaje> CREATOR = new Creator<Mensaje>() {
        @Override
        public Mensaje createFromParcel(Parcel in) {
            return new Mensaje(in);
        }

        @Override
        public Mensaje[] newArray(int size) {
            return new Mensaje[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeInt(this.usuarioEnvia);
        dest.writeInt(this.conversacion);
        dest.writeString(this.textoMensaje);
        dest.writeSerializable(this.fechaEnvio);

    }
}
