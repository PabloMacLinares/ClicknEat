package com.dam2.clickneat.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.dam2.clickneat.R;
import com.dam2.clickneat.pojos.Domicilio;
import com.dam2.clickneat.utils.ImageHelper;

import java.util.ArrayList;

/**
 * Created by ferna on 20/05/2017.
 */

public class DomicilioFragmentDialog extends AppCompatDialogFragment implements DialogFragmentImageInterface.SelectedImage {

    private DialogFragmentInterface listener;
    private DialogFragmentImageInterface.SelectImage listenerImage;
    public final static int PICK_IMAGE_FROM_DIALOG_DOMICILIO = 11;

    //UI Interface

    private ImageView iv;
    private EditText editText;
    private Bitmap imageSelected;

    public DomicilioFragmentDialog() {
        // Empty constructor required for DialogFragment
    }

    public static DomicilioFragmentDialog newInstance(String title) {
        DomicilioFragmentDialog frag = new DomicilioFragmentDialog();

        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v                  = inflater.inflate(R.layout.dialog_domicilio, null);
        alertDialogBuilder.setView(v);

        this.iv         = (ImageView) v.findViewById(R.id.ivDomicilio);
        this.editText   = (EditText) v.findViewById(R.id.etDireccion);

        this.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listenerImage.onSelectImage();
            }
        });

        alertDialogBuilder.setPositiveButton(getString(R.string.dialog_OK),  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success

                Domicilio domicilio         = new Domicilio();

                //Cargamos las imagenes
                ArrayList<String> imagenes  = new ArrayList();
                imagenes.add(ImageHelper.bitmapTobase64String(DomicilioFragmentDialog.this.imageSelected));
                domicilio.setImagenes(imagenes);

                //Cargamos la direccion
                domicilio.setDireccion(DomicilioFragmentDialog.this.editText.getText().toString());
                DomicilioFragmentDialog.this.listener.onPositiveButtonClicked(domicilio);
            }
        });
        alertDialogBuilder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DomicilioFragmentDialog.this.listener.onNegativeButtonClicked();
            }

        });

        return alertDialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener        = (DialogFragmentInterface) context;
            listenerImage   = (DialogFragmentImageInterface.SelectImage) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó DialogFragmentInterface");

        }
    }

    @Override
    public void onSelectedImage(Bitmap image) {

        if ( image != null ) {

            this.iv.setImageBitmap(image);
            this.imageSelected = image;
        }

    }
}
