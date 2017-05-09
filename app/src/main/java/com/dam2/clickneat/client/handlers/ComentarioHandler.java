package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.pojos.Comentario;
import com.dam2.clickneat.pojos.Comentario;
import com.dam2.clickneat.pojos.ListIds;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferna on 28/04/2017.
 */

public class ComentarioHandler extends ClientHandler<Comentario> {

    public ComentarioHandler(DataReceiver<Comentario> dataReceiver) {
        super(dataReceiver);
    }

    @Override
    public void getAllElements() {
        Client.makeRequest("comentario", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("comentario/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    @Override
    public void insertElement(Comentario element) {
        System.out.println(JsonHelper.toJson(element));
        Client.makeRequest("comentario" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(Comentario element) {

        Client.makeRequest("comentario/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("comentario/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("comentario", JsonHelper.toJson(new ListIds(ids)), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
    }

    /*@Override
    public void onResponseReceived(Object data,String requestId) {

        switch ( requestId ) {

            case GET_ALL_ID : {

                dataReceiver.onListReceived((ArrayList<Comentario>)data);
                break;
            }

            case GET_ID: {

                dataReceiver.onElementReceived((Comentario)data);
                break;
            }

            case INSERT_ID: {

                dataReceiver.onDataItemInsertedReceived(Integer.valueOf((String)data));
                break;
            }

            default: {

                dataReceiver.onDataNoErrorReceived((String)data);
            }
        }
    }

    @Override
    public void onErrorReceived(Object data, String requestId) {
        System.err.println("RequestId: " + requestId);
        System.err.println(data);

        dataReceiver.onDataErrorReceived((String)data);
    }*/

}
