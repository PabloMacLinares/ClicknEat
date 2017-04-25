package com.dam2.clickneat.fragments.map;

/**
 * Created by Pablo on 25/04/2017.
 */

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View view;
    private MapContract.Model model;

    public MapPresenter(MapContract.View view){
        this.view = view;
        model = new MapModel(this);
    }
}
