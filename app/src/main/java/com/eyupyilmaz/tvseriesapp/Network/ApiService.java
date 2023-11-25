package com.eyupyilmaz.tvseriesapp.Network;

import com.eyupyilmaz.tvseriesapp.Responses.TVSeriesDetailsResponse;
import com.eyupyilmaz.tvseriesapp.Responses.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Call<TVSeriesDetailsResponse> getTVSeriesDetails(@Query("q") String tvSeriesId);
}
