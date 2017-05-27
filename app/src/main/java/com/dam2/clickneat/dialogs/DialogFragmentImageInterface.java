package com.dam2.clickneat.dialogs;

import android.graphics.Bitmap;

/**
 * Created by ferna on 20/05/2017.
 */

public interface DialogFragmentImageInterface {

    interface SelectImage {

        void onSelectImage();
    }

    interface SelectedImage {

        void onSelectedImage(Bitmap image);
    }

}
