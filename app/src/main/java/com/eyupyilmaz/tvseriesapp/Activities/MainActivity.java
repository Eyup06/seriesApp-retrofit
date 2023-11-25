package com.eyupyilmaz.tvseriesapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.eyupyilmaz.tvseriesapp.Adapters.TVSeriesAdapter;
import com.eyupyilmaz.tvseriesapp.Listeners.TVSeriesListener;
import com.eyupyilmaz.tvseriesapp.R;
import com.eyupyilmaz.tvseriesapp.ViewModels.MostPopularTVShowsViewModel;
import com.eyupyilmaz.tvseriesapp.databinding.ActivityMainBinding;
import com.eyupyilmaz.tvseriesapp.models.TVSeries;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVSeriesListener {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVSeries> tvSeries = new ArrayList<>();
    private TVSeriesAdapter tvSeriesAdapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        Initialization();
    }

    private void Initialization(){
        activityMainBinding.tvShowsRv.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvSeriesAdapter = new TVSeriesAdapter(tvSeries, this);
        activityMainBinding.tvShowsRv.setAdapter(tvSeriesAdapter);
        activityMainBinding.tvShowsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvShowsRv.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePage){
                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        getMostPopularTVShows();

    }

    private void getMostPopularTVShows(){
        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this,mostPopularTVShowsResponse ->{
            toggleLoading();
            if (mostPopularTVShowsResponse != null){
                totalAvailablePage = mostPopularTVShowsResponse.getTotalPages();
                if (mostPopularTVShowsResponse.getTvSeries() != null){
                    int oldCount = tvSeries.size();
                    tvSeries.addAll(mostPopularTVShowsResponse.getTvSeries());
                    tvSeriesAdapter.notifyItemRangeInserted(oldCount,tvSeries.size());
                }
            }
        });
    }

    private void toggleLoading(){
        if (currentPage == 1){
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
                activityMainBinding.setIsLoading(false);
            }else {
                activityMainBinding.setIsLoading(true);
            }
        }else{
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            }else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVSeriesClicked(TVSeries tvSeries) {
        Intent intent = new Intent(getApplicationContext(), TVSeriesDetailsActivity.class);
        intent.putExtra("id",tvSeries.getId());
        intent.putExtra("name",tvSeries.getName());
        intent.putExtra("startDate",tvSeries.getStartDate());
        intent.putExtra("country",tvSeries.getCountry());
        intent.putExtra("network",tvSeries.getNetwork());
        intent.putExtra("status",tvSeries.getStatus());
        startActivity(intent);
    }
}