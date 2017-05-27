package com.dam2.clickneat.pojos;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by ferna on 20/05/2017.
 */

public class ComentarioMetadata extends BaseClass {

    @Expose
    private int id;
    @Expose
    private int comentario;
    @Expose
    private String titulo;
    @Expose
    private String imagen;

    public ComentarioMetadata() {

        super( ComentarioMetadata.class, new TypeToken<ArrayList<ComentarioMetadata>>(){});
        this.id         = 0;
        this.comentario = 0;
        this.titulo     = "";
        this.imagen     = "";
    }

    protected ComentarioMetadata(Parcel in) {

        super(in);

        this.id         = in.readInt();
        this.comentario = in.readInt();
        this.titulo     = in.readString();
        this.imagen     = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setComentario(int comentario) {
        this.comentario = comentario;
    }

    public int getComentario() {
        return this.comentario;
    }

    public static final Creator<ComentarioMetadata> CREATOR = new Creator<ComentarioMetadata>() {
        @Override
        public ComentarioMetadata createFromParcel(Parcel in) {
            return new ComentarioMetadata(in);
        }

        @Override
        public ComentarioMetadata[] newArray(int size) {
            return new ComentarioMetadata[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeInt(this.comentario);
        dest.writeString(this.titulo);
        dest.writeString(this.imagen);

    }
}
