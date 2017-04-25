package com.dam2.clickneat.client;

/**
 * Created by Pablo on 25/04/2017.
 */

public interface ResponseReceiver {

    public void onResponseReceived(Object data);

    public void onErrorReceived(String error);

}
