package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.pojos.ListIds;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.List;

/**
 * Created by ferna on 28/04/2017.
 */

public class DomicilioHandler extends ClientHandler<Domicilio> {

    public DomicilioHandler(DataReceiver<Domicilio> dataReceiver) {
        super(dataReceiver);
    }

    @Override
    public void getAllElements() {
        Client.makeRequest("domicilio", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("domicilio/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    @Override
    public void insertElement(Domicilio element) {

        Client.makeRequest("domicilio" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(Domicilio element) {

        Client.makeRequest("domicilio/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("domicilio/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("domicilio", JsonHelper.toJson(new ListIds(ids)), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
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
