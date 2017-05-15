package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferna on 28/04/2017.
 */

public class ConversacionHandler extends ClientHandler<Conversacion> {

    public ConversacionHandler(DataReceiver<Conversacion> dataReceiver) {
        super(dataReceiver);
    }

    @Override
    public void getAllElements() {
        Client.makeRequest("conversacion", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("conversacion/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    @Override
    public void insertElement(Conversacion element) {

        Client.makeRequest("conversacion" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(Conversacion element) {

        Client.makeRequest("conversacion/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void updateVariableElement(Conversacion element, String variable) {

        Client.makeRequest("conversacion/" + element.getId() + "/" + variable, JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("conversacion/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("conversacion", JsonHelper.toJson(ids), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
    }

    /*@Override
    public void onResponseReceived(Object data,String requestId) {

        switch ( requestId ) {

            case GET_ALL_ID: {

                dataReceiver.onListReceived((ArrayList<Conversacion>)data);

                break;
            }

            case GET_ID: {

                dataReceiver.onElementReceived((Conversacion) data);
                break;
            }

            case INSERT_ID: {

                dataReceiver.onDataItemInsertedReceived(Integer.valueOf((String)data));
            }

            default: {


            }
        }

        System.out.println("RequestId: " + requestId);
        System.out.println(data);
    }

    @Override
    public void onErrorReceived(Object data, String requestId) {

        dataReceiver.onDataErrorReceived((String)data);

        System.err.println("RequestId: " + requestId);
        System.err.println(data);
    }*/

}
