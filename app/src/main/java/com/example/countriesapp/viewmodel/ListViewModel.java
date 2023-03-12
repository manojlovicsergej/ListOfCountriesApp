package com.example.countriesapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countriesapp.model.CountriesModel;
import com.example.countriesapp.model.CountriesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {
    //With MutableLiveData you can update value Asynchronously
    //Live data is observable
    public MutableLiveData<List<CountriesModel>> countries = new MutableLiveData<List<CountriesModel>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private CountriesService countriesService = CountriesService.getInstance();

    //Entry point for the view in our ViewModel
    public void refresh(){
        fetchCountries();
    }

    private CompositeDisposable disposable = new CompositeDisposable();

    private void fetchCountries(){
        loading.setValue(true);
        disposable.add(
            countriesService.getCountries()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<List<CountriesModel>>() {
                        @Override
                        public void onSuccess(List<CountriesModel> countriesModels) {
                            countries.setValue(countriesModels);
                            countryLoadError.setValue(false);
                            loading.setValue(false);
                        }
                        @Override
                        public void onError(Throwable e) {
                            countryLoadError.setValue(true);
                            loading.setValue(false);
                            e.printStackTrace();
                        }
                    })
        );
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }
}
