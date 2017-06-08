package com.dam2.clickneat.pojos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

import com.dam2.clickneat.R;
import com.dam2.clickneat.preferences.Preferences;
import com.dam2.clickneat.utils.BitmapHelper;
import com.dam2.clickneat.utils.JwtHelper;
import com.dam2.clickneat.utils.StringHelper;
import com.dam2.clickneat.views.publicacion.PublicacionView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

/**
 * Created by Pablo on 07/06/2017.
 */

public class PublicacionMarker implements GoogleMap.OnMarkerClickListener {

    private Context context;
    private GoogleMap googleMap;
    private Publicacion publicacion;
    private Marker marker;
    private boolean isFolded;
    private Bitmap foldedMarker;
    private Bitmap unfoldedMarker;
    private Picasso picasso;
    private int markerColor;

    public PublicacionMarker(Context context, GoogleMap googleMap, Publicacion publicacion) {
        this.context        = context;
        this.picasso        = Picasso.with(context);
        this.googleMap      = googleMap;
        this.publicacion    = publicacion;
        this.isFolded       = true;
        markerColor         = ResourcesCompat.getColor(context.getResources(), R.color.colorAccent, null);
        initMarker();
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Marker getMarker() {
        if (marker == null) {
            initMarker();
        }
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public boolean isFolded() {
        return isFolded;
    }

    public void setFolded(boolean folded) {
        isFolded = folded;
    }

    private void initMarker() {
        LatLng position = new LatLng(
            publicacion.getDomicilio().getUbicacion().getLat(),
            publicacion.getDomicilio().getUbicacion().getLng()
        );

        foldedMarker = createFoldedMarker();
        unfoldedMarker = createUnfoldedMarker();

        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromBitmap(foldedMarker));

        this.marker = googleMap.addMarker(markerOptions);
        googleMap.setOnMarkerClickListener(this);
    }

    private Bitmap createFoldedMarker() {
        int size = 150;
        int padding = 10;

        Bitmap publicacionIcon = BitmapHelper.createBitmap(
                picasso,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.user_marker),
                publicacion.getFoto()
        );
        Bitmap marker = Bitmap.createBitmap(size, size + 30, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(marker);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(markerColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, size, size, paint);

        Rect iconRect = new Rect(padding, padding, size - padding, size - padding);
        canvas.drawBitmap(publicacionIcon, null, iconRect, paint);

        Path path = new Path();
        path.moveTo(0, size);
        path.lineTo(size, size);
        path.lineTo(size / 2, size + 20);
        path.close();
        canvas.drawPath(path, paint);

        return marker;
    }

    private Bitmap createUnfoldedMarker() {
        int size = 150;
        int padding = 10;
        int extraWidth = 300;

        Bitmap publicacionIcon = BitmapHelper.createBitmap(
                picasso,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.user_marker),
                publicacion.getFoto()
        );
        Bitmap marker = Bitmap.createBitmap(size + extraWidth, size + 30, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(marker);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(markerColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, size + extraWidth, size, paint);

        Rect iconRect = new Rect(padding, padding, size - padding, size - padding);
        canvas.drawBitmap(publicacionIcon, null, iconRect, paint);

        Path path = new Path();
        path.moveTo(size, size);
        path.lineTo(size * 2, size);
        path.lineTo((size + extraWidth) / 2, size + 20);
        path.close();
        canvas.drawPath(path, paint);

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(30);

        canvas.drawText(publicacion.getTitulo(), (padding * 2) + (size - padding), padding + 30, textPaint);

        textPaint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(
                "Fecha: " + StringHelper.dateToStringPublicacion(publicacion.getFecha()),
                (padding * 2) + (size - padding),
                (padding * 2) + 60,
                textPaint
        );
        canvas.drawText(
                "Precio: " + publicacion.getPrecio() + " €",
                (padding * 2) + (size - padding),
                (padding * 3) + 90,
                textPaint
        );

        return marker;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (isFolded) {
            isFolded = false;
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(unfoldedMarker));
        } else {

            int typeAction = PublicacionView.VIEW_ACTION;
            Preferences preferences = new Preferences(context);
            String token     = preferences.getString(context.getString(R.string.preferences_api_token_user));

            int idUsuario    = token.equals(Preferences.DEFAULT_STRING) ? 0 : (Integer) JwtHelper.getElementFromToken(token, context.getString(R.string.preferences_id_user), Integer.class );
            int idPerfil     = token.equals(Preferences.DEFAULT_STRING) ? 0 : (Integer)JwtHelper.getElementFromToken(token, context.getString(R.string.preferences_id_perfil_user), Integer.class );
            String profile   = token.equals(Preferences.DEFAULT_STRING) ? "" : (String)JwtHelper.getElementFromToken(token, context.getString(R.string.preferences_profile), String.class);
            String nombre_perfil   = token.equals(Preferences.DEFAULT_STRING) ? "" : (String)JwtHelper.getElementFromToken(token, context.getString(R.string.perfil_nombre), String.class);

            PerfilUsuario perfil = new PerfilUsuario();
            perfil.setId(idPerfil);
            perfil.setUsuario(idUsuario);
            perfil.setImagen(profile);
            perfil.setNombre(nombre_perfil);

            Intent intent = new Intent(context, PublicacionView.class);
            intent.putExtra("typeAction", typeAction);
            intent.putExtra("publicacion", publicacion);
            intent.putExtra("isFromMap", true);
            intent.putExtra("idUsuarioDispositivo", idUsuario);
            context.startActivity(intent);

        }
        return false;
    }
}
