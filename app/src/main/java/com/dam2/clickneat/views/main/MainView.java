package com.dam2.clickneat.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Token;
import com.dam2.clickneat.pojos.Usuario;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.utils.BitmapHelper;
import com.dam2.clickneat.utils.JwtHelper;
import com.dam2.clickneat.views.BaseActivity;
import com.dam2.clickneat.views.chats.ChatsView;
import com.dam2.clickneat.views.login.LoginView;
import com.dam2.clickneat.views.perfilUsuario.PerfilUsuarioView;
import com.dam2.clickneat.views.reservas.ReservasView;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainView extends BaseActivity
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static final int LOGIN_ACTION       = 1;
    private Picasso picasso;

    //UI Interface
    private NavigationView navigationView;
    private CircleImageView ivProfile;
    private TextView tvUsername;
    private TextView tvEmail;

    //Utils
    private MainContract.Presenter presenter;
    private Preferences preferences;
    private Usuario usuario;

    //Intent login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter   = new MainPresenter(this);
        preferences = new Preferences(this);
        picasso     = Picasso.with(this);

        init();
    }

    @Override
    protected void onResume() {

        super.onResume();

        //Debemos de generar el usuario
        this.presenter.onLoginUsuario(this.usuario);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch ( id ) {

            case R.id.nav_perfil: {

                Intent i = new Intent(MainView.this, PerfilUsuarioView.class);

                i.putExtra(getString(R.string.preferences_id_user), this.usuario.getId());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);

                break;
            }

            case R.id.nav_reservas: {

                Intent i = new Intent(MainView.this, ReservasView.class);
                i.putExtra(getString(R.string.preferences_id_user), this.usuario.getId());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);

                break;
            }


            case R.id.nav_conversacion: {

                Intent i = new Intent(MainView.this, ChatsView.class);
                startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                break;
            }

            case R.id.nav_logout: {

                //Siempre vaciamos el token de usuario para que no pueda iniciar sesion
                this.preferences.setString(getString(R.string.preferences_api_token_user), Preferences.DEFAULT_STRING);

                //Comprobamos que dicho usuario se habia logueado previamente a traves de las redes sociales de Faceboook o Google
                if ( AccessToken.getCurrentAccessToken() != null ) {

                    LoginManager.getInstance().logOut();
                }
                else
                {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                }

                this.finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void init() {

        loadUserInfo(false);

        navigationView  = (NavigationView) findViewById(R.id.nav_view);

        //Header
        View view   = navigationView.getHeaderView(0);
        ivProfile   = (CircleImageView) view.findViewById(R.id.header_profile);
        tvUsername  = (TextView) view.findViewById(R.id.header_username);
        tvEmail     = (TextView) view.findViewById(R.id.header_email);

    }

    @Override
    public void errorLogin(String error) {

        //Si existe algun tipo de error en el login redirigimos a la activity del login
        Intent i = new Intent(this, LoginView.class);
        startActivityForResult(i, LOGIN_ACTION);
    }

    @Override
    public void loginSuccess(String token) {

        //Almacenamos el token y nos quedamos en esta vista
        preferences.setString(getString(R.string.preferences_api_token_user), token);

        //Aqui debemos de visualizar la informacion en el drawer
        loadUserInfo(true);

        //Una vez que nuestro usuario se loguea debemos de actualizar su token y registrar su dispositivo
        String firebase_token   = preferences.getString(getString(R.string.preferences_token));
        Token tokenFirebaseUser = new Token();

        tokenFirebaseUser.setValor(firebase_token);
        tokenFirebaseUser.addUsuario(usuario.getId());

        //Actualizamos nuestro usuario
        usuario.addToken(tokenFirebaseUser);
        this.presenter.onUpdateUserElement(usuario, "tokens");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode >= 0 && resultCode == RESULT_OK ) {

            switch ( requestCode ) {

                case LOGIN_ACTION: {

                    loadUserInfo(true);
                    break;
                }

            }
        }
    }

    private void loadUserInfo(boolean showUI) {

        this.usuario     = new Usuario();

        //Debemos de obtener los datos del usuario
        String token     = this.preferences.getString(getString(R.string.preferences_api_token_user));

        int idUsuario    = token.equals(Preferences.DEFAULT_STRING) ? 0 : (Integer)JwtHelper.getElementFromToken(token, getString(R.string.preferences_id_user), Integer.class );
        int idPerfil     = token.equals(Preferences.DEFAULT_STRING) ? 0 : (Integer)JwtHelper.getElementFromToken(token, getString(R.string.preferences_id_perfil_user), Integer.class );
        String username  = token.equals(Preferences.DEFAULT_STRING) ? "" : (String)JwtHelper.getElementFromToken(token, getString(R.string.preferences_username), String.class );
        String password  = token.equals(Preferences.DEFAULT_STRING) ? "" : (String)JwtHelper.getElementFromToken(token, getString(R.string.preferences_password), String.class);
        String profile   = token.equals(Preferences.DEFAULT_STRING) ? "" : (String)JwtHelper.getElementFromToken(token, getString(R.string.preferences_profile), String.class);
        String email     = token.equals(Preferences.DEFAULT_STRING) ? "" : (String)JwtHelper.getElementFromToken(token, getString(R.string.preferences_email), String.class);

        this.usuario.setId(idUsuario);
        this.usuario.setUsername(username);
        this.usuario.setPassword(password);
        this.usuario.getPerfil().setId(idPerfil);
        this.usuario.getPerfil().setImagen(profile);
        this.usuario.getPerfil().setUsuario(idUsuario);
        this.usuario.setEmail(email);

        //Visualizamos la informacion del usuario en nuestra interfaz
        if ( showUI ) {

            BitmapHelper.loadBitmapAsynchronously(picasso, this.ivProfile, R.drawable.placeholder, profile);
            /*Picasso.with(this)
                    .load(profile)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(ivProfile);*/

            tvUsername.setText(username);
            tvEmail.setText(email);
        }
    }

}
