package com.dam2.clickneat.pojos;

import java.util.ArrayList;

/**
 * Created by ferna on 27/04/2017.
 */

public class PerfilUsuario {

    private int id;
    private String nombre;
    private int usuario;
    private ArrayList<Domicilio> domicilios;
    private String imagen;
    private ArrayList<Comentario> comentariosRecibidos;
    private ArrayList<Comentario> comentariosDados;

    public PerfilUsuario() {

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
