package com.dam2.clickneat.client.handlers;

import com.dam2.clickneat.client.ResponseReceiver;

import java.util.List;

/**
 * Created by Pablo on 25/04/2017.
 */

public abstract class ClientHandler<T> implements ResponseReceiver{

    public abstract void getAllElements();

    public abstract void getElement(long id);

    public abstract void insertElement(T element);

    public abstract void updateElement(T element);

    public abstract void deleteElement(long id);

    public abstract void deleteElements(List<Long> ids);

}
