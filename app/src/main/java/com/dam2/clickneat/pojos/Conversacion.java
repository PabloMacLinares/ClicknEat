package com.dam2.clickneat.pojos;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by ferna on 27/04/2017.
 */

public class Conversacion extends BaseClass {

    @Expose
    private int id;
    @Expose
    private ArrayList<Mensaje> mensajes;
    @Expose
    private int usuarioInicia;
    @Expose
    private int usuarioRecibe;

    public Conversacion() {

        super( Conversacion.class, new TypeToken<ArrayList<Conversacion>>(){});

        this.id             = 0;
        this.mensajes       = new ArrayList();
        this.usuarioInicia  = 0;
        this.usuarioRecibe  = 0;
    }

    protected Conversacion(Parcel in) {
        super(in);

        this.id             = in.readInt();
        in.readTypedList( mensajes, Mensaje.CREATOR);
        this.usuarioInicia  = in.readInt();
        this.usuarioRecibe  = in.readInt();
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

    public static final Creator<Conversacion> CREATOR = new Creator<Conversacion>() {
        @Override
        public Conversacion createFromParcel(Parcel in) {
            return new Conversacion(in);
        }

        @Override
        public Conversacion[] newArray(int size) {
            return new Conversacion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeTypedList(mensajes);
        dest.writeInt(this.usuarioInicia);
        dest.writeInt(this.usuarioRecibe);

    }
}
