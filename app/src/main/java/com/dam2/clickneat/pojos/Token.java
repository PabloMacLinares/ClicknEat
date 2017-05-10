package com.dam2.clickneat.pojos;

import android.os.Parcel;
import android.os.Parcelable;

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
    @Expose
    private int usuario;

    public Token() {

        super( Token.class, new TypeToken<ArrayList<Token>>(){} );

        this.id         = 0;
        this.valor      = "";
        this.usuario    = 0;
    }

    protected Token(Parcel in) {
        super(in);

        this.id         = in.readInt();
        this.valor      = in.readString();
        this.usuario    = in.readInt();
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

    public void setUsuario(int usuario){
        this.usuario = usuario;
    }

    public int getUsuario() {
        return this.usuario;
    }

    public static final Creator<Token> CREATOR = new Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel in) {
            return new Token(in);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeString(this.valor);
        dest.writeInt(this.usuario);

    }

}
