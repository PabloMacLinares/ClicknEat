package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.pojos.ListIds;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.List;

/**
 * Created by ferna on 28/04/2017.
 */

public class PerfilUsuarioHandler extends ClientHandler<PerfilUsuario> {

    public PerfilUsuarioHandler(DataReceiver<PerfilUsuario> dataReceiver) {
        super(dataReceiver);
    }

    @Override
    public void getAllElements() {
        Client.makeRequest("perfil-usuario", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("perfil-usuario/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    @Override
    public void insertElement(PerfilUsuario element) {

        Client.makeRequest("perfil-usuario" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(PerfilUsuario element) {

        Client.makeRequest("perfil-usuario/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("perfil-usuario/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("perfil-usuario", JsonHelper.toJson(new ListIds(ids)), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
    }

    /*@Override
    public void onResponseReceived(Object data,String requestId) {
        System.out.println("RequestId: " + requestId);
        System.out.println(data);
    }

    @Override
    public void onErrorReceived(Object data, String requestId) {
        System.err.println("RequestId: " + requestId);
        System.err.println(data);
    }*/

}
