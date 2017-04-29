package com.dam2.clickneat.pojos;

import android.os.Parcel;

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

    protected Comentario(Parcel in) {
        super(in);

        this.id                 = in.readInt();
        this.usuarioDa          = in.readInt();
        this.usuarioRecibe      = in.readInt();
        this.valoracion         = in.readString();
        this.puntuacion         = in.readInt();
        this.fechaComentario    = (Date) in.readSerializable();
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


    public static final Creator<Comentario> CREATOR = new Creator<Comentario>() {
        @Override
        public Comentario createFromParcel(Parcel in) {
            return new Comentario(in);
        }

        @Override
        public Comentario[] newArray(int size) {
            return new Comentario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeInt(this.usuarioDa);
        dest.writeInt(this.usuarioRecibe);
        dest.writeString(this.valoracion);
        dest.writeInt(this.puntuacion);
        dest.writeSerializable(this.fechaComentario);

    }
}
