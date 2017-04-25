package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.Client;

import java.util.List;

/**
 * Created by Pablo on 25/04/2017.
 */

public class ExampleHandler extends ClientHandler<Object>{

    @Override
    public void getAllElements() {
        Client.makeRequest("example", null, Client.RequestMethod.GET, this);
    }

    @Override
    public void getElement(long id) {
        Client.makeRequest("example/" + id, null, Client.RequestMethod.GET, this);
    }

    @Override
    public void insertElement(Object element) {
        Client.makeRequest("example", null, Client.RequestMethod.POST, this);
    }

    @Override
    public void updateElement(Object element) {
        Client.makeRequest("example/{element.id}", element, Client.RequestMethod.PUT, this);
    }

    @Override
    public void deleteElement(long id) {
        Client.makeRequest("example/" + id, null, Client.RequestMethod.DELETE, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {
        Client.makeRequest("example/", ids, Client.RequestMethod.DELETE, this);
    }

    //TODO crear interfaz que recive los eventos cuando se hace una peticion al Handler
    @Override
    public void onResponseReceived(Object data) {

    }

    @Override
    public void onErrorReceived(String error) {

    }
}
