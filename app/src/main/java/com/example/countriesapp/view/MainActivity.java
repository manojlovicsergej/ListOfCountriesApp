package com.example.countriesapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.countriesapp.R;
import com.example.countriesapp.model.CountriesModel;
import com.example.countriesapp.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView countriesList;
    TextView listError;
    ProgressBar loadingView;
    SwipeRefreshLayout refreshLayout;

    private ListViewModel viewModel;
    private CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countriesList = findViewById(R.id.countriesList);
        listError = findViewById(R.id.list_error);
        loadingView = findViewById(R.id.loading_view);
        refreshLayout = findViewById(R.id.swipeRefreshLayout);

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();

        countriesList.setLayoutManager(new LinearLayoutManager(this));
        countriesList.setAdapter(adapter);
        observeViewModel();

    }

    private void observeViewModel(){
        viewModel.countries.observe(this, new Observer<List<CountriesModel>>() {
            @Override
            public void onChanged(List<CountriesModel> countriesModels) {
                if(countriesModels !=null){
                    countriesList.setVisibility(View.VISIBLE);
                    adapter.updateCountries(countriesModels);
                }
            }
        });
        viewModel.countryLoadError.observe(this , isError -> {
            if(isError !=null){
                listError.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });
        viewModel.loading.observe(this,isLoading->{
            if(isLoading != null){
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if(isLoading){
                    listError.setVisibility(View.GONE);
                    countriesList.setVisibility(View.GONE);
                }
            }
        });
    }
}