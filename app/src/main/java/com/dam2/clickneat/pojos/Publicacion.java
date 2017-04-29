package com.dam2.clickneat.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ferna on 27/04/2017.
 */

public class Publicacion extends BaseClass {

    @Expose
    private int id;
    @Expose
    private Date horaInicio;
    @Expose
    private Date horaFin;
    @Expose
    private Date fecha;
    @Expose
    private int plazasTotales;
    @Expose
    private float precio;
    @Expose
    private int domicilio;
    @Expose
    private String foto;
    @Expose
    private ArrayList<String> platos;
    @Expose
    private int usuario;
    @Expose
    private ArrayList<Reserva> reservas;
    @Expose
    private boolean completo;
    @Expose
    private String descripcion;
    @Expose
    private String titulo;


    public Publicacion() {

        super( Publicacion.class, new TypeToken<ArrayList<Publicacion>>(){});

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
