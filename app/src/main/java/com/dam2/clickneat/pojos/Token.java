package com.dam2.clickneat.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by ferna on 27/04/2017.
 */

public class Token extends BaseClass {

    @Expose
    private int id;
    @Expose
    private String valor;

    public Token() {

        super( Token.class, new TypeToken<ArrayList<Token>>(){} );

        this.id         = 0;
        this.valor      = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
