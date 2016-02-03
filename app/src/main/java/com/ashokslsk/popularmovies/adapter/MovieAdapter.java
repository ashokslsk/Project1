package com.ashokslsk.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashokslsk.popularmovies.MovieDetails;
import com.ashokslsk.popularmovies.R;
import com.ashokslsk.popularmovies.network.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ashok.kumar on 03/02/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>{

    private static final String TAG = "MovieAdapter";

    private Context mContext;
    private ArrayList<String> mThumbNailPath;  // this array will contain the paths to posters
    ArrayList<String> movieArrayStr;


    public MovieAdapter(Context mContext, ArrayList<String> mThumbNailPath, ArrayList<String> movieArrayStr) {
        this.mContext = mContext;
        this.mThumbNailPath = mThumbNailPath;
        this.movieArrayStr = movieArrayStr;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_movie_item, null);

        MovieViewHolder viewHolder = new MovieViewHolder(view);


        return viewHolder;
    }



    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieDetails = movieArrayStr.get(position);    // get the corresponding movie details from the array
                Log.d(TAG, "onClick() " + movieDetails);

                Intent movieDetailsIntent = new Intent(mContext, MovieDetails.class);
                movieDetailsIntent.putExtra(Intent.EXTRA_TEXT, movieDetails);
                mContext.startActivity(movieDetailsIntent);
            }
        });

        //Download image using picasso library
        Picasso.with(mContext).load(Constants.IMAGE_BASE_URL+ mThumbNailPath.get(position))
                .error(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.mMovieThumbnail);
    }

    @Override
    public int getItemCount() {
        return (null != mThumbNailPath ? mThumbNailPath.size() : 0);
    }
}
