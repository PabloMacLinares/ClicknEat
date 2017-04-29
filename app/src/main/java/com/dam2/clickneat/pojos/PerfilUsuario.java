package com.dam2.clickneat.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by ferna on 27/04/2017.
 */

public class PerfilUsuario extends BaseClass {

    @Expose
    private int id;
    @Expose
    private String nombre;
    @Expose
    private int usuario;
    @Expose
    private ArrayList<Domicilio> domicilios;
    @Expose
    private String imagen;
    @Expose
    private ArrayList<Comentario> comentariosRecibidos;
    @Expose
    private ArrayList<Comentario> comentariosDados;

    public PerfilUsuario() {

        super( PerfilUsuario.class, new TypeToken<ArrayList<PerfilUsuario>>(){});

        this.id                     = 0;
        this.nombre                 = "";
        this.usuario                = 0;
        this.domicilios             = new ArrayList();
        this.imagen                 = "";
        this.comentariosRecibidos   = new ArrayList();
        this.comentariosDados       = new ArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Domicilio> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(ArrayList<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    public void addDomicilio(Domicilio domicilio) {
        this.domicilios.add(domicilio);
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<Comentario> getComentariosRecibidos() {
        return comentariosRecibidos;
    }

    public void setComentariosRecibidos(ArrayList<Comentario> comentariosRecibidos) {
        this.comentariosRecibidos = comentariosRecibidos;
    }

    public void addComentarioRecibido(Comentario comentario) {
        this.comentariosRecibidos.add(comentario);
    }

    public ArrayList<Comentario> getComentariosDados() {
        return comentariosDados;
    }

    public void setComentariosDados(ArrayList<Comentario> comentariosDados) {
        this.comentariosDados = comentariosDados;
    }

    public void addComentarioDado(Comentario comentario) {
        this.comentariosDados.add(comentario);
    }
}
