package com.ashokslsk.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ashokslsk.popularmovies.R;
import com.ashokslsk.popularmovies.model.Movie;
import com.ashokslsk.popularmovies.network.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ashok.kumar on 03/02/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    Context context = null;
    List<Movie> movies = null;
    int actualPosterViewWidth =0;
    private MovieClickInterface movieClickInterface;
    private static final double TMDB_POSTER_SIZE_RATIO = 2/3;

    public MovieAdapter(Context context, List<Movie> movies, int actualPosterViewWidth, MovieClickInterface movieClickInterface)
    {
        this.context = context;
        this.movies = movies;
        this.actualPosterViewWidth = actualPosterViewWidth;
        this.movieClickInterface = movieClickInterface;
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_movie_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        //Download image using picasso library
        Picasso.with(context).load(Constants.IMAGE_BASE_URL+ movies.get(position).getPosterPath())
                .error(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.mMovieThumbnail);

        holder.mMovieThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieClickInterface.onMovieClick(holder.itemView,movies.get(position),false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView mMovieThumbnail;

        public MovieViewHolder(View itemView)
        {
            super(itemView);
            mMovieThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);

        }

    }

    public interface MovieClickInterface
    {
        void onMovieClick(View itemView,Movie movie,boolean isDefaultSelection);
    }


}
