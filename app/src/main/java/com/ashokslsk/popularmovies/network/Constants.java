package com.ashokslsk.popularmovies.network;

/**
 * Created by ashok.kumar on 03/02/16.
 */
public class Constants {

    // strings required for building the API call
    public static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    public static final String SORT_PARAM = "sort_by";
    public static final String API_PARAM = "api_key";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";


    public static final String TAG_SORT_ON_POPULARITY = "popularity.desc";    // the strings for API call
    public static final String TAG_SORT_ON_RATINGS = "vote_average.desc";
    public static final String TAG_POSTER_PATH = "poster_path";
    public static final String MOVIE_ARRAY_KEY = "movieArray";
    public static final String POSTER_PATHS_KEY = "posterPaths";
}
