package com.dam2.clickneat.client;

import android.os.AsyncTask;

import com.dam2.clickneat.pojos.BaseClass;
import com.dam2.clickneat.utils.BufferHelper;
import com.dam2.clickneat.utils.JsonHelper;
import com.dam2.clickneat.utils.StringHelper;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pablo on 25/04/2017.
 */

public class Client {

    public enum RequestMethod{
        GET, POST, PUT, DELETE
    }
    //TODO chambiar la URL de la API
    public static final String API_URL = "http://clickandeat-fernan13.c9users.io/api/";

    //TODO data es el JSON, no se de que tipo es, por eso he puesto Object, ya lo cambiaré
    public static void makeRequest(String path, String data, RequestMethod method, ResponseReceiver receiver){
        /*switch (method){
            case GET:
                sendGetRequest(path, receiver);
                break;
            case POST:
                sendPostRequest(path, data, receiver);
                break;
            case PUT:
                sendPutRequest(path, data, receiver);
                break;
            case DELETE:
                sendDeleteRequest(path, data, receiver);
                break;
            default:
                break;
        }*/

        sendRequest(path, data, method, receiver);
    }

    //TODO Hay que ver que parametros necesitan los metodos get, post, put, delete
    private static void sendGetRequest(String path, ResponseReceiver receiver ){
        //Make request
        //if error -> sendError()
        //else -> sendResponse()

    }

    private static void sendPostRequest(String path, String data, ResponseReceiver receiver ){
        //Make request
        //if error -> sendError()
        //else -> sendResponse()
    }

    private static void sendPutRequest(String path, String data, ResponseReceiver receiver){
        //Make request
        //if error -> sendError()
        //else -> sendResponse()
    }

    private static void sendDeleteRequest(String path, String data, ResponseReceiver receiver){
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


    private static void sendRequest(String path, String data, final RequestMethod method, final ResponseReceiver receiver) {


        new AsyncTask<String, Void, Object>() {

            @Override
            protected Object doInBackground(String... params) {

                HttpURLConnection conn = null;
                String path                     = params[0];
                String data                     = params[1];

                try {

                    //Generamos la url a la cual nos conectaremos
                    URL url = new URL(API_URL + path);

                    //Abrimos la conexion sin realizarla
                    conn    = (HttpURLConnection) url.openConnection();

                    //Asignamos el tipo de peticion HTTP que se realizara
                    conn.setRequestMethod(method.name());

                    //Asignamos el tiempo de espera a la respuesta del servidor una vez se haya realizado la conexion
                    conn.setReadTimeout(10000);

                    //Asignamos el tiempo maximo de espera para establecer conexion con el servidor
                    conn.setConnectTimeout(15000);

                    //Cargamos los datos a enviar en la peticion
                    if ( method != RequestMethod.GET && data != null) {

                        conn.setDoOutput(true);

                        // Tamaño previamente conocido
                        conn.setFixedLengthStreamingMode(data.getBytes().length);

                        // Establecer application/json para enviar los datos
                        conn.setRequestProperty("Content-Type","application/json");

                        //Llenamos el buffer de salida con el JSON que queremos enviar al servidor
                        BufferHelper.writeOutputStream(conn.getOutputStream(), data);
                    }

                    //Establecemos la conexion
                    conn.connect();

                    //Obtenemos la respuesta del servidor
                    String jsonResponse = BufferHelper.readInputStream(conn.getInputStream());
                    JSONObject response = new JSONObject(jsonResponse);

                    int errorResponse       = response.getInt("error");
                    String dataResponse     = response.getString("data");
                    String jsonClassName    = JsonHelper.getClassNameForJson(dataResponse);

                    Object dataUI;

                    if ( jsonClassName.contains("JSON") ) {

                        //Obtenemos el nombre de la clase por la cual hemos realizado la peticion
                        String pojoClassName = StringHelper.stringToCamelCase(path.split("\\/")[0]);
                        String packageName   = "com.dam2.clickneat.pojos";

                        //Clase superior a partir de la cual podemos obtener el tipo del objeto
                        BaseClass baseClass  = (BaseClass)Class.forName(packageName + "." + pojoClassName).newInstance();
                        Type type            = jsonClassName.equals("JSONObject") ? baseClass.getType() : baseClass.getListType();

                        //Obtenemos la respuesta
                        dataUI = JsonHelper.fromJson(dataResponse, type);
                    }
                    else {

                        dataUI = dataResponse;
                    }

                    Object[] dataOnPostExecute = new Object[2];
                    dataOnPostExecute[0]     = errorResponse;
                    dataOnPostExecute[1]     = dataUI;

                    return dataOnPostExecute;

                }catch( Exception e ) {

                    System.out.println(e.getMessage());
                }
                finally {

                    //Finalizamos la conexion
                    if ( conn != null ) {

                        conn.disconnect();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                /*
                    Comprobamos el codigo de error, si este es distinto de 0 enviamos una respuesta positiva
                    si no lo es enviamos una respuesta negativa
                */
                if ( o != null ) {

                    Object[] values = (Object[]) o;
                    int errorCode   = (int) values[0];
                    Object data     =  values[1];


                    if ( errorCode == 0 ) {

                        sendResponse(receiver, data );
                    }
                    else {
                        sendError(receiver, (String)data);
                    }
                }
            }
        }.execute( path, data );
    }
}
