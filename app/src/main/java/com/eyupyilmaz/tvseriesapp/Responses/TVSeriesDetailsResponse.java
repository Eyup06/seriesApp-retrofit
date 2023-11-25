package com.eyupyilmaz.tvseriesapp.Responses;

import com.eyupyilmaz.tvseriesapp.models.TVSeriesDetails;
import com.google.gson.annotations.SerializedName;

public class TVSeriesDetailsResponse {

    @SerializedName("tvShow")
    private TVSeriesDetails tvSeriesDetails;

    public TVSeriesDetails getTvSeriesDetails() {
        return tvSeriesDetails;
    }
}
