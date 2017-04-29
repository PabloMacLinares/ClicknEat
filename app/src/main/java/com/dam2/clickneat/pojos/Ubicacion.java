package com.dam2.clickneat.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ferna on 29/04/2017.
 */

public class Ubicacion implements Parcelable {

    private Double lat;
    private Double lng;

    public Ubicacion() {

        this(0.0, 0.0);
    }

    public Ubicacion( Double lat, Double lng ) {

        this.lat = lat;
        this.lng = lng;
    }

    protected Ubicacion(Parcel in) {

        this.lat = in.readDouble();
        this.lng = in.readDouble();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
    }
}
