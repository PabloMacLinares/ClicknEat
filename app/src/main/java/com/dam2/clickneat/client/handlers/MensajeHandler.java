package com.dam2.clickneat.client.handlers;
import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.pojos.ListIds;
import com.dam2.clickneat.pojos.Mensaje;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.List;

/**
 * Created by ferna on 30/04/2017.
 */

public class MensajeHandler extends ClientHandler<Mensaje> {

    public MensajeHandler(DataReceiver<Mensaje> dataReceiver) {
        super(dataReceiver);
    }

    @Override
    public void getAllElements() {

        Client.makeRequest("mensaje", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("mensaje/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    @Override
    public void insertElement(Mensaje element) {

        Client.makeRequest("mensaje" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(Mensaje element) {

        Client.makeRequest("mensaje/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("mensaje/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("mensaje", JsonHelper.toJson(new ListIds(ids)), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
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
