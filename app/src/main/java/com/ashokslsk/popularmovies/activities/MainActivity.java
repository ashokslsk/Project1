package com.ashokslsk.popularmovies.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ashokslsk.popularmovies.R;
import com.ashokslsk.popularmovies.adapter.MovieAdapter;
import com.ashokslsk.popularmovies.fragments.MovieDetailFragment;
import com.ashokslsk.popularmovies.fragments.MovieGridFragment;
import com.ashokslsk.popularmovies.model.Movie;
import com.ashokslsk.popularmovies.model.SortCriteria;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickInterface{

    private static final String KEY_SELECTED_MOVIE = "selected_movie";
    private static final String KEY_SORT_ORDER = "sort_order";

    private MovieGridFragment mMoviesFragment = null;

    // only used in two-pane layout
    private MovieDetailFragment mDetailsFragment = null;
    private boolean mTwoPane = false;
    private Movie mSelectedMovie = null;
    private SortCriteria mSelectedSortCriteria = MovieGridFragment.defaultSortCriteria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMoviesFragment = (MovieGridFragment) getSupportFragmentManager().findFragmentById(R.id.movies_fragment);
        if (findViewById(R.id.details_fragment_container) != null) {
            mTwoPane = true;
            if (savedInstanceState != null) {
                mSelectedSortCriteria = (SortCriteria) savedInstanceState.getSerializable(KEY_SORT_ORDER);
                Parcelable movieParcelable = savedInstanceState.getParcelable(KEY_SELECTED_MOVIE);
                Movie selectedMovie = (Movie)movieParcelable;
                showMovieDetails(selectedMovie);
            }
        }
        hideDetailsFragment();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SELECTED_MOVIE, mSelectedMovie);
        outState.putSerializable(KEY_SORT_ORDER, mSelectedSortCriteria);
    }
    public void showMovieDetails(@Nullable Movie movie) {
        if (! mTwoPane) {
            return;
        }
        if (movie == null) {
            return;
        }
        mSelectedMovie = movie;
        showDetailsFragment();
        mDetailsFragment = MovieDetailFragment.newInstance(movie, true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.details_fragment_container, mDetailsFragment,
                        MovieDetailFragment.class.getSimpleName())
                .commit();
//
    }

    @Override
    public void onMovieClick(View movieView, Movie movie,boolean isDefaultSelcetion) {
        String ARG_MOVIE = "movie";
        if (! mTwoPane) {
            if(isDefaultSelcetion)
                return;
            Intent intent = new Intent(this, MovieDetails.class);
            intent.putExtra(ARG_MOVIE,movie);
            if (movieView != null) {
                ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(movieView, 0, 0,
                        movieView.getWidth(), movieView.getHeight());
                startActivity(intent, opts.toBundle());
            } else {
                startActivity(intent);
            }
        } else {
            showMovieDetails(movie);
        }

    }

    public void showDetailsFragment()
    {
        if(findViewById(R.id.details_fragment_container) != null && findViewById(R.id.details_fragment_container).getVisibility()== View.GONE)
            findViewById(R.id.details_fragment_container).setVisibility(View.VISIBLE);
    }
    public void hideDetailsFragment()
    {
        if(findViewById(R.id.details_fragment_container) != null && findViewById(R.id.details_fragment_container).getVisibility()==View.VISIBLE)
            findViewById(R.id.details_fragment_container).setVisibility(View.GONE);
    }

}
