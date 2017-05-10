package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.pojos.ListIds;
import com.dam2.clickneat.pojos.Usuario;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.List;

/**
 * Created by ferna on 28/04/2017.
 */

public class UsuarioHandler extends ClientHandler<Usuario> {

    protected static final String LOGIN_ELEMENT = "login_element";
    protected static final String RESEND_MAIL   = "resend_mail";

    public UsuarioHandler(DataReceiver<Usuario> dataReceiver) {
        super(dataReceiver);
    }

    @Override
    public void getAllElements() {

        Client.makeRequest("usuario", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("usuario/" + id, null, Client.RequestMethod.GET, GET_ID, this);
    }

    public void loginUser(Usuario element) {

        Client.makeRequest("usuario/login", JsonHelper.toJson(element), Client.RequestMethod.POST, LOGIN_ELEMENT, this);
    }

    public void resendValidationEmail( Usuario element ) {

        Client.makeRequest("usuario/resend", JsonHelper.toJson(element), Client.RequestMethod.POST, RESEND_MAIL, this);
    }
    @Override
    public void insertElement(Usuario element) {

        Client.makeRequest("usuario" , JsonHelper.toJson(element), Client.RequestMethod.POST, INSERT_ID, this);
    }

    @Override
    public void updateElement(Usuario element) {

        Client.makeRequest("usuario/" + element.getId(), JsonHelper.toJson(element), Client.RequestMethod.PUT, UPDATE_ID, this);
    }

    @Override
    public void deleteElement(long id) {

        Client.makeRequest("usuario/" + id, null, Client.RequestMethod.DELETE, DELETE_ID, this);
    }

    @Override
    public void deleteElements(List<Long> ids) {

        Client.makeRequest("usuario", JsonHelper.toJson(new ListIds(ids)), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
    }

    /*@Override
    public void onResponseReceived(Object data, String requestId) {
        System.out.println("RequestId: " + requestId);
        System.out.println(data);
    }

    @Override
    public void onErrorReceived(Object data, String requestId) {
        System.err.println("RequestId: " + requestId);
        System.err.println(data);
    }*/

}
