package com.dam2.clickneat.pojos;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by ferna on 30/04/2017.
 */

/**
 * Clase creada para la eliminacion de multiples pojos a traves de una peticion a la api
 */
public class ListIds {

    @Expose
    private List<Long> ids;

    public ListIds( List<Long> ids ) {

        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
