package com.dam2.clickneat.client;

/**
 * Created by Pablo on 25/04/2017.
 */

public interface ResponseReceiver {

    public void onResponseReceived(Object data, String requestId);

    public void onErrorReceived(Object data, String requestId);

}
