package com.dam2.clickneat.views.registro;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Usuario;
import com.dam2.clickneat.views.BaseActivity;
import com.dam2.clickneat.views.login.LoginView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class RegisterView extends BaseActivity implements RegisterContract.View {

    private static final int GOOGLE_SIGN_IN     = 1;

    //Facebook login
    private CallbackManager callbackManager;
    private LoginButton btFacebookSignIn;

    //Google login
    private Button btGoogleSignIn;
    /*private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;*/

    //UI Interface
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private View mRegisterView;
    private Button btRegister;
    private ProgressDialog progressDialog;

    //Presenter
    private RegisterPresenter presenter;

    //Variable que controla que tipo de usuario se registra
    private int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Inicializamos el SDK de Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_registro);

        this.presenter = new RegisterPresenter(this);

        init();

    }

    @Override
    public void registerError(String error) {

        showProgress(false);
        Snackbar.make(mRegisterView, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void registerSuccess(String token) {

        showProgress(false);
        Intent intent = getIntent();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(getString(R.string.preferences_api_token_user), token);
        intent.putExtra(getString(R.string.user_type_value), userType);
        setResult(RESULT_OK, intent);

        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode >= 0 ) {

            Usuario user = new Usuario();

            switch (requestCode) {

                case GOOGLE_SIGN_IN: {

                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                    if (result.isSuccess()) {

                        GoogleSignInAccount acct = result.getSignInAccount();

                        //Generamos el usuario

                        user.setUsername(acct.getDisplayName());
                        user.setEmail(acct.getEmail());
                        user.getPerfil().setImagen(acct.getPhotoUrl() != null ? acct.getPhotoUrl().toString() : null);
                        user.getPerfil().setNombre(acct.getDisplayName());
                        user.setEnabled(true);
                        user.setPassword(acct.getId());

                        user.setGoogle_id(acct.getId());
                        user.setGoogle_access_token(String.valueOf(acct.getIdToken()));

                        //Insertamos el usuario
                        showProgress(true);

                        RetrieveGoogleTokenTask task = new RetrieveGoogleTokenTask();
                        task.execute(user);

                    }


                    break;
                }

                default: {

                    callbackManager.onActivityResult(requestCode, resultCode, data);
                    break;
                }
            }


        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.
        etUsername.setError(null);
        etPassword.setError(null);
        etEmail.setError(null);

        // Store values at the time of the login attempt.
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email    = etEmail.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            etUsername.setError(getString(R.string.error_field_required));
            focusView = etUsername;
            cancel = true;
        }

        //Check for a valid mail
        if ( !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || TextUtils.isEmpty(email)) {

            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            //Generamos el usuario
            Usuario user = new Usuario();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);

            this.userType = getResources().getInteger(R.integer.user_type_normal);
            this.presenter.onRegisterUser(user);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void showProgress(final boolean show) {

        if ( show ) {

            progressDialog.show();
        }
        else {

            progressDialog.dismiss();
        }
    }

    private void init() {


        //Google SignIn

        /*gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();*/

        btGoogleSignIn = (Button) findViewById(R.id.email_sign_in_google);

        btGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
            }
        });

        //Facebook SignIn
        btFacebookSignIn = (LoginButton) findViewById(R.id.email_sign_in_facebook);
        btFacebookSignIn.setReadPermissions("email");
        btFacebookSignIn.registerCallback(callbackManager, new FacebookCallbackLogin());
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallbackLogin());


        //UI Components

        progressDialog = new ProgressDialog(RegisterView.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.action_register_in_app));
        progressDialog.setCancelable(false);

        etUsername     = (EditText) findViewById(R.id.usuarioRegistro);
        etEmail        = (EditText) findViewById(R.id.correoRegistro);
        etPassword     = (EditText) findViewById(R.id.contrasenaRegistro);

        btRegister     = (Button) findViewById(R.id.register_action_button);

        mRegisterView  = findViewById(R.id.register_view);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptRegister();
            }
        });

    }

    //UI Methods
    public void onClick(View v) {

        int id = v.getId();

        switch ( id ) {

            case R.id.gg: {

                btGoogleSignIn.performClick();
                break;
            }

            case R.id.fb: {
                btFacebookSignIn.performClick();
                break;
            }
        }

    }

    /*@Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Snackbar.make(btGoogleSignIn, getResources().getString(R.string.error_register_fcb),
                Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.connect_account), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);

                    }
                }).show();
    }*/



    private class RetrieveGoogleTokenTask extends AsyncTask<Usuario, Void, Usuario> {

        @Override
        protected Usuario doInBackground(Usuario... params) {
            Usuario user = params[0];

            String accountName = user.getEmail();
            String scopes      = "oauth2:profile email";
            String token       = null;

            try {

                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);

            } catch (Exception e) {}

            user.setGoogle_access_token(token);

            return user;
        }

        @Override
        protected void onPostExecute(Usuario user) {

            //Aqui debemos de registrar el usuario

            RegisterView.this.userType = getResources().getInteger(R.integer.user_type_social);
            RegisterView.this.presenter.onRegisterUser(user);
        }
    }

    private class FacebookCallbackLogin implements FacebookCallback<LoginResult> {

        @Override
        public void onSuccess(LoginResult loginResult) {

            GraphRequest graphrequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {

                            if (object != null) {

                                Usuario user = new Usuario();
                                String email = "", imagen = "", nombre = "", access_token = "", user_id = "", password = "";
                                AccessToken token = AccessToken.getCurrentAccessToken();

                                try {

                                    email           = object.getString("email");
                                    imagen          = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                    nombre          = object.getString("name");
                                    access_token    = token.getToken();
                                    user_id         = token.getUserId();
                                    password        = token.getUserId();

                                    if ( !email.isEmpty() ) {

                                        user.setUsername(nombre);
                                        user.setPassword(password);
                                        user.setEmail(email);
                                        user.getPerfil().setImagen(imagen);
                                        user.getPerfil().setNombre(nombre);
                                        user.setFacebook_access_token(access_token);
                                        user.setFacebook_id(user_id);
                                        user.setEnabled(true);

                                        //Registramos a nuestro usuario
                                        showProgress(true);
                                        RegisterView.this.userType = getResources().getInteger(R.integer.user_type_social);
                                        RegisterView.this.presenter.onRegisterUser(user);

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,picture.type(large)");
            graphrequest.setParameters(parameters);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
            graphrequest.executeAsync();

        }

        @Override
        public void onCancel() {

            Snackbar.make(btFacebookSignIn, getResources().getString(R.string.error_register_fcb),
                    Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.connect_account), new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            LoginManager.getInstance().logInWithReadPermissions(
                                    RegisterView.this, Arrays.asList("public_profile") );

                        }
                    }).show();
        }

        @Override
        public void onError(FacebookException error) {

            Snackbar.make(btFacebookSignIn, getResources().getString(R.string.error_register_fcb),
                    Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.connect_account), new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            LoginManager.getInstance().logInWithReadPermissions(
                                    RegisterView.this,Arrays.asList("public_profile") );

                        }
                    }).show();
        }
    }
}
