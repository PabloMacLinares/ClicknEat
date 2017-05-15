package com.dam2.clickneat.views.login;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Usuario;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.views.BaseActivity;
import com.dam2.clickneat.views.registro.RegisterView;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import jp.wasabeef.blurry.Blurry;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginView extends BaseActivity implements LoginContract.View, LoaderCallbacks<Cursor> {

    private LoginContract.Presenter presenter;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS  = 0;
    private static final int REGISTER_ACTION        = 1;
    private static final int GOOGLE_SIGN_IN         = 2;

    //Facebook login
    private CallbackManager callbackManager;
    private LoginButton btFacebookSignIn;

    //Google login
    private Button btGoogleSignIn;
    /*private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;*/

    // UI references.
    private AutoCompleteTextView usuarioView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private Button btLoginUser;
    private TextView tvRegisterUser;
    private ProgressDialog progressDialog;

    //Utils
    private Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Inicializamos el SDK de Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        presenter   = new LoginPresenter(this);
        preferences = new Preferences(this);

        init();

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(usuarioView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        usuarioView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = usuarioView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            usuarioView.setError(getString(R.string.error_field_required));
            focusView = usuarioView;
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

            this.presenter.onLoginUser(user);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        if ( show ) {

            progressDialog.show();
        }
        else {

            progressDialog.dismiss();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginView.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        usuarioView.setAdapter(adapter);
    }

    @Override
    public void viewErrorLogin(String error) {

        showProgress(false);
        Snackbar.make(mLoginFormView, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void viewLoginSuccess(String token) {

        showProgress(false);

        //Almacenamos el token recibido del servidor
        this.preferences.setString(getString(R.string.preferences_api_token_user), token);
        
        Intent intent = getIntent();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b = new Bundle();
        b.putParcelable("google", gso);

        setResult(RESULT_OK, intent);

        this.finish();
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK ) {

            switch ( requestCode ) {

                case REGISTER_ACTION: {

                    Bundle bundle = data.getExtras();
                    String token  = bundle.getString(getString(R.string.preferences_api_token_user));
                    int userType  = bundle.getInt(getString(R.string.user_type_value), Preferences.DEFAULT_INTEGER);

                    //Almacenamos el token recibido del servidor
                    this.preferences.setString(getString(R.string.preferences_api_token_user), token);

                    //Si el usuario el social la cuenta se cuentra habiltiada por defecto
                    if ( userType != Preferences.DEFAULT_INTEGER && userType == getResources().getInteger(R.integer.user_type_social) ) {

                        Intent intent = getIntent();

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        setResult(RESULT_OK, intent);

                        this.finish();
                    }
                    break;
                }

                case GOOGLE_SIGN_IN: {

                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                    if (result.isSuccess()) {

                        GoogleSignInAccount acct = result.getSignInAccount();

                        //Generamos el usuario
                        Usuario user = new Usuario();

                        user.setUsername(acct.getDisplayName());
                        user.setEmail(acct.getEmail());
                        user.setPassword(acct.getId());

                        showProgress(true);
                        this.presenter.onLoginUser(user);

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


    //Inicializamos los componentes de nuestra Activity
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

        // Set up the login form.
        usuarioView = (AutoCompleteTextView) findViewById(R.id.usuario);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.contraseña);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        btLoginUser = (Button) findViewById(R.id.email_sign_in_button);
        btLoginUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        tvRegisterUser = (TextView) findViewById(R.id.action_register);
        tvRegisterUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginView.this, RegisterView.class);
                startActivityForResult(i, REGISTER_ACTION);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);


        progressDialog = new ProgressDialog(LoginView.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.action_login_in_app));
        progressDialog.setCancelable(false);

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


    private class FacebookCallbackLogin implements FacebookCallback<LoginResult> {

        @Override
        public void onSuccess(LoginResult loginResult) {

            GraphRequest graphrequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {

                            if (object != null) {

                                Usuario user = new Usuario();
                                String email = "", nombre = "", password = "";
                                AccessToken token = AccessToken.getCurrentAccessToken();

                                try {

                                    email           = object.getString("email");
                                    nombre          = object.getString("name");
                                    password        = token.getUserId();

                                    if ( !email.isEmpty() ) {

                                        user.setUsername(nombre);
                                        user.setPassword(password);
                                        user.setEmail(email);

                                        showProgress(true);
                                        LoginView.this.presenter.onLoginUser(user);

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
                                    LoginView.this, Arrays.asList("public_profile") );

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
                                    LoginView.this,Arrays.asList("public_profile") );

                        }
                    }).show();
        }
    }

}

