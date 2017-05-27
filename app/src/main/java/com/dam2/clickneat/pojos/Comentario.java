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
    private float puntuacion;
    @Expose
    private Date fechaComentario;
    @Expose
    private ComentarioMetadata metadata;

    public Comentario() {

        super( Comentario.class, new TypeToken<ArrayList<Comentario>>(){});
        this.id                 = 0;
        this.usuarioDa          = 0;
        this.usuarioRecibe      = 0;
        this.valoracion         = "";
        this.puntuacion         = 0;
        this.fechaComentario    = new Date(System.currentTimeMillis());
        this.metadata           = new ComentarioMetadata();
    }

    protected Comentario(Parcel in) {
        super(in);

        this.id                 = in.readInt();
        this.usuarioDa          = in.readInt();
        this.usuarioRecibe      = in.readInt();
        this.valoracion         = in.readString();
        this.puntuacion         = in.readFloat();
        this.fechaComentario    = (Date) in.readSerializable();
        this.metadata           = in.readParcelable(ComentarioMetadata.class.getClassLoader());
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

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public ComentarioMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ComentarioMetadata comentarioMetadata) {
        this.metadata = comentarioMetadata;
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
        dest.writeFloat(this.puntuacion);
        dest.writeSerializable(this.fechaComentario);
        dest.writeParcelable(this.metadata, flags);
    }
}
