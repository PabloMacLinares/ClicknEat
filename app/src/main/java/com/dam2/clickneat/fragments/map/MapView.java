package com.dam2.clickneat.fragments.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.dam2.clickneat.R;
import com.dam2.clickneat.client.DataReceiver;
import com.dam2.clickneat.client.handlers.PublicacionHandler;
import com.dam2.clickneat.pojos.Publicacion;
import com.dam2.clickneat.pojos.PublicacionMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class MapView extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragmet";

    private static final int DEFAULT_ZOOM = 14;
    private GoogleMap googleMap;
    private com.google.android.gms.maps.MapView map;
    private RadioButton rbRadius1km, rbRadius3km, rbRadius5km;

    private Marker userPositionMarker;
    private List<PublicacionMarker> publicacionesMarkers;
    private PublicacionHandler publicacionHandler;
    private int[] radiusOptions = new int[] {1000, 3000, 5000}; //Kilometros
    private int selectedRadius = radiusOptions[0];

    public MapView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initComponents(view, savedInstanceState);

        publicacionesMarkers = new ArrayList<>();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    private void initComponents(View view, Bundle savedInstanceState) {
        map = (com.google.android.gms.maps.MapView) view.findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        map.getMapAsync(this);

        rbRadius1km = (RadioButton) view.findViewById(R.id.rbRadius1km);
        rbRadius3km = (RadioButton) view.findViewById(R.id.rbRadius3km);
        rbRadius5km = (RadioButton) view.findViewById(R.id.rbRadius5km);

        publicacionHandler = new PublicacionHandler(new DataReceiverPublicacion());

        initEvents();
    }

    private void initEvents() {
        rbRadius1km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadius = radiusOptions[0];
                getPublicaciones();
            }
        });

        rbRadius3km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadius = radiusOptions[1];
                getPublicaciones();
            }
        });

        rbRadius5km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadius = radiusOptions[2];
                getPublicaciones();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setZoomGesturesEnabled(true);
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng fakePosition = new LatLng(37.1863789, -3.6031181);

        addMarker(fakePosition, true);
        getPublicaciones();
        updateCameraPosition(fakePosition);
    }

    private void addMarker(LatLng position, boolean isUserPosition) {
        if (isUserPosition) {
            userPositionMarker = googleMap.addMarker(
                new MarkerOptions().position(position)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_marker))
            );
        } else {
            googleMap.addMarker(
                    new MarkerOptions().position(position)
            );
        }
    }

    private void addPublicacionMarkers(List<Publicacion> publicaciones) {
        for (PublicacionMarker publicacionMarker : publicacionesMarkers) {
            publicacionMarker.getMarker().remove();
        }

        for (Publicacion publicacion : publicaciones) {
            PublicacionMarker publicacionMarker = new PublicacionMarker(this.getContext(), googleMap, publicacion);
            publicacionesMarkers.add(publicacionMarker);
        }
    }

    private void updateCameraPosition(LatLng position) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM));
    }

    private void getPublicaciones() {

        publicacionHandler.getAllElements();
    }

    private class DataReceiverPublicacion implements DataReceiver<Publicacion> {

        @Override
        public void onListReceived(List<Publicacion> list) {
            addPublicacionMarkers(list);
        }

        @Override
        public void onElementReceived(Publicacion list) {

        }

        @Override
        public void onDataItemInsertedReceived(int id) {

        }

        @Override
        public void onDataNoErrorReceived(String noerror) {

        }

        @Override
        public void onDataErrorReceived(String error) {

        }

        @Override
        public void onLoginReceived(String token) {

        }
    }
}
