package com.eyupyilmaz.tvseriesapp.Repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eyupyilmaz.tvseriesapp.Network.ApiClient;
import com.eyupyilmaz.tvseriesapp.Network.ApiService;
import com.eyupyilmaz.tvseriesapp.Responses.TVSeriesDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVSeriesDetailsRepository {

    private ApiService apiService;

    public TVSeriesDetailsRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVSeriesDetailsResponse> getTVSeriesDetails(String tvSeriesId){
        MutableLiveData<TVSeriesDetailsResponse> data = new MutableLiveData<>();
        apiService.getTVSeriesDetails(tvSeriesId).enqueue(new Callback<TVSeriesDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVSeriesDetailsResponse> call,@NonNull Response<TVSeriesDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVSeriesDetailsResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
