package com.dam2.clickneat.pojos;

import android.os.Parcel;

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
    private Double precio;
    @Expose
    private Domicilio domicilio;
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
        this.plazasTotales  = 0;
        this.precio         = 0.0;
        this.domicilio      = new Domicilio();
        this.foto           = "";
        this.platos         = new ArrayList();
        this.usuario        = 0;
        this.reservas       = new ArrayList();
        this.completo       = false;
        this.descripcion    = "";
        this.titulo         = "";
    }

    protected Publicacion(Parcel in) {

        super(in);

        this.id             = in.readInt();
        this.horaInicio     = (Date)in.readSerializable();
        this.horaFin        = (Date)in.readSerializable();
        this.fecha          = (Date)in.readSerializable();
        this.plazasTotales  = in.readInt();
        this.precio         = in.readDouble();
        this.domicilio      = in.readParcelable(Domicilio.class.getClassLoader());
        this.foto           = in.readString();
        this.platos         = in.createStringArrayList();
        this.usuario        = in.readInt();
        in.readTypedList( this.reservas == null ? new ArrayList<Reserva>() : this.reservas, Reserva.CREATOR);
        this.completo       = in.readByte() != 0;
        this.descripcion    = in.readString();
        this.titulo         = in.readString();

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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
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


    public static final Creator<Publicacion> CREATOR = new Creator<Publicacion>() {
        @Override
        public Publicacion createFromParcel(Parcel in) {
            return new Publicacion(in);
        }

        @Override
        public Publicacion[] newArray(int size) {
            return new Publicacion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeSerializable(this.horaInicio);
        dest.writeSerializable(this.horaFin);
        dest.writeSerializable(this.fecha);
        dest.writeInt(this.plazasTotales);
        dest.writeDouble(this.precio);
        dest.writeParcelable(this.domicilio, flags);
        dest.writeString(this.foto);
        dest.writeStringList(platos);
        dest.writeInt(this.usuario);
        dest.writeTypedList(this.reservas);
        dest.writeByte((byte) (this.completo ? 1 : 0));
        dest.writeString(this.descripcion);
        dest.writeString(this.titulo);

    }
}
