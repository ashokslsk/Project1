package com.ashokslsk.popularmovies.network;

import android.content.Context;

import com.ashokslsk.popularmovies.helpers.ServiceHelpers;

/**
 * Created by ashok.kumar on 09/02/16.
 */
public class MovieNetworkRequest {

    MovieInterface movieApi = null;

    public MovieNetworkRequest(Context context)
    {
        movieApi = ServiceHelpers.createService(MovieInterface.class, context);
    }

    public void fetchMovies(String sortOrder, String apiKey)
    {
        movieApi.fetchMovies(sortOrder, apiKey);
    }

    public MovieInterface getMovieApi() {
        return movieApi;
    }

}
