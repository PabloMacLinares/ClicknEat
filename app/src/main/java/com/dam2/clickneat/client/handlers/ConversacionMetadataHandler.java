package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferna on 28/04/2017.
 */

public class ConversacionMetadataHandler extends ClientHandler<ConversacionMetadata> {

    public ConversacionMetadataHandler(DataReceiver<ConversacionMetadata> dataReceiver) {
        super(dataReceiver);
    }

    @Override
    public void getAllElements() {
        Client.makeRequest("conversacion-metadata", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("conversacion/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    @Override
    public void insertElement(ConversacionMetadata element) {

        Client.makeRequest("conversacion-metadata" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(ConversacionMetadata element) {

        Client.makeRequest("conversacion-metadata/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("conversacion-metadata/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("conversacion-metadata", JsonHelper.toJson(ids), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
    }

    //Metodo utilizado para poder obtener todas las conversaciones de un usuario
    public void getAllElementsByUserId(int id ) {

        Client.makeRequest("conversacion-metadata/usuario/" + id + "/", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    /*@Override
    public void onResponseReceived(Object data,String requestId) {

        switch ( requestId ) {

            case GET_ALL_ID: {

                dataReceiver.onListReceived((ArrayList<ConversacionMetadata>)data);

                break;
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
