package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.ResponseReceiver;

import java.util.List;

/**
 * Created by Pablo on 25/04/2017.
 */

public abstract class ClientHandler<T> implements ResponseReceiver {

    /*Estas variables sirven para identificar que metodo invocó la peticion,
    * es util para saber en los métodos onResponseReceived y onErrorReceived quien lanzó la petición
    * y asi actuar consecuentemente
    */
    protected static final String GET_ALL_ID = "get_all";
    protected static final String GET_ID = "get";
    protected static final String INSERT_ID = "insert";
    protected static final String UPDATE_ID = "update";
    protected static final String DELETE_ID = "delete";
    protected static final String DELETE_ELEMENTS_ID = "delete_elements";

    public abstract void getAllElements();

    public abstract void getElement(long id);

    public abstract void insertElement(T element);

    public abstract void updateElement(T element);

    public abstract void deleteElement(long id);

    public abstract void deleteElements(List<Long> ids);

}
