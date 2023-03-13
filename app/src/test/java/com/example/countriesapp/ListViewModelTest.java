package com.example.countriesapp;

import android.widget.ListView;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.countriesapp.model.CountriesModel;
import com.example.countriesapp.model.CountriesService;
import com.example.countriesapp.viewmodel.ListViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private Single<List<CountriesModel>> testSingle;

    @Mock
    CountriesService countriesService;

    @InjectMocks
    ListViewModel listViewModel = new ListViewModel();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCountriesSuccess(){
        CountriesModel country = new CountriesModel("countryName","capital","flag");
        ArrayList<CountriesModel> countriesModels = new ArrayList<>();
        testSingle = Single.just(countriesModels);

        Mockito.when(countriesService.getCountries()).thenReturn(testSingle);

        listViewModel.refresh();
        Assert.assertEquals(1,listViewModel.countries.getValue().size());
        Assert.assertEquals(false,listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false,listViewModel.loading.getValue());
    }

    @Before
    public void setupRxSchedulers(){
        Scheduler immediate = new Scheduler(){

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(runnable -> {
                    runnable.run();
                },true);
            }
        };
        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);
    }

}

