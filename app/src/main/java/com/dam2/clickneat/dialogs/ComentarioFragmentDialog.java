package com.dam2.clickneat.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Comentario;
import com.dam2.clickneat.pojos.Usuario;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by ferna on 21/05/2017.
 */

public class ComentarioFragmentDialog extends AppCompatDialogFragment {

    private DialogFragmentInterface listener;
    private Usuario usuario;
    private int idUsuarioPerfil;

    //UI Interface
    private CircleImageView cImage;
    private TextView tvUsername;
    private MaterialRatingBar ratingBar;
    private EditText edValoracion;
    private Button buttonOK, buttonCancel;


    public ComentarioFragmentDialog() {
        // Empty constructor required for DialogFragment
    }

    public static ComentarioFragmentDialog newInstance(Usuario usuario, int idUsuarioYo) {

        ComentarioFragmentDialog frag = new ComentarioFragmentDialog();

        Bundle args = new Bundle();
        args.putParcelable("usuario", usuario);
        args.putInt("idUsuarioPerfil", idUsuarioYo);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.usuario        = getArguments().getParcelable("usuario");
            this.idUsuarioPerfil = getArguments().getInt("idUsuarioPerfil");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v                  = inflater.inflate(R.layout.dialog_comentario, null);


        alertDialogBuilder.setView(v);

        //Cargamos la informacion
        loadUI(v);

        Dialog dialog = alertDialogBuilder.create();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener        = (DialogFragmentInterface) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó DialogFragmentInterface");

        }
    }


    private void loadUI(View v) {

        //Buscamos los elementos de la vista
        this.cImage         = (CircleImageView)v.findViewById(R.id.dialog_comentario_civ);
        this.tvUsername     = (TextView) v.findViewById(R.id.dialog_comentario_tvUsername);
        this.ratingBar      = (MaterialRatingBar) v.findViewById(R.id.dialog_comentario_stars);
        this.edValoracion   = (EditText) v.findViewById(R.id.dialog_comentario_edValoracion);
        this.buttonOK       = (Button) v.findViewById(R.id.button_ok);
        this.buttonCancel   = (Button) v.findViewById(R.id.button_cancel);

        //Cargamos los datos
        Picasso.with(getActivity())
                .load(this.usuario.getPerfil().getImagen())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(cImage);

        tvUsername.setText(this.usuario.getPerfil().getNombre());

        this.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Comentario comentario = new Comentario();
                comentario.setUsuarioDa(ComentarioFragmentDialog.this.usuario.getPerfil().getId());
                comentario.setUsuarioRecibe(ComentarioFragmentDialog.this.idUsuarioPerfil);
                comentario.setValoracion(ComentarioFragmentDialog.this.edValoracion.getText().toString());
                comentario.setPuntuacion(ComentarioFragmentDialog.this.ratingBar.getRating());

                ComentarioFragmentDialog.this.dismiss();
                ComentarioFragmentDialog.this.listener.onPositiveButtonClicked(comentario);
            }
        });

        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComentarioFragmentDialog.this.dismiss();
                ComentarioFragmentDialog.this.listener.onNegativeButtonClicked();
            }
        });
    }

}
