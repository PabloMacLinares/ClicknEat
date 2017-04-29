package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.pojos.Usuario;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferna on 28/04/2017.
 */

public class UsuarioHandler extends ClientHandler<Usuario> {

    @Override
    public void getAllElements() {
        Client.makeRequest("usuario", null, Client.RequestMethod.GET, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("usuario/" + id, null, Client.RequestMethod.GET, this);
    }

    @Override
    public void insertElement(Usuario element) {

        Client.makeRequest("usuario" , JsonHelper.toJson(element), Client.RequestMethod.POST, this);
    }

    @Override
    public void updateElement(Usuario element) {

        Client.makeRequest("usuario/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("usuario/" + id, null, Client.RequestMethod.DELETE, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("usuario", JsonHelper.toJson(ids), Client.RequestMethod.DELETE, this);
    }

    @Override
    public void onResponseReceived(Object data) {

        System.out.println(data);
    }

    @Override
    public void onErrorReceived(String error) {

        System.out.println(error);
    }

}
