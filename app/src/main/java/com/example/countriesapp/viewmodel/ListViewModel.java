package com.example.countriesapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countriesapp.model.CountriesModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {
    //With MutableLiveData you can update value Asynchronously
    //Live data is observable
    public MutableLiveData<List<CountriesModel>> countries = new MutableLiveData<List<CountriesModel>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    //Entry point for the view in our ViewModel
    public void refresh(){
        fetchCountries();
    }

    private void fetchCountries(){
        CountriesModel  country1 = new CountriesModel("Albania","Tirana","");
        CountriesModel  country2 = new CountriesModel("Serbia","Belgrade","");
        CountriesModel  country3 = new CountriesModel("England","London","");
        List<CountriesModel> list = new ArrayList<>();
        list.add(country1);
        list.add(country2);
        list.add(country3);
        countries.setValue(list);
        countryLoadError.setValue(false);
        loading.setValue(false);
    }


}
