package com.dam2.clickneat.pojos;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by ferna on 27/04/2017.
 */

public class Domicilio extends BaseClass {

    @Expose
    private int id;
    @Expose
    private String direccion;
    @Expose
    private Ubicacion ubicacion;
    @Expose
    private int cp;
    @Expose
    private String pais;
    @Expose
    private String localidad;
    @Expose
    private String barrio;
    @Expose
    private ArrayList<String> imagenes;
    @Expose
    private int perfil;
    @Expose
    private ArrayList<Integer> publicaciones;


    public Domicilio() {


        super( Domicilio.class, new TypeToken<ArrayList<Domicilio>>(){});
        this.id             = 0;
        this.direccion      = "";
        this.ubicacion      = new Ubicacion();
        this.cp             = 0;
        this.pais           = "";
        this.localidad      = "";
        this.barrio         = "";
        this.imagenes       = new ArrayList();
        this.perfil         = 0;
        this.publicaciones  = new ArrayList();
    }

    protected Domicilio( Parcel in ){

        super(in);
        this.id             = in.readInt();
        this.direccion      = in.readString();
        this.ubicacion      = in.readParcelable(Ubicacion.class.getClassLoader());
        this.cp             = in.readInt();
        this.pais           = in.readString();
        this.localidad      = in.readString();
        this.barrio         = in.readString();
        in.readList(imagenes, String.class.getClassLoader());
        this.perfil         = in.readInt();
        in.readList(this.publicaciones, Integer.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    public void addImagen(String imagen) {
        this.imagenes.add(imagen);
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public ArrayList<Integer> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(ArrayList<Integer> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public void addPublicacion(int publicacion) {
        this.publicaciones.add(publicacion);
    }

    public static final Creator<Domicilio> CREATOR = new Creator<Domicilio>() {
        @Override
        public Domicilio createFromParcel(Parcel in) {
            return new Domicilio(in);
        }

        @Override
        public Domicilio[] newArray(int size) {
            return new Domicilio[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeString(this.direccion);
        dest.writeParcelable(ubicacion, flags);
        dest.writeInt(this.cp);
        dest.writeString(this.pais);
        dest.writeString(this.localidad);
        dest.writeString(this.barrio);
        dest.writeList(this.imagenes);
        dest.writeInt(this.perfil);
        dest.writeList(publicaciones);

    }
}
