package com.dam2.clickneat.fragments.map;

/**
 * Created by Pablo on 25/04/2017.
 */

public class MapModel implements MapContract.Model {

    private MapContract.Presenter presenter;

    public MapModel(MapContract.Presenter presenter){
        this.presenter = presenter;
    }
}
