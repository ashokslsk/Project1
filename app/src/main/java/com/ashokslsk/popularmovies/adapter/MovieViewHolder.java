package com.ashokslsk.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ashokslsk.popularmovies.R;

/**
 * Created by ashok.kumar on 03/02/16.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {

    public ImageView mMovieThumbnail;


    public MovieViewHolder(View itemView) {
        super(itemView);
        this.mMovieThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
    }
}
