package com.ashokslsk.popularmovies.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokslsk.popularmovies.R;
import com.ashokslsk.popularmovies.network.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ashok.kumar on 03/02/16.
 */
public class MovieDetailFragment extends Fragment {

    public MovieDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_detail_main, container, false);

        Intent oldIntent = getActivity().getIntent();
        if (oldIntent != null && oldIntent.hasExtra(Intent.EXTRA_TEXT)) {
            String movieDetails = oldIntent.getStringExtra(Intent.EXTRA_TEXT);
            try {

                // convert the String extra back into JSON
                JSONObject movieDetailsJSON = new JSONObject(movieDetails);

                // get all the required views

                TextView title_textView = (TextView) rootView.findViewById(R.id.title_textView);
                TextView synopsis_textView = (TextView) rootView.findViewById(R.id.synopsis_textView);
                ImageView poster_imageView = (ImageView) rootView.findViewById(R.id.poster_imageView);
                TextView year_textView = (TextView) rootView.findViewById(R.id.year_textView);
                TextView ratings_textView = (TextView) rootView.findViewById(R.id.ratings_textView);

                // load them up

                title_textView.setText(movieDetailsJSON.getString(Constants.TAG_ORIGINAL_TITLE));
                synopsis_textView.setText(movieDetailsJSON.getString(Constants.TAG_SYNOPSIS));
                year_textView.setText("Release Date: " + movieDetailsJSON.getString(Constants.TAG_RELEASE_DATE));
                ratings_textView.setText("Ratings: " + movieDetailsJSON.getString(Constants.TAG_RATINGS));

                // use Picasso to load up the Image View
                Picasso.with(getContext()).load(Constants.IMAGE_BASE_URL + movieDetailsJSON.getString(Constants.TAG_POSTER_PATH)).into(poster_imageView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return rootView;
    }
}
