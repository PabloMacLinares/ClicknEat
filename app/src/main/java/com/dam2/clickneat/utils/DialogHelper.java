package com.dam2.clickneat.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by ferna on 24/05/2017.
 */

public class DialogHelper {

    public static Dialog getDialog(Context cxt, String title, String message,
                                   Dialog.OnClickListener positiveListener,
                                   Dialog.OnClickListener negativeListener,
                                   Dialog.OnClickListener neutralListener ){

        AlertDialog.Builder builder = new AlertDialog.Builder(cxt);

        builder.setTitle(title).setMessage(message);

        if ( positiveListener != null ) builder.setPositiveButton("OK", positiveListener);
        if ( negativeListener != null ) builder.setNegativeButton("CANCEL", negativeListener);
        if ( neutralListener != null ) builder.setNeutralButton("NADA", neutralListener);


        return builder.create();
    }
}
