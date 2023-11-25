package com.eyupyilmaz.tvseriesapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.eyupyilmaz.tvseriesapp.Repositories.TVSeriesDetailsRepository;
import com.eyupyilmaz.tvseriesapp.Responses.TVSeriesDetailsResponse;

public class TVSeriesDetailsViewModel extends ViewModel {

    private TVSeriesDetailsRepository tvSeriesDetailsRepository;

    public TVSeriesDetailsViewModel(){
        tvSeriesDetailsRepository = new TVSeriesDetailsRepository();
    }

    public LiveData<TVSeriesDetailsResponse> getTVSeriesDetails(String tvSeriesId){
        return tvSeriesDetailsRepository.getTVSeriesDetails(tvSeriesId);
    }

}
