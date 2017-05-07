package com.dam2.clickneat.client.handlers;
import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.pojos.ListIds;
import com.dam2.clickneat.pojos.Token;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.List;

/**
 * Created by ferna on 30/04/2017.
 */

public class TokenHandler extends ClientHandler<Token> {

    public TokenHandler(DataReceiver<Token> dataReceiver) {
        super(dataReceiver);
    }

    @Override
    public void getAllElements() {

        Client.makeRequest("token", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("token/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    @Override
    public void insertElement(Token element) {

        Client.makeRequest("token" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(Token element) {

        Client.makeRequest("token/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("token/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("token", JsonHelper.toJson(new ListIds(ids)), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
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
