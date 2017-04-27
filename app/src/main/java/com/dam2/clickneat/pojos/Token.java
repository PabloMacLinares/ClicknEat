package com.dam2.clickneat.pojos;

/**
 * Created by ferna on 27/04/2017.
 */

public class Token {

    private int id;
    private String valor;

    public Token() {

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
