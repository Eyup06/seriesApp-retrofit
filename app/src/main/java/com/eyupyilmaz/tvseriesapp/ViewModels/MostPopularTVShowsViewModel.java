package com.eyupyilmaz.tvseriesapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.eyupyilmaz.tvseriesapp.Repositories.MostPopularTVShowsRepository;
import com.eyupyilmaz.tvseriesapp.Responses.TVShowResponse;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowsViewModel(){
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTVShows(int page){
        return mostPopularTVShowsRepository.getMostPopularTVShow(page);
    }
}
