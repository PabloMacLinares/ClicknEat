package com.dam2.clickneat.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ferna on 29/04/2017.
 */

public class Ubicacion implements Parcelable {

    private int id;
    private Double lat;
    private Double lng;
    private int domicilio;

    public Ubicacion() {

        this(0, 0.0, 0.0, 0);
    }

    public Ubicacion( int id, Double lat, Double lng, int domicilio ) {

        this.id         = id;
        this.lat        = lat;
        this.lng        = lng;
        this.domicilio  = domicilio;
    }

    protected Ubicacion(Parcel in) {

        this.id         = in.readInt();
        this.lat        = in.readDouble();
        this.lng        = in.readDouble();
        this.domicilio  = in.readInt();
    }

    public static final Creator<Ubicacion> CREATOR = new Creator<Ubicacion>() {
        @Override
        public Ubicacion createFromParcel(Parcel in) {
            return new Ubicacion(in);
        }

        @Override
        public Ubicacion[] newArray(int size) {
            return new Ubicacion[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public int getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(int domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeInt(this.domicilio);
    }
}
