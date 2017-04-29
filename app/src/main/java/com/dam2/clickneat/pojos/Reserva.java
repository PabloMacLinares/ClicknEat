package com.dam2.clickneat.pojos;

import android.os.Parcel;

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

    protected Reserva(Parcel in) {
        super(in);

        this.id             = in.readInt();
        this.publicacion    = in.readInt();
        this.usuario        = in.readInt();
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

    public static final Creator<Reserva> CREATOR = new Creator<Reserva>() {
        @Override
        public Reserva createFromParcel(Parcel in) {
            return new Reserva(in);
        }

        @Override
        public Reserva[] newArray(int size) {
            return new Reserva[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeInt(this.publicacion);
        dest.writeInt(this.usuario);

    }
}
