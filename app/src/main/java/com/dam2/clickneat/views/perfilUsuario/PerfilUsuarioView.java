package com.dam2.clickneat.views.perfilUsuario;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.dialogs.ComentarioFragmentDialog;
import com.dam2.clickneat.dialogs.DialogFragmentImageInterface;
import com.dam2.clickneat.dialogs.DialogFragmentInterface;
import com.dam2.clickneat.dialogs.DomicilioFragmentDialog;

import com.dam2.clickneat.pojos.Comentario;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.pojos.Usuario;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.recyclerview.adapters.perfil.ComentarioAdapter;
import com.dam2.clickneat.recyclerview.adapters.perfil.DomicilioAdapter;
import com.dam2.clickneat.recyclerview.adapters.perfil.PublicacionAdapter;
import com.dam2.clickneat.utils.ImageHelper;
import com.dam2.clickneat.utils.ImagePicker;
import com.dam2.clickneat.utils.JsonHelper;
import com.dam2.clickneat.utils.JwtHelper;
import com.dam2.clickneat.views.BaseActivity;
import com.dam2.clickneat.views.publicacion.PublicacionView;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by JAVIER on 18/05/2017.
 */

public class PerfilUsuarioView extends BaseActivity implements  PerfilUsuarioContract.View,
                                                                DialogFragmentInterface,
                                                                DialogFragmentImageInterface.SelectImage{

    private PerfilUsuarioPresenter presenter;
    private Usuario usuario;

    private Preferences preferences;
    private int idUsuarioVista;
    private int idPerfilMe;
    private String token;

    //UI Components
    private CircleImageView ivPerfilUsuario;
    private EditText etPerfilUsuario;
    private Button btDomicilio, btPublicacion, btComentario;
    private ProgressDialog progressDialog;
    private FloatingActionButton fabUpdatePerfil;

    private RecyclerView rvDomicilios, rvPublicacion, rvComentarios;
    private DomicilioAdapter domicilioAdapter;
    private ComentarioAdapter comentarioAdapter;
    private PublicacionAdapter publicacionAdapter;

    //Domicilio dialogo
    private DomicilioFragmentDialog domicilioFragmentDialog;
    private ComentarioFragmentDialog comentarioFragmentDialog;

    //Cambios de informacion
    private boolean nameChange;
    private boolean imageChange;

    //Imagenes
    private Uri uriSelectedImage;

    //Permissions
    private boolean isPermissionGranted;
    private DexterBuilder permissionChecker;

    private boolean isMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_perfil_usuario);

        this.presenter = new PerfilUsuarioPresenter(this);
        this.preferences = new Preferences(this);

        //Debemos de obtener el identificador del usuario
        this.idUsuarioVista         = savedInstanceState != null ? savedInstanceState.getInt(getString(R.string.preferences_id_user))
                                                                 : getIntent().getIntExtra(getString(R.string.preferences_id_user), Preferences.DEFAULT_INTEGER);


        this.token                  = this.preferences.getString(getString(R.string.preferences_api_token_user));
        this.idPerfilMe             = savedInstanceState != null ? savedInstanceState.getInt("idPerfilMe")
                                                                 : (token.equals(Preferences.DEFAULT_STRING) ? 0 :
                                                                   (Integer) JwtHelper.getElementFromToken(token, getString(R.string.preferences_id_perfil_user), Integer.class ));

        this.usuario                = savedInstanceState != null ? (Usuario) savedInstanceState.getParcelable("usuario") : null;
        this.nameChange             = savedInstanceState != null ? savedInstanceState.getBoolean("nameChange") : false;
        this.imageChange            = savedInstanceState != null ? savedInstanceState.getBoolean("imageChange") : false;
        this.isPermissionGranted    = savedInstanceState != null ? savedInstanceState.getBoolean("isPermissionGranted") : false;
        this.uriSelectedImage       = savedInstanceState != null ? (Uri) savedInstanceState.getParcelable("uriSelectedImage") : null;

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Solo nos descargamos los datos del usuario cuando no los tenemos
        this.presenter.onLoadPerfilUsuario(this.idUsuarioVista);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("usuario", this.usuario);
        outState.putInt(getString(R.string.preferences_id_user), this.idUsuarioVista);
        outState.putInt("idPerfilMe", this.idPerfilMe);
        outState.putBoolean("nameChange", this.nameChange);
        outState.putBoolean("imageChange", this.imageChange);
        outState.putBoolean("isPermissionGranted", this.isPermissionGranted);
        outState.putParcelable("uriSelectedImage", this.uriSelectedImage);
    }

    @Override
    public void viewPerfilUsuario(Usuario usuario) {

        this.usuario = usuario;
        loadInfoUser();
    }

    @Override
    public void viewError(String error) {

        Snackbar.make(rvComentarios, error, Snackbar.LENGTH_LONG).show();
        if ( this.progressDialog.isShowing() ) this.progressDialog.dismiss();
    }

    @Override
    public void viewDomicilio(Domicilio domicilio) {

        this.domicilioAdapter.addDomicilio(domicilio);
    }

    @Override
    public void viewComentario(Comentario comentario) {
        this.comentarioAdapter.addComentario(comentario);
    }

    @Override
    public void viewUpdatePerfilUsuario(String noerror) {

        nameChange  = nameChange ? !nameChange : nameChange;
        imageChange = imageChange ? !imageChange : imageChange;

        this.preferences.setString(getString(R.string.preferences_api_token_user), noerror);

        this.progressDialog.dismiss();
    }

    private void init() {

        this.ivPerfilUsuario    = (CircleImageView) findViewById(R.id.fotoPerfil);
        this.etPerfilUsuario    = (EditText) findViewById(R.id.nombreUsuario);
        this.fabUpdatePerfil    = (FloatingActionButton) findViewById(R.id.fabUpdatePerfil);

        //Buttons
        this.btDomicilio     = (Button) findViewById(R.id.btnMoreDomicilio);
        this.btPublicacion   = (Button) findViewById(R.id.btnMorePublicacion);
        this.btComentario    = (Button) findViewById(R.id.btnMoreComentario);

        //RecyclewView
        this.rvDomicilios    = (RecyclerView) findViewById(R.id.rv_domicilios_perfil);
        this.rvPublicacion   = (RecyclerView) findViewById(R.id.rv_publicaciones_perfil);
        this.rvComentarios   = (RecyclerView) findViewById(R.id.rv_comentarios_perfil);

        this.domicilioAdapter   = new DomicilioAdapter(this);
        this.publicacionAdapter = new PublicacionAdapter(this);
        this.comentarioAdapter  = new ComentarioAdapter(this);

        this.domicilioAdapter.setHasStableIds(true);
        this.publicacionAdapter.setHasStableIds(true);
        this.comentarioAdapter.setHasStableIds(true);

        this.rvDomicilios.setNestedScrollingEnabled(false);
        this.rvPublicacion.setNestedScrollingEnabled(false);
        this.rvComentarios.setNestedScrollingEnabled(false);


        this.rvDomicilios.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.rvPublicacion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.rvComentarios.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        this.rvDomicilios.setAdapter(this.domicilioAdapter);
        this.rvPublicacion.setAdapter(this.publicacionAdapter);
        this.rvComentarios.setAdapter(this.comentarioAdapter);

        //Progress Dialog
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.action_update_app));
        progressDialog.setCancelable(false);

        //Dialogs
        this.domicilioFragmentDialog  = DomicilioFragmentDialog.newInstance("Domicilio");

        //Permissions
        this.permissionChecker = Dexter.withActivity(this)
                                       .withPermissions(
                                                Manifest.permission.CAMERA,
                                                Manifest.permission.READ_EXTERNAL_STORAGE
                                       ).withListener(new MultiplePermissionsListener() {
                                            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                                                PerfilUsuarioView.this.isPermissionGranted = report.areAllPermissionsGranted();

                                                if ( !report.areAllPermissionsGranted() ) {

                                                    DialogOnAnyDeniedMultiplePermissionsListener.Builder
                                                            .withContext(PerfilUsuarioView.this)
                                                            .withTitle(getString(R.string.title_dialog_permission_images))
                                                            .withMessage(getString(R.string.title_dialog_permission_images))
                                                            .withButtonText(android.R.string.ok)
                                                            .withIcon(R.drawable.ic_menu_camera)
                                                            .build();
                                                }
                                                else {

                                                    PerfilUsuarioView.this.ivPerfilUsuario.callOnClick();
                                                }
                                            }
                                            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                                       });
    }


    private void loadInfoUser() {

        //Visualizamos la informacion
        RequestCreator picasso;

        if ( this.uriSelectedImage == null ) {

            String imagen   = this.usuario.getPerfil().getImagen().isEmpty() ? "-" : this.usuario.getPerfil().getImagen();
            picasso         = Picasso.with(this).load(imagen);
        }
        else {

            picasso = Picasso.with(this).load(this.uriSelectedImage);
        }

        picasso.placeholder(R.drawable.placeholder)
               .error(R.drawable.placeholder)
               .networkPolicy(NetworkPolicy.NO_CACHE)
               .memoryPolicy(MemoryPolicy.NO_CACHE)
               .into(this.ivPerfilUsuario);

        //Si no es nuestro usuario no editaremos el campo

        this.isMe    = Integer.compare(this.usuario.getPerfil().getId(), this.idPerfilMe) == 0;

        this.etPerfilUsuario.setText(this.usuario.getPerfil().getNombre());

        //Actualizamos UI
        this.etPerfilUsuario.setEnabled(isMe);
        this.btDomicilio.setVisibility(isMe ? View.VISIBLE : View.GONE);
        this.btPublicacion.setVisibility(isMe ? View.VISIBLE : View.GONE);
        this.btComentario.setVisibility(!isMe ? View.VISIBLE : View.GONE);
        this.fabUpdatePerfil.setVisibility(isMe ? View.VISIBLE : View.GONE);

        if ( isMe ) {

            this.ivPerfilUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                if ( !PerfilUsuarioView.this.isPermissionGranted ) {

                    PerfilUsuarioView.this.permissionChecker.check();
                }
                else {

                    Intent intent = ImagePicker.getPickImageIntent(PerfilUsuarioView.this);
                    startActivityForResult(intent, ImagePicker.PICK_IMAGE_ID);
                }
                }
            });

            this.etPerfilUsuario.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    nameChange = !s.toString().equals(PerfilUsuarioView.this.usuario.getPerfil().getNombre());
                }
            });

        }
        //Actualizamos los adaptadores
        this.domicilioAdapter.setDomicilios(this.usuario.getPerfil().getDomicilios());
        this.publicacionAdapter.setPublicaciones(this.usuario.getPublicaciones());
        this.comentarioAdapter.setComentarios(this.usuario.getPerfil().getComentariosRecibidos());

        //Agregamos los eventos
        this.btDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                domicilioFragmentDialog.show(getSupportFragmentManager(), "Domicilio");
            }
        });

        this.btPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilUsuarioView.this, PublicacionView.class);
                intent.putExtra(getString(R.string.preferences_id_user), PerfilUsuarioView.this.usuario.getId());
                intent.putExtra("perfil", PerfilUsuarioView.this.usuario.getPerfil());
                intent.putExtra("typeAction", PublicacionView.INSERT_ACTION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
            }
        });

        this.btComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtenemos nuestro usuario de las preferencias compartidas
                String username  = token.equals(Preferences.DEFAULT_STRING) ? "" : (String)JwtHelper.getElementFromToken(token, getString(R.string.preferences_username), String.class );
                String profile   = token.equals(Preferences.DEFAULT_STRING) ? "" : (String)JwtHelper.getElementFromToken(token, getString(R.string.preferences_profile), String.class);

                Usuario usuarioMe = new Usuario();
                usuarioMe.getPerfil().setId(idPerfilMe);
                usuarioMe.getPerfil().setNombre(username);
                usuarioMe.getPerfil().setImagen(profile);

                PerfilUsuarioView.this.comentarioFragmentDialog = ComentarioFragmentDialog.newInstance(usuarioMe, PerfilUsuarioView.this.usuario.getPerfil().getId());
                comentarioFragmentDialog.show(getSupportFragmentManager(), "Comentario");
            }
        });

        this.publicacionAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtenemos la accion si es de edicion o visualizacion
                int position            = PerfilUsuarioView.this.rvPublicacion.getChildAdapterPosition(v);
                int typeAction          = PerfilUsuarioView.this.isMe ? PublicacionView.EDIT_ACTION : PublicacionView.VIEW_ACTION;

                Publicacion publicacion = PerfilUsuarioView.this.publicacionAdapter.getPublicacion(position);

                Intent i = new Intent(PerfilUsuarioView.this, PublicacionView.class);
                i.putExtra("typeAction", typeAction);
                i.putExtra("publicacion", publicacion);
                i.putExtra("perfil", PerfilUsuarioView.this.usuario.getPerfil());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(i);
            }
        });

        this.fabUpdatePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( nameChange || imageChange ) progressDialog.show();

                if ( nameChange ) {

                    //Obtenemos el texto de nuestro editext
                    PerfilUsuarioView.this.usuario.getPerfil().setNombre(PerfilUsuarioView.this.etPerfilUsuario.getText().toString());
                    PerfilUsuarioView.this.presenter.onUpdatePerfilUsuarioElement(PerfilUsuarioView.this.usuario.getPerfil(), "nombre");
                }

                if ( imageChange ) {

                    Bitmap bitmap = ((BitmapDrawable)ivPerfilUsuario.getDrawable()).getBitmap();
                    PerfilUsuarioView.this.usuario.getPerfil().setImagen(ImageHelper.bitmapTobase64String(bitmap));
                    PerfilUsuarioView.this.presenter.onUpdatePerfilUsuarioElement(PerfilUsuarioView.this.usuario.getPerfil(), "imagen");
                }
            }
        });
    }

    @Override
    public void onPositiveButtonClicked(Object response) {

        if ( response instanceof Domicilio ) {

            Domicilio domicilio = (Domicilio)response;
            domicilio.setPerfil(this.usuario.getPerfil().getId());

            this.presenter.onAddDomicilioUsuario(domicilio);
        }
        else if ( response instanceof  Comentario ) {

            this.presenter.onAddComentarioUsuario((Comentario)response);
        }
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case DomicilioFragmentDialog.PICK_IMAGE_FROM_DIALOG_DOMICILIO: {

                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                this.domicilioFragmentDialog.onSelectedImage(bitmap);

                break;
            }

            case ImagePicker.PICK_IMAGE_ID: {

                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);

                if ( bitmap != null ) {

                    //Actualizamos los datos
                    this.uriSelectedImage   = ImagePicker.getUriFromResult(this, resultCode, data);
                    imageChange             = true;
                }

                break;
            }
        }
    }

    @Override
    public void onSelectImage() {

        Intent intent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(intent, DomicilioFragmentDialog.PICK_IMAGE_FROM_DIALOG_DOMICILIO);
    }
}
