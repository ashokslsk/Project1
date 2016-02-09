package com.ashokslsk.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokslsk.popularmovies.fragments.MovieDetailFragment;
import com.ashokslsk.popularmovies.R;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieDetailFragment())
                    .commit();
        }
    }
}
