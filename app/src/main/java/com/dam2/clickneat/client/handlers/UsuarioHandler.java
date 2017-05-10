package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.Client;
import com.dam2.clickneat.pojos.Usuario;
import com.dam2.clickneat.utils.JsonHelper;

import java.util.List;

/**
 * Created by ferna on 28/04/2017.
 */

public class UsuarioHandler extends ClientHandler<Usuario> {

    /*Estas variables sirven para identificar que metodo invocó la peticion,
    * es util para saber en los métodos onResponseReceived y onErrorReceived quien lanzó la petición
    * y asi actuar consecuentemente
    */
    private static final String GET_ALL_ID = "get_all";
    private static final String GET_ID = "get";
    private static final String INSERT_ID = "insert";
    private static final String UPDATE_ID = "update";
    private static final String DELETE_ID = "delete";
    private static final String DELETE_ELEMENTS_ID = "delete_elements";

    @Override
    public void getAllElements() {
        Client.makeRequest("usuario", null, Client.RequestMethod.GET, GET_ALL_ID, this);
    }

    @Override
    public void getElement(long id) {

        Client.makeRequest("usuario/" + id, null, Client.RequestMethod.GET, GET_ID, this);
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

        Client.makeRequest("usuario", JsonHelper.toJson(ids), Client.RequestMethod.DELETE, DELETE_ELEMENTS_ID, this);
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
