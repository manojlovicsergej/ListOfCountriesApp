package com.example.countriesapp.model;

import com.example.countriesapp.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CountriesService {
    private static CountriesService instance;

    @Inject
    CountriesApi api;

    private CountriesService(){
        DaggerApiComponent.create().inject(this);
    }

    public static CountriesService getInstance(){
        if(instance == null)
            instance = new CountriesService();
        return instance;
    }

    public Single<List<CountriesModel>> getCountries(){
        return api.getCountries();
    }
}
