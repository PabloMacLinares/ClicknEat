package com.dam2.clickneat.client;

/**
 * Created by Pablo on 25/04/2017.
 */

public class Client {

    public static enum RequestMethod{
        GET, POST, PUT, DELETE
    }
    //TODO chambiar la URL de la API
    public static final String API_URL = "host:port/";

    //TODO data es el JSON, no se de que tipo es, por eso he puesto Object, ya lo cambiaré
    public static void makeRequest(String path, Object data, RequestMethod method, ResponseReceiver receiver){
        switch (method){
            case GET:
                sendGetRequest();
                break;
            case POST:
                sendPostRequest();
                break;
            case PUT:
                sendPutRequest();
                break;
            case DELETE:
                sendDeleteRequest();
                break;
            default:
                break;
        }
    }

    //TODO Hay que ver que parametros necesitan los metodos get, post, put, delete
    private static void sendGetRequest(){
        //Make request
        //if error -> sendError()
        //else -> sendResponse()
    }

    private static void sendPostRequest(){
        //Make request
        //if error -> sendError()
        //else -> sendResponse()
    }

    private static void sendPutRequest(){
        //Make request
        //if error -> sendError()
        //else -> sendResponse()
    }

    private static void sendDeleteRequest(){
        //Make request
        //if error -> sendError()
        //else -> sendResponse()
    }

    private static void sendResponse(ResponseReceiver receiver, Object data){
        receiver.onResponseReceived(data);
    }

    private static void sendError(ResponseReceiver receiver, String error){
        receiver.onErrorReceived(error);
    }
}
