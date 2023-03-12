package com.example.countriesapp.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface CountriesApi {
    //Single is a type of observable of rxJava that emits only one value and finishes
    @GET("DevTides/countries/master/countriesV2.json")
    Single<List<CountriesModel>> getCountries();

    //if we don't know what is endpoint we can simply use get with Object
    @GET
    Single<Object> getObject(@Url String urlString);


}
