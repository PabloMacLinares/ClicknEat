package com.dam2.clickneat.pojos;

/**
 * Created by ferna on 29/04/2017.
 */

public class Ubicacion {

    private Double lat;
    private Double lng;

    public Ubicacion() {

        this(0.0, 0.0);
    }

    public Ubicacion( Double lat, Double lng ) {

        this.lat = lat;
        this.lng = lng;
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
}
