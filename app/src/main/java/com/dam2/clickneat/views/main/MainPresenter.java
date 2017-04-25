package com.dam2.clickneat.views.main;

/**
 * Created by Pablo on 25/04/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainContract.Model model;

    public MainPresenter(MainContract.View view){
        this.view = view;
        model = new MainModel(this);
    }
}
