package com.dam2.clickneat.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferna on 26/05/2017.
 */

public class BitmapHelper {

    /*
    * Picasso no maneja muy bien las referencias y en algunas ocasiones las pierde y las
    * toma el recolector de basura. Para ello generamos una lista de targets la cual posee
    * una referencia strong para evitar que el recolector de basura la elimine antes de que
    * termine de cargar la imagen
    * */
    private static final List<Target> targets = new ArrayList<>();

    public static byte[] getByteArrayFromBitmap( Bitmap bmp ) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, bytes);

        return bytes.toByteArray();
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap getImageResize(Bitmap bmp , int reqWidth, int reqHeight) {

        byte[] res = getByteArrayFromBitmap(bmp);

        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeByteArray(res, 0, res.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeByteArray(res, 0, res.length, options);
    }

    public static void loadBitmapAsynchronously(Picasso picasso, final ImageView imageView, int placeholder, final String image ) {

        if ( image == null || image.equals("-") || image.trim().isEmpty() ) {

            picasso.load(placeholder)
                   .fit()
                   .centerCrop()
                   .into(imageView);

        }
        else
        {
            try
            {

                final Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                        new AsyncTask<Bitmap, Void, Bitmap>(){

                            @Override
                            protected Bitmap doInBackground(Bitmap... params) {

                                Bitmap bitmap = params[0];

                                return getImageResize(bitmap, bitmap.getWidth() /3, bitmap.getHeight()/ 3);
                            }

                            @Override
                            protected void onPostExecute(Bitmap bitmap) {

                                imageView.setImageBitmap(bitmap);
                                targets.remove(this);
                            }
                        }.execute(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {


                        imageView.setImageDrawable(errorDrawable);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {


                        imageView.setImageDrawable(placeHolderDrawable);

                    }
                };
                targets.add(target);
                picasso.load(image)
                       .into(target);
            }
            catch( Exception e){

                picasso.load(placeholder)
                       .fit()
                       .centerCrop()
                       .into(imageView);


            }
        }
    }

}
