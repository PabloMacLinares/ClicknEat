package com.dam2.clickneat.firebase;

import com.dam2.clickneat.R;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.client.handlers.TokenHandler;
import com.dam2.clickneat.pojos.Token;
import com.dam2.clickneat.preferences.Preferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.List;

/*
* Servicio de Firebase que nos permite registrar el token del usuario.
* Este servicio es llamado cada vez que el token se actualiza.
* De modo que cuando inicie el usuario la aplicacion por primera vez y se registre guardaremos
* su token en SP para posteriormente a traves de su identificador de usuario lo almacenemos en
* nuestro servidor.
* PD: Para futuras actualizaciones del token, es necesario que el identificador del usuario se
* encuentre en las SP ya que lo necesita para actualizarlo correctamente
* */
public class TokenService extends FirebaseInstanceIdService implements DataReceiver<Token> {

    private static final String TAG = "ServicioTokenPopCloud";
    private Preferences p;

    @Override
    public void onTokenRefresh() {

        // Get updated InstanceID token.
        String refreshedToken   = FirebaseInstanceId.getInstance().getToken();

        //Obtenemos las key de las sharedpreferences
        String keyToken         = this.getString(R.string.preferences_token);
        String keyIdUser        = this.getString(R.string.preferences_id_user);
        String keyIdToken       = this.getString(R.string.preferences_id_token);

        //Almacenamos el token del usuario
        this.p = new Preferences(this.getApplicationContext());
        this.p.setString( keyToken, refreshedToken);

        int idUsuario = this.p.getInteger(keyIdUser);

        //Si el usuario se ha logueado al menos una vez podremos actualizar su token
        if ( Integer.compare( idUsuario, Preferences.DEFAULT_INTEGER) != 0 ) {

            TokenHandler handler = new TokenHandler(this);
            Token token          = new Token();

            token.setUsuario(idUsuario);
            token.setValor(refreshedToken);

            //Buscamos en las preferencias el identificador del token para saber
            // si tenemos que actualizarlo o insertarlo

            int idToken = p.getInteger(keyIdToken);

            if ( Integer.compare( idToken, Preferences.DEFAULT_INTEGER) != 0 ) {

                //Actualizamos
                token.setId(idToken);
                handler.updateElement(token);
            }
            else {

                //Cuando insertemos el token en la respuesta del servidor se guardara el ID en sp
                handler.insertElement(token);
            }
        }

    }

    @Override
    public void onListReceived(List<Token> list) {

    }

    @Override
    public void onElementReceived(Token list) {

    }

    @Override
    public void onDataItemInsertedReceived(int id) {
        this.p.setInteger(this.getString(R.string.preferences_token), id);
    }

    @Override
    public void onDataNoErrorReceived(String noerror) {

    }

    @Override
    public void onDataErrorReceived(String error) {

    }
}
