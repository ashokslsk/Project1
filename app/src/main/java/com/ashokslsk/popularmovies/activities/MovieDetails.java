package com.ashokslsk.popularmovies.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ashokslsk.popularmovies.R;
import com.ashokslsk.popularmovies.fragments.MovieDetailFragment;
import com.ashokslsk.popularmovies.model.Movie;
import com.ashokslsk.popularmovies.network.MovieNetworkService;
import com.ashokslsk.popularmovies.util.Utils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetails extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.backdrop)
    ImageView backdrop;
    @Bind(R.id.main_frame)
    FrameLayout mainFrame;
    @Bind(R.id.fab_fav)
    FloatingActionButton fabFavourite;
    @OnClick(R.id.fab_fav)
    public void onFavouriteCLicked()
    {
        Utils.toggleFavourite(getApplicationContext(), movie);
        fabFavourite.setImageResource(Utils.isFavourite(getApplicationContext(),movie)?R.drawable.ic_favorite_white_24dp:R.drawable.ic_favorite_border_white_24dp);
    }
    int actualBackdropViewWidth;
    private static final String ARG_MOVIE = "movie";
    private Movie movie;
    private final static double backdropRatio = 5/3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        movie = bundle.getParcelable(ARG_MOVIE);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, MovieDetailFragment.newInstance(movie, false)).commit();
        loadBackdrop();
        fabFavourite.setImageResource(Utils.isFavourite(getApplicationContext(),movie)?R.drawable.ic_favorite_white_24dp:R.drawable.ic_favorite_border_white_24dp);
    }

    public void loadBackdrop()
    {
        final ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int gridWidth = viewGroup.getWidth();
                actualBackdropViewWidth = gridWidth ;
                Log.e("actualbackgrop width", "" + gridWidth);

                Picasso.with(getApplicationContext()).load(MovieNetworkService.buildBackdropUrl(movie.getBackdropPath(), actualBackdropViewWidth))
                        .error(R.drawable.ic_placeholder)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(backdrop);


            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(ARG_MOVIE,movie);
    }
}
