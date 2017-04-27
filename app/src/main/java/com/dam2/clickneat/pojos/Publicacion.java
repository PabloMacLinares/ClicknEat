package com.dam2.clickneat.pojos;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ferna on 27/04/2017.
 */

public class Publicacion {

    private int id;
    private Date horaInicio;
    private Date horaFin;
    private Date fecha;
    private int plazasTotales;
    private float precio;
    private int domicilio;
    private String foto;
    private ArrayList<String> platos;
    private int usuario;
    private ArrayList<Reserva> reservas;
    private boolean completo;
    private String descripcion;
    private String titulo;


    public Publicacion() {

        this.id             = 0;
        this.horaInicio     = new Date(System.currentTimeMillis());
        this.horaFin        = new Date(System.currentTimeMillis());
        this.fecha          = new Date(System.currentTimeMillis());
        this.precio         = 0;
        this.domicilio      = 0;
        this.foto           = "";
        this.platos         = new ArrayList();
        this.usuario        = 0;
        this.reservas       = new ArrayList();
        this.completo       = false;
        this.descripcion    = "";
        this.titulo         = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getPlazasTotales() {
        return plazasTotales;
    }

    public void setPlazasTotales(int plazasTotales) {
        this.plazasTotales = plazasTotales;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(int domicilio) {
        this.domicilio = domicilio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public ArrayList<String> getPlatos() {
        return platos;
    }

    public void setPlatos(ArrayList<String> platos) {
        this.platos = platos;
    }

    public void addPlato(String plato) {
        this.platos.add(plato);
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void addReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
