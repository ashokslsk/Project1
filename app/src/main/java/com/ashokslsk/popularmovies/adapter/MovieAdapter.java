package com.ashokslsk.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashokslsk.popularmovies.R;
import com.ashokslsk.popularmovies.network.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ashok.kumar on 03/02/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>{

    private Context mContext;
    private ArrayList<String> mThumbNailPath;  // this array will contain the paths to posters

    public MovieAdapter(Context mContext, ArrayList<String> mThumbNailPath) {
        this.mContext = mContext;
        this.mThumbNailPath = mThumbNailPath;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_movie_item, null);

        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

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
