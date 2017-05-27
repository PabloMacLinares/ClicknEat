package com.dam2.clickneat.views.publicacion;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.pojos.Reserva;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.recyclerview.adapters.publicacion.PlatosAdapter;
import com.dam2.clickneat.recyclerview.expandable.singlecheck.Adapter.SingleCheckDomicilioAdapter;
import com.dam2.clickneat.recyclerview.expandable.singlecheck.Model.SingleCheckDomicilio;
import com.dam2.clickneat.utils.DateHelper;
import com.dam2.clickneat.utils.DialogHelper;
import com.dam2.clickneat.utils.ImageHelper;
import com.dam2.clickneat.utils.ImagePicker;
import com.dam2.clickneat.utils.JsonHelper;
import com.dam2.clickneat.utils.JwtHelper;
import com.dam2.clickneat.utils.StringHelper;
import com.dam2.clickneat.views.BaseActivity;
import com.dam2.clickneat.views.chats.chat.ChatView;
import com.dam2.clickneat.views.perfilUsuario.PerfilUsuarioView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JAVIER on 18/05/2017.
 */

public class PublicacionView extends BaseActivity implements PublicacionContract.View,
                                                             DatePickerDialog.OnDateSetListener,
                                                             TimePickerDialog.OnTimeSetListener {

    //UI Components
    //private NestedScrollView nestedScrollView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;

    private ImageView ivPublicacion;
    private EditText edTitulo, edDescripcion, edGourmets, edCoste, edFecha, edHoraIni, edHoraFin;
    private FloatingActionButton fab;

    private RecyclerView rvPlatos;
    private PlatosAdapter adapterPlatos;
    private Button btAddPlato;

    private RecyclerView rvDomicilios;
    private SingleCheckDomicilioAdapter adapterDomicilios;

    private LinearLayout llDatosPersonales;
    private Button btPerfil, btConversacion;

    //Logic Components

    public static final int INSERT_ACTION = 0;
    public static final int EDIT_ACTION   = 1;
    public static final int VIEW_ACTION   = 2;

    private boolean isHourInitialSelected;
    private int idReserva;
    private PublicacionContract.Presenter presenter;

    //Imagenes
    private Uri uriSelectedImage;

    //Permissions
    private boolean isPermissionGranted;
    private DexterBuilder permissionChecker;

    /*
    * TODO  Variable que controla el tipo de accion que se realizará en la vista
    *   0 : Accion de insertar
    *   1 : Accion de actualizar
    *   2 : Accion de ver
    * */
    private int typeAction;
    private Publicacion publicacion;
    private PerfilUsuario perfilUsuario;
    private int idUsuario;
    private int idUsuarioDispositivo;

    private ArrayList<Domicilio> domicilios;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Quitamos la barra de titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_publicacion_crear_editar);

        this.typeAction     = savedInstanceState != null    ? savedInstanceState.getInt("typeAction")
                                                            : getIntent().getIntExtra("typeAction", Preferences.DEFAULT_INTEGER);

        this.publicacion    = savedInstanceState != null    ? (Publicacion) savedInstanceState.getParcelable("publicacion")
                                                            : (getIntent().getParcelableExtra("publicacion") == null ? new Publicacion()
                                                            : (Publicacion) getIntent().getParcelableExtra("publicacion")) ;


        this.perfilUsuario  = savedInstanceState != null    ? (PerfilUsuario) savedInstanceState.getParcelable("perfil")
                                                            : (PerfilUsuario) getIntent().getParcelableExtra("perfil");

        /*this.idUsuario      = savedInstanceState != null    ? savedInstanceState.getInt(getString(R.string.preferences_id_user))
                                                            : (this.typeAction != EDIT_ACTION ? getIntent().getIntExtra(getString(R.string.preferences_id_user), Preferences.DEFAULT_INTEGER)
                                                            : this.publicacion.getUsuario());*/

        this.idReserva      = savedInstanceState != null    ? savedInstanceState.getInt("idReserva") : 0;

        this.domicilios     = savedInstanceState != null    ? savedInstanceState.<Domicilio>getParcelableArrayList("domicilios") : null;


        this.isPermissionGranted    = savedInstanceState != null ? savedInstanceState.getBoolean("isPermissionGranted") : false;
        this.uriSelectedImage       = savedInstanceState != null ? (Uri) savedInstanceState.getParcelable("uriSelectedImage") : null;

        this.idUsuarioDispositivo   = savedInstanceState != null ? savedInstanceState.getInt("idUsuarioDispositivo")
                                                                 : getIdUserLogged();

        this.presenter   = new PublicacionPresenter(this);

        init();
    }

    @Override
    protected void onResume() {

        super.onResume();

        switch ( this.typeAction ) {

            case VIEW_ACTION: {

                if ( this.idReserva == 0 ) {

                    this.presenter.onLoadReservas(this.publicacion.getId());
                }

                break;
            }

            default: {

                if ( this.domicilios == null ) {

                    this.presenter.onLoadDomiciliosUsuario(this.perfilUsuario.getId());
                }

                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if ( this.typeAction != VIEW_ACTION ) {

            getMenuInflater().inflate(R.menu.publicacion_menu, menu);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // Respond to the action bar's Up/Home button
            case android.R.id.home: {

                Intent upIntent = NavUtils.getParentActivityIntent(this);

                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {

                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    PublicacionView.this.finish();
                    NavUtils.navigateUpTo(this, upIntent);
                }

                return true;

            }

            case R.id.publicacion_imagen: {

                if ( !PublicacionView.this.isPermissionGranted ) {

                    PublicacionView.this.permissionChecker.check();
                }
                else {

                    Intent intent = ImagePicker.getPickImageIntent(PublicacionView.this);
                    startActivityForResult(intent, ImagePicker.PICK_IMAGE_ID);
                }

                return true;
            }


        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putInt(getString(R.string.preferences_id_user), this.idUsuario);
        outState.putInt("idUsuarioDispositivo", this.idUsuarioDispositivo);
        outState.putParcelable("perfil", this.perfilUsuario);
        outState.putParcelable("publicacion", this.publicacion);
        outState.putInt("typeAction", this.typeAction);
        outState.putParcelableArrayList("domicilios", this.domicilios);
        outState.putInt("idReserva", this.idReserva);
        outState.putBoolean("isPermissionGranted", this.isPermissionGranted);
        outState.putParcelable("uriSelectedImage", this.uriSelectedImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch ( requestCode ) {

            case ImagePicker.PICK_IMAGE_ID: {

                Bitmap bitmap           = ImagePicker.getImageFromResult(this, resultCode, data);

                if ( bitmap != null ) {

                    //Actualizamos los datos
                    this.uriSelectedImage   = ImagePicker.getUriFromResult(this, resultCode, data);
                    this.publicacion.setFoto(ImageHelper.bitmapTobase64String(bitmap));

                    Picasso.with(this)
                           .load(this.uriSelectedImage)
                           .placeholder(R.drawable.chimichangas)
                           .error(R.drawable.chimichangas)
                           .memoryPolicy(MemoryPolicy.NO_CACHE)
                           .networkPolicy(NetworkPolicy.NO_CACHE)
                           .into(this.ivPublicacion);
                }

                break;
            }
        }
    }

    /***********************************************************************
     *  INTERFACES
     */
    @TargetApi(Build.VERSION_CODES.N)
    private void showDatePicker() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PublicacionView.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMinDate(java.util.Calendar.getInstance());
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void showTimePicker() {

        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                PublicacionView.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );

        java.util.Calendar timeNow = java.util.Calendar.getInstance();
        dpd.setMinTime(timeNow.get(java.util.Calendar.HOUR_OF_DAY), timeNow.get(java.util.Calendar.MINUTE), 0);
        dpd.show(getFragmentManager(), "TimePickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        //Asignamos la fecha a la publicacion
        java.util.Calendar now = java.util.Calendar.getInstance();
        now.set(year, monthOfYear, dayOfMonth);

        this.publicacion.setFecha(now.getTime());
        this.edFecha.setText(StringHelper.dateToStringPublicacion(now.getTime()));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        java.util.Calendar now = java.util.Calendar.getInstance();
        now.set(now.get(java.util.Calendar.YEAR), now.get(java.util.Calendar.MONTH), now.get(java.util.Calendar.DAY_OF_MONTH), hourOfDay, minute);

        if ( this.isHourInitialSelected ) {

            this.publicacion.setHoraInicio(now.getTime());
            this.edHoraIni.setText(StringHelper.dateToString(now.getTime()));
        }
        else {

            this.publicacion.setHoraFin(now.getTime());
            this.edHoraFin.setText(StringHelper.dateToString(now.getTime()));
        }

    }


    @Override
    public void viewDomiciliosUsuario(ArrayList<Domicilio> domicilios) {

        //Visualizamos el recyclewView de Domicilios
        this.domicilios = domicilios;
        seeDomicilios();
    }

    @Override
    public void viewLoadedReservas(ArrayList<Reserva> reservas) {

        //Aqui solo llegaremos si estamos en el modo de ver, chequearemos si el usuario ha reservado previamente
        for ( Reserva reserva : reservas ) {

            if ( reserva.getUsuario() == this.idUsuarioDispositivo ) {

                this.idReserva = reserva.getId();

                //Debemos de cambiar el icono del floating action button
                this.fab.setImageResource(R.drawable.ic_delete_forever_white_24px);
                break;
            }
        }


    }

    @Override
    public void viewError(String error) {

        Snackbar.make(this.rvPlatos, error, Snackbar.LENGTH_LONG).show();
        if ( progressDialog.isShowing() ) progressDialog.dismiss();
    }

    @Override
    public void viewNoError(String noerror) {
        Snackbar.make(this.rvPlatos, noerror, Snackbar.LENGTH_LONG).show();
        if ( progressDialog.isShowing() ) progressDialog.dismiss();
    }

    @Override
    public void viewRemovedReserva() {

        this.idReserva = 0;
        loadActionFAB();

        this.progressDialog.dismiss();
    }

    @Override
    public void viewAddReserva(long idReserva) {

        this.idReserva = (int)idReserva;
        loadActionFAB();

        this.progressDialog.dismiss();
    }

    @Override
    public void viewAddPublicacion(Publicacion publicacion) {

        this.publicacion = publicacion;
        this.typeAction  = EDIT_ACTION;
        loadPublicacionUI();
        loadActionFAB();

        this.progressDialog.dismiss();

    }

    @Override
    public void viewConversacion(Conversacion conversacion) {

        //Obtenemos los metadatas para la conversacion
        ConversacionMetadata metadata = null;

        for ( ConversacionMetadata cm : conversacion.getMetadatas() ) {

            if ( cm.getUsuario() == this.idUsuarioDispositivo) {

                metadata = cm;
                break;
            }
        }

        Intent intent = new Intent(PublicacionView.this, ChatView.class);
        intent.putExtra("perfilUsuario", PublicacionView.this.perfilUsuario);
        intent.putExtra(getString(R.string.preferences_id_user), PublicacionView.this.idUsuarioDispositivo);
        intent.putExtra("metadata", metadata);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(intent);

        this.progressDialog.dismiss();
    }

    private void init() {

        //Collapsing Toolbar Layout
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "PUBLICACION_IMAGE");

        this.toolbar                 = (Toolbar) findViewById(R.id.toolbar);
        this.collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        this.collapsingToolbarLayout.setTitle(this.publicacion.getTitulo().isEmpty() ? "Nueva Publicacion" : this.publicacion.getTitulo());
        this.collapsingToolbarLayout.setExpandedTitleColor(ResourcesCompat.getColor(getResources(), android.R.color.transparent, null));

        //Back to parent

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));*/

        this.ivPublicacion      = (ImageView) findViewById(R.id.imagenDestacada);
        this.edTitulo           = (EditText) findViewById(R.id.tituloPlato);
        this.edDescripcion      = (EditText) findViewById(R.id.edDescripcion);
        this.edGourmets         = (EditText) findViewById(R.id.numeroMaxGourmets);
        this.edCoste            = (EditText) findViewById(R.id.dineroPlato);
        this.edFecha            = (EditText) findViewById(R.id.publicacion_fecha);
        this.edHoraIni          = (EditText) findViewById(R.id.publicacion_hora_ini);
        this.edHoraFin          = (EditText) findViewById(R.id.publicacion_hora_fin);
        this.fab                = (FloatingActionButton) findViewById(R.id.fabPublicacion);
        this.llDatosPersonales  = (LinearLayout) findViewById(R.id.contacta_publicacion);
        this.btPerfil           = (Button) findViewById(R.id.ver_perfil_publicacion);
        this.btConversacion     = (Button) findViewById(R.id.hablar_publicacion);
        this.btAddPlato         = (Button) findViewById(R.id.btAddPlato);

        this.rvDomicilios       = (RecyclerView) findViewById(R.id.rv_publicacion_domicilios);

        this.rvDomicilios.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Progress Dialog
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.action_update_app));
        progressDialog.setCancelable(false);

        //Si existen domicilios cargados los cargamos en el adaptador
        /*if ( this.domicilios != null ) {

            this.adapterDomicilios  = new SingleCheckDomicilioAdapter(getListCheckerDomicilios(domicilios));
            this.rvDomicilios.setAdapter(this.adapterDomicilios);
        }*/

        this.rvPlatos           = (RecyclerView) findViewById(R.id.rvPlatos);
        this.adapterPlatos      = new PlatosAdapter();

        this.rvPlatos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.rvPlatos.setAdapter(this.adapterPlatos);
        this.adapterPlatos.setEditable(this.typeAction != 2);

        //Agregamos los eventos
        if ( this.typeAction != VIEW_ACTION ) {


            View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if ( hasFocus ) {

                        if ( v.getId() != R.id.publicacion_fecha ) {

                            PublicacionView.this.isHourInitialSelected = v.getId() == R.id.publicacion_hora_ini;
                            showTimePicker();
                        }
                        else {

                            showDatePicker();
                        }
                    }
                }
            };

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ( v.getId() != R.id.publicacion_fecha ) {

                        PublicacionView.this.isHourInitialSelected = v.getId() == R.id.publicacion_hora_ini;
                        showTimePicker();
                    }
                    else {

                        showDatePicker();
                    }
                }
            };

            this.edFecha.setOnFocusChangeListener(focusChangeListener);
            this.edHoraIni.setOnFocusChangeListener(focusChangeListener);
            this.edHoraFin.setOnFocusChangeListener(focusChangeListener);

            this.edFecha.setOnClickListener(clickListener);
            this.edHoraIni.setOnClickListener(clickListener);
            this.edHoraFin.setOnClickListener(clickListener);

        }
        else {

            this.edTitulo.setEnabled(false);
            this.edDescripcion.setEnabled(false);
            this.edGourmets.setEnabled(false);
            this.edCoste.setEnabled(false);
            this.edFecha.setEnabled(false);
            this.edHoraIni.setEnabled(false);
            this.edHoraFin.setEnabled(false);
            this.btAddPlato.setVisibility(View.GONE);
            this.adapterPlatos.setEditable(false);

            //Cargamos los domicilios cuando no estemos creando o modificando la publicacion
            seeDomicilios();

            //Cargamos los datos personales para que se puedan acceder a ellos solo cuando estamos viendo la publicacion
            this.llDatosPersonales.setVisibility(View.VISIBLE);

            this.btConversacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PublicacionView.this.presenter.onLoadConversacionUsers(PublicacionView.this.idUsuarioDispositivo, PublicacionView.this.publicacion.getUsuario());
                    PublicacionView.this.progressDialog.setMessage(getString(R.string.action_loading_app));
                    PublicacionView.this.progressDialog.show();
                }
            });

            this.btPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(PublicacionView.this, PerfilUsuarioView.class);
                    intent.putExtra(getString(R.string.preferences_id_user), PublicacionView.this.perfilUsuario.getUsuario());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivity(intent);
                }
            });

            //RecyclewView platos no sera visible en este modo si no tiene platos
            this.rvPlatos.setVisibility(this.publicacion.getPlatos() == null || this.publicacion.getPlatos().isEmpty() ? View.GONE : View.VISIBLE);
        }

        this.btAddPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PublicacionView.this.adapterPlatos.addNewPlato();
            }
        });

        //Permissions
        this.permissionChecker = Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                        PublicacionView.this.isPermissionGranted = report.areAllPermissionsGranted();

                        if ( !report.areAllPermissionsGranted() ) {

                            DialogOnAnyDeniedMultiplePermissionsListener.Builder
                                    .withContext(PublicacionView.this)
                                    .withTitle(getString(R.string.title_dialog_permission_images))
                                    .withMessage(getString(R.string.title_dialog_permission_images))
                                    .withButtonText(android.R.string.ok)
                                    .withIcon(R.drawable.ic_menu_camera)
                                    .build();
                        }
                        else {

                            Intent intent = ImagePicker.getPickImageIntent(PublicacionView.this);
                            startActivityForResult(intent, ImagePicker.PICK_IMAGE_ID);
                        }
                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                });


        //Si estamos viendo la publicacion el boton flotante sera utilizado para realizar o eliminar la reserva
        //Si estamos insertando o actualizando la publicacion será utilizado para dicha accion
        loadActionFAB();

        if ( this.publicacion != null ) {

            loadPublicacionUI();
        }
    }

    //Funcion utilizada para poder cargar los domicilios dentro del adaptador expansible
    private ArrayList<SingleCheckDomicilio> getListCheckerDomicilios(ArrayList<Domicilio> domicilios) {

        SingleCheckDomicilio singleCheckDomicilio = new SingleCheckDomicilio("Domicilios", domicilios, R.drawable.ic_home_black_24px);
        ArrayList<SingleCheckDomicilio> listCheckDomicilio = new ArrayList();

        listCheckDomicilio.add(singleCheckDomicilio);

        return listCheckDomicilio;
    }

    //Funcion utilizada para poder volcar la informacion de los domicilios en UI
    private void seeDomicilios() {

        if ( domicilios != null && domicilios.size() > 0 ) {

            this.rvDomicilios.setVisibility(View.VISIBLE);

            this.adapterDomicilios = new SingleCheckDomicilioAdapter(getListCheckerDomicilios(domicilios));
            this.rvDomicilios.setAdapter(this.adapterDomicilios);

            if ( this.publicacion.getDomicilio().getId() > 0 ) {

                this.adapterDomicilios.checkDomicilio(this.publicacion.getDomicilio());
            }
        }

    }

    //Funcion utilizada para poder eliminar una reserva
    private void removeReserva() {

        String title    = getString(R.string.remove_reserva_title);
        String message  = getString(R.string.remove_reserva_message);
        DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Eliminamos la reserva del usuario
                PublicacionView.this.presenter.onRemoveReserva(PublicacionView.this.idReserva);
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        };

        DialogHelper.getDialog(this, title, message, okListener, cancelListener, null).show();

    }

    //Funcion utilizada para poder cargar la accion de nuestro FAB
    private void loadActionFAB() {

        View.OnClickListener clickListener  = null;
        int icon                            = 0;

        switch (PublicacionView.this.typeAction) {

            case INSERT_ACTION: {

                clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PublicacionView.this.loadInfoIntoPublicacion();
                        PublicacionView.this.presenter.onAddPublicacion(PublicacionView.this.publicacion);

                        PublicacionView.this.progressDialog.setMessage(getString(R.string.action_insert_app));
                        PublicacionView.this.progressDialog.show();
                    }
                };

                icon = R.drawable.ic_add_white_24px;
                break;
            }

            case EDIT_ACTION: {

                clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PublicacionView.this.loadInfoIntoPublicacion();
                        PublicacionView.this.presenter.onUpdatePublicacion(PublicacionView.this.publicacion);

                        PublicacionView.this.progressDialog.setMessage(getString(R.string.action_update_app));
                        PublicacionView.this.progressDialog.show();
                    }
                };

                icon = R.drawable.ic_done_white_24px;
                break;
            }

            case VIEW_ACTION: {

                //Si está reservada se elimina la reserva
                clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ( PublicacionView.this.publicacion.isCompleto() ) {

                            String title    = getString(R.string.remove_reserva_title_completa);
                            String message  = getString(R.string.remove_reserva_message_completa);
                            DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //Eliminamos la reserva del usuario
                                   dialog.dismiss();
                                }
                            };

                            DialogHelper.getDialog(PublicacionView.this, title, message, okListener, null, null).show();
                        }
                        else {

                            if ( PublicacionView.this.idReserva > 0 ) {

                                //Mostraremos un AlertDialog para poder confirmar la cancelacion de la reserva
                                PublicacionView.this.removeReserva();

                            }
                            else {

                                //Se agrega la reserva
                                Reserva reserva = new Reserva();
                                reserva.setUsuario(PublicacionView.this.idUsuarioDispositivo);
                                reserva.setPublicacion(PublicacionView.this.publicacion.getId());

                                PublicacionView.this.presenter.onAddReserva(reserva);


                                PublicacionView.this.progressDialog.setMessage(getString(R.string.action_reserva_app));
                                PublicacionView.this.progressDialog.show();
                            }
                        }

                    }
                };

                icon = PublicacionView.this.publicacion.isCompleto() ? R.drawable.ic_lock_white_24px :
                        (PublicacionView.this.idReserva > 0 ? R.drawable.ic_delete_forever_white_24px : R.drawable.ic_room_service_white_24px);
                break;
            }
        }

        this.fab.setImageResource(icon);
        this.fab.setOnClickListener(clickListener);
    }

    //Funcion utilizada para visualizar en la interfaz la publicacion
    private void loadPublicacionUI() {

        //Imagen
        RequestCreator picasso;

        if ( this.uriSelectedImage == null ) {

            String imagen   = this.publicacion.getFoto() == null || this.publicacion.getFoto().isEmpty() ? "-" : this.publicacion.getFoto();
            picasso         = Picasso.with(this).load(imagen);
        }
        else {

            picasso = Picasso.with(this).load(this.uriSelectedImage);
        }

        picasso.networkPolicy(NetworkPolicy.NO_CACHE)
               .memoryPolicy(MemoryPolicy.NO_CACHE)
               .placeholder(R.drawable.chimichangas)
               .error(R.drawable.chimichangas)
               .into(this.ivPublicacion);

        this.edTitulo.setText(this.publicacion.getTitulo());
        this.edDescripcion.setText(this.publicacion.getDescripcion() == null ? "" : this.publicacion.getDescripcion());
        this.edGourmets.setText(String.valueOf(this.publicacion.getPlazasTotales()));
        this.edCoste.setText(String.valueOf(this.publicacion.getPrecio()));
        this.edFecha.setText(StringHelper.dateToStringPublicacion(this.publicacion.getFecha()));
        this.edHoraIni.setText(StringHelper.hourToString(this.publicacion.getHoraInicio()));
        this.edHoraFin.setText(StringHelper.hourToString(this.publicacion.getHoraFin()));

        //Cargamos los platos
        if ( this.publicacion.getPlatos() != null) {
            this.adapterPlatos.setPlatos(this.publicacion.getPlatos());
        }

    }

    //Funcion para cargar la informacion en la publicacion
    private void loadInfoIntoPublicacion() {

        this.publicacion.setTitulo(this.edTitulo.getText().toString());
        this.publicacion.setDescripcion(this.edDescripcion.getText().toString());
        this.publicacion.setPlazasTotales(Integer.parseInt(this.edGourmets.getText().toString()));
        this.publicacion.setPrecio(Double.parseDouble(this.edCoste.getText().toString()));
        this.publicacion.setFecha(DateHelper.dateFromString(this.edFecha.getText().toString(), DateHelper.DATE_TYPE));
        this.publicacion.setHoraInicio(DateHelper.dateFromString(this.edHoraIni.getText().toString(), DateHelper.HOUR_TYPE));
        this.publicacion.setHoraFin(DateHelper.dateFromString(this.edHoraFin.getText().toString(), DateHelper.HOUR_TYPE));
        this.publicacion.setPlatos(this.adapterPlatos.getPlatos());
        this.publicacion.setDomicilio(this.adapterDomicilios.getCheckedDomicilio());
        this.publicacion.setUsuario(this.publicacion.getUsuario() >= 0 ? this.publicacion.getUsuario() : this.idUsuarioDispositivo);
    }

    //Funcion utilizada para obtener el ID del usuario logueado en el dispositivo
    private int getIdUserLogged() {

        Preferences preferences = new Preferences(this);
        String token            = preferences.getString(getString(R.string.preferences_api_token_user));

        return (Integer) JwtHelper.getElementFromToken(token, getString(R.string.preferences_id_user), Integer.class );
    }

}
