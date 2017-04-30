package com.dam2.clickneat.client.handlers;
import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.pojos.ListIds;
import com.dam2.clickneat.pojos.Mensaje;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.List;

/**
 * Created by ferna on 30/04/2017.
 */

public class PublicacionHandler extends ClientHandler<Publicacion> {

    @Override
    public void getAllElements() {

        Client.makeRequest("publicacion", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("publicacion/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    @Override
    public void insertElement(Publicacion element) {

        Client.makeRequest("publicacion" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(Publicacion element) {

        Client.makeRequest("publicacion/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("publicacion/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("publicacion", JsonHelper.toJson(new ListIds(ids)), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
    }

    @Override
    public void onResponseReceived(Object data,String requestId) {
        System.out.println("RequestId: " + requestId);
        System.out.println(data);
    }

    @Override
    public void onErrorReceived(Object data, String requestId) {
        System.err.println("RequestId: " + requestId);
        System.err.println(data);
    }
}
