package com.dam2.clickneat.views.chats.chat;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Conversacion;
import com.dam2.clickneat.pojos.ConversacionMetadata;
import com.dam2.clickneat.pojos.Mensaje;
import com.dam2.clickneat.pojos.PerfilUsuario;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.recyclerview.adapters.conversacion.MensajesAdapter;
import com.dam2.clickneat.utils.JsonHelper;
import com.dam2.clickneat.utils.JwtHelper;
import com.dam2.clickneat.utils.StringHelper;
import com.dam2.clickneat.views.BaseActivity;
import com.squareup.picasso.Picasso;

import net.mobindustry.emojilib.EmojiPanel;
import net.mobindustry.emojilib.EmojiParser;


import de.hdodenhof.circleimageview.CircleImageView;

public class ChatView extends BaseActivity implements ChatContract.View {

    private ChatContract.Presenter presenter;
    private ConversacionMetadata metadata;
    private Preferences preferences;

    //ID usuario almacenado en preferencias compartidas
    private int idUsuario;
    //Tendremos la variable que almacena el perfil del otro usuario por si tenemos que crear una conversacion con el
    private PerfilUsuario perfilUsuario;

    private RecyclerView rvMensajes;
    private MensajesAdapter adapterMensajes;

    //Elementos del layout
    private FrameLayout mFrameLayout;
    private EmojiPanel mPanel;
    private EmojiParser mParser;
    private CircleImageView circle;
    private TextView tvUsername;
    private TextView tvLastime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.presenter     = new ChatPresenter(this);
        this.preferences   = new Preferences(this);

        String token       = preferences.getString(getString(R.string.preferences_api_token_user));

        this.metadata      = savedInstanceState == null  ? (ConversacionMetadata) getIntent().getParcelableExtra("metadata")
                                                         : (ConversacionMetadata) savedInstanceState.getParcelable("metadata");
        this.perfilUsuario = savedInstanceState == null  ? (PerfilUsuario) getIntent().getParcelableExtra("perfilUsuario")
                                                         : (PerfilUsuario) savedInstanceState.getParcelable("perfilUsuario");
        this.idUsuario      = savedInstanceState == null ? (getIntent().getIntExtra(getString(R.string.preferences_id_user), Preferences.DEFAULT_INTEGER) != Preferences.DEFAULT_INTEGER
                                                         ?  getIntent().getIntExtra(getString(R.string.preferences_id_user), Preferences.DEFAULT_INTEGER)
                                                         :  token.equals(Preferences.DEFAULT_STRING) ? -1 : (Integer) JwtHelper.getElementFromToken(token, getString(R.string.preferences_id_user), Integer.class ))
                                                         :  savedInstanceState.getInt(getString(R.string.preferences_id_user));
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Si la conversacion existe la actualizamos
        if ( this.metadata != null ) {

            this.presenter.onReadMensajes(metadata);
            this.presenter.onLoadMensajes(metadata.getConversacion());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("metadata", this.metadata);
        outState.putParcelable("perfilUsuario", this.perfilUsuario);
        outState.putInt(getString(R.string.preferences_id_user), this.idUsuario);
    }

    @Override
    public void onBackPressed() {

        if (mPanel.isEmojiAttached()) {

            mPanel.dissmissEmojiPopup();

        } else {

            super.onBackPressed();
        }
    }
    @Override
    protected void onPause() {

        super.onPause();
        mPanel.dissmissEmojiPopup();
    }

    private void init() {

        //Inicializamos nuestro recyclerview
        rvMensajes                  = (RecyclerView) findViewById(R.id.rvChat);
        adapterMensajes             = new MensajesAdapter(idUsuario);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        manager.setStackFromEnd(true);
        rvMensajes.setAdapter(adapterMensajes);
        rvMensajes.setLayoutManager(manager);

        //Inicializamos nuestra cabecera personalizada
        drawCustomActionBar();

        //Inicializamos nuestro panel de emoticonos
        mFrameLayout    = (FrameLayout) findViewById(R.id.root_frame_layout);
        mPanel          = new EmojiPanel(this, mFrameLayout, new EmojiPanel.EmojiClickCallback() {
            @Override
            public void sendClicked(Spannable span) {

                //mTextView.setText(span);

                String input = span.toString();

                if (!TextUtils.isEmpty(input)) {

                    //Generamos el mensaje y lo enviamos
                    Mensaje mensaje = new Mensaje();
                    mensaje.setTextoMensaje(input);
                    mensaje.setUsuarioEnvia(ChatView.this.idUsuario);

                    //Realizamos la peticicion
                    if ( ChatView.this.metadata == null ) {

                        //Generamos la conversacion
                        Conversacion conversacion = new Conversacion();
                        conversacion.setUsuarioInicia(ChatView.this.idUsuario);
                        conversacion.setUsuarioRecibe(ChatView.this.perfilUsuario.getUsuario());
                        conversacion.addMensaje(mensaje);

                        presenter.onCreateConversacion(conversacion);
                    }
                    else {

                        //Solo agregamos el mensaje
                        mensaje.setConversacion(ChatView.this.metadata.getConversacion());
                        ChatView.this.presenter.onSendMensaje(mensaje);
                    }

                    //Insertamos el mensaje en nuestra view
                    ChatView.this.adapterMensajes.addMensaje(mensaje);
                    ChatView.this.rvMensajes.scrollToPosition(ChatView.this.adapterMensajes.getItemCount() - 1);
                }
            }

            @Override
            public void stickerClicked(String path) {
                // mSticker.setImageURI(Uri.parse(path));

            }
        });
        //Set default icons for buttons
        //panel.iconsInit();

        //or if you need custom icons for buttons
        mPanel.iconsInit(R.drawable.ic_send_smile_levels, R.drawable.input_send);

        //initialise panel
        mPanel.init();
    }

    @Override
    public void viewConversacion(Conversacion conversacion) {

        //Cargamos los metadatos
        for ( ConversacionMetadata connMetadata : conversacion.getMetadatas() ) {

            if ( connMetadata.getUsuario() == idUsuario ) {

                this.metadata = connMetadata;
                this.viewConversacionMetadata(connMetadata);
            }
        }

        adapterMensajes.setMensajes(conversacion.getMensajes());
    }

    @Override
    public void viewMensaje(Mensaje mensaje) {

        adapterMensajes.addMensaje(mensaje);
        rvMensajes.scrollToPosition(adapterMensajes.getItemCount() - 1);

        //Debemos de actualizar los metadatos
        this.presenter.onReadMensajes(this.metadata);
    }

    @Override
    public void viewConversacionMetadata(ConversacionMetadata conversacionMetadata) {

        //Comprobamos que cambios existen en los metadatos y actualizamos la interfaz
        Picasso.with(this)
                .load(conversacionMetadata.getFoto())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerInside()
                .into(this.circle);

        tvUsername.setText(conversacionMetadata.getTitulo());
        tvLastime.setText(StringHelper.dateFullToString(conversacionMetadata.getFechaEnvio()));


        //Por ultimo almacenamos los metadatos y los actualizamos
        this.metadata = conversacionMetadata;
        this.metadata.setMensajesNoLeidos(0);
        presenter.onReadMensajes(metadata);
    }

    @Override
    public void viewError(String error) {

        Snackbar.make(rvMensajes, error, Snackbar.LENGTH_LONG).show();
    }

    private void drawCustomActionBar() {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar_layout, null);

        circle         = (CircleImageView) mCustomView.findViewById(R.id.profile_image);
        tvUsername     = (TextView) mCustomView.findViewById(R.id.tvUsername);
        tvLastime      = (TextView) mCustomView.findViewById(R.id.tvLastTime);

        String image, title, subtitle = "";

        if ( this.metadata == null ) {

            image       = this.perfilUsuario.getImagen();
            title       = this.perfilUsuario.getNombre();
        }
        else {

            image    = (this.metadata.getFoto() == null || this.metadata.getFoto().isEmpty()) ? "-" : this.metadata.getFoto();
            title    = this.metadata.getTitulo();
            subtitle = StringHelper.dateFullToString(this.metadata.getFechaEnvio());
        }

        Picasso.with(this)
                .load(image)
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(this.circle);
        tvUsername.setText(title);
        tvLastime.setText(subtitle);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }
}
