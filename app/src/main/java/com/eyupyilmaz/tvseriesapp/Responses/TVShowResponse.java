package com.eyupyilmaz.tvseriesapp.Responses;

import com.eyupyilmaz.tvseriesapp.models.TVSeries;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private List<TVSeries> tvSeries;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<TVSeries> getTvSeries() {
        return tvSeries;
    }
}
