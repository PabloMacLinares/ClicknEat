package com.dam2.clickneat.client;

import java.util.List;

/**
 * Created by ferna on 05/05/2017.
 */

/*
*   Interfaz generica que recibe las respuestas de cada uno de los handlers
*      Nota: Si se necesita multiples handlers dentro de un mismo modelo
*      se deben de crear clases internas que implementen la interfaz de comunicacion
* */
public interface DataReceiver<T> {

    void onListReceived(List<T> list);
    void onElementReceived(T list);
    void onDataItemInsertedReceived(int id);
    void onDataNoErrorReceived(String noerror);
    void onDataErrorReceived(String error);
    void onLoginReceived(String token);
}
