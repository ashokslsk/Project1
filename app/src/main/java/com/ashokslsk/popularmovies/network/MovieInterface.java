package com.ashokslsk.popularmovies.network;

import com.ashokslsk.popularmovies.model.MoviesResponse;
import com.ashokslsk.popularmovies.model.ReviewsResponse;
import com.ashokslsk.popularmovies.model.TrailersResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by ashok.kumar on 09/02/16.
 */
public interface MovieInterface {

    @GET("/3/discover/movie")
    Observable<MoviesResponse> fetchMovies(@Query(Constants.SORT_PARAM) String sortOrder, @Query(Constants.API_PARAM) String apiKey);

    @GET("/3/discover/movie")
    Observable<MoviesResponse> fetchMovies(@Query(Constants.SORT_PARAM) String sortOrder, @Query(Constants.API_PARAM) String apiKey,@Query("page") int page);

    @GET("/3/movie/{id}/videos")
    Observable<TrailersResponse> fetchTrailers(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("/3/movie/{id}/reviews")
    Observable<ReviewsResponse> fetchReviews(@Path("id") int id, @Query("api_key") String apiKey);
}
