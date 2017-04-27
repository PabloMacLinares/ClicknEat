package com.dam2.clickneat.pojos;

import java.util.ArrayList;

/**
 * Created by ferna on 27/04/2017.
 */

public class Domicilio {

    private int id;
    private String direccion;
    private ArrayList<String> ubicacion;
    private int cp;
    private String pais;
    private String localidad;
    private String barrio;
    private ArrayList<String> imagenes;
    private int perfil;
    private ArrayList<Publicacion> publicaciones;


    public Domicilio() {

        this.id             = 0;
        this.direccion      = "";
        this.ubicacion      = new ArrayList();
        this.cp             = 0;
        this.pais           = "";
        this.localidad      = "";
        this.barrio         = "";
        this.imagenes       = new ArrayList();
        this.perfil         = 0;
        this.publicaciones  = new ArrayList();
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

    public ArrayList<String> getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(ArrayList<String> ubicacion) {
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

    public ArrayList<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(ArrayList<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public void addPublicacion(Publicacion publicacion) {
        this.publicaciones.add(publicacion);
    }
}
