package com.dam2.clickneat.views.chats.chat;

import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.client.handlers.ConversacionHandler;
import com.dam2.clickneat.client.handlers.ConversacionMetadataHandler;
import com.dam2.clickneat.client.handlers.MensajeHandler;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.pojos.Mensaje;

import java.util.List;

/**
 * Created by Pablo on 25/04/2017.
 */

public class ChatModel implements ChatContract.Model  {

    private ChatContract.Presenter presenter;
    private ConversacionHandler conversacionHandler;
    private ConversacionMetadataHandler conversacionMetadataHandler;
    private MensajeHandler mensajeHandler;


    public ChatModel(ChatContract.Presenter presenter){

        this.presenter                   = presenter;
        this.conversacionHandler         = new ConversacionHandler(new DataConversacionReceiber());
        this.conversacionMetadataHandler = new ConversacionMetadataHandler(new DataConversacionMetadataReceiber());
        this.mensajeHandler              = new MensajeHandler(new DataMensajeReceiber());

    }

    @Override
    public void loadConversacion(int idConversacion) {

        conversacionHandler.getElement(idConversacion);
    }

    @Override
    public void readMensajes(ConversacionMetadata metadata) {

        metadata.setMensajesNoLeidos(0);
        conversacionMetadataHandler.updateElement(metadata);
    }

    @Override
    public void sendMensaje(Mensaje mensaje) {

        mensajeHandler.insertElement(mensaje);
    }

    @Override
    public void createConversacion(Conversacion conversacion) {
        conversacionHandler.insertElement(conversacion);
    }

    //Clases internas que nos permiten agregar los escuchadores de nuestros eventos utilizando la interfaz generica
    private class DataConversacionReceiber implements DataReceiver<Conversacion> {

        @Override
        public void onListReceived(List<Conversacion> list) {

        }

        @Override
        public void onElementReceived(Conversacion list) {
            presenter.loadConversacion(list);
        }

        @Override
        public void onDataItemInsertedReceived(int id) {
            conversacionHandler.getElement(id);
        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {

            presenter.loadError(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }

    }
    private class DataConversacionMetadataReceiber implements DataReceiver<ConversacionMetadata> {

        @Override
        public void onListReceived(List<ConversacionMetadata> list) {

        }

        @Override
        public void onElementReceived(ConversacionMetadata list) {

        }

        @Override
        public void onDataItemInsertedReceived(int id) {

        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {

            presenter.loadError(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }

    }
    private class DataMensajeReceiber implements DataReceiver<Mensaje> {

        @Override
        public void onListReceived(List<Mensaje> list) {

        }

        @Override
        public void onElementReceived(Mensaje list) {

        }

        @Override
        public void onDataItemInsertedReceived(int id) {

        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {

            presenter.loadError(error);
        }

        @Override
        public void onLoginReceived(String token) {

        }

    }

}
