package com.dam2.clickneat.views.main;

/**
 * Created by Pablo on 25/04/2017.
 */

public class MainModel implements MainContract.Model {

    private MainContract.Presenter presenter;

    public MainModel(MainContract.Presenter presenter){
        this.presenter = presenter;
    }
}
