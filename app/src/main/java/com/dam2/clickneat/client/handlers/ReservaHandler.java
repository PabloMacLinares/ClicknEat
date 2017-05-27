package com.dam2.clickneat.client.handlers;
import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.pojos.ListIds;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.pojos.Reserva;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.List;

/**
 * Created by ferna on 30/04/2017.
 */

public class ReservaHandler extends ClientHandler<Reserva> {

    public ReservaHandler(DataReceiver<Reserva> dataReceiver) {
        super(dataReceiver);
    }

    @Override
    public void getAllElements() {

        Client.makeRequest("reserva", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("reserva/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    @Override
    public void insertElement(Reserva element) {

        Client.makeRequest("reserva" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(Reserva element) {

        Client.makeRequest("reserva/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("reserva/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("reserva", JsonHelper.toJson(new ListIds(ids)), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
    }

    @Override
    public void updateVariableElement(Reserva element, String variable) {

        Client.makeRequest("reserva/" + element.getId() + "/" + variable, JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    public void getReservasPublicacion(long idPublicacion) {

        Client.makeRequest("reserva?publicacion=" + idPublicacion, null, Client.RequestMethod.GET, GET_ALL_ID, this);
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
