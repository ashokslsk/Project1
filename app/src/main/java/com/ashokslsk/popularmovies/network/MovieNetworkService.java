package com.ashokslsk.popularmovies.network;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ashokslsk.popularmovies.BuildConfig;
import com.ashokslsk.popularmovies.R;
import com.ashokslsk.popularmovies.helpers.ServiceHelpers;
import com.ashokslsk.popularmovies.model.Trailer;

/**
 * Created by ashok.kumar on 09/02/16.
 */
public class MovieNetworkService {
    private static final Uri POPULAR_IMAGE_BASE_URI = Uri.parse("http://image.tmdb.org/t/p/");

    MovieInterface movieApi = null;

    public MovieNetworkService(Context context)
    {
        movieApi = ServiceHelpers.createService(MovieInterface.class, context);
    }

    public void fetchMovies(String sortOrder, String apiKey)
    {
        movieApi.fetchMovies(sortOrder, apiKey);
    }

    public MovieInterface getMovieApi() {
        return movieApi;
    }

    public static String getUrl(Context context,@NonNull Trailer trailer) {
        if (trailer.getSite().equalsIgnoreCase(context.getResources().getString(R.string.youtube))) {
            return String.format("http://www.youtube.com/watch?v=%1$s", trailer.getKey());
        } else {
            throw new UnsupportedOperationException("Only YouTube is supported!");
        }
    }

    public static String getThumbnailUrl(Context context,@NonNull Trailer trailer) {
        if (trailer.getSite().equalsIgnoreCase(context.getResources().getString(R.string.youtube))) {
            Log.e("thumnail url is", "" + String.format("http://img.youtube.com/vi/%1$s/0.jpg", trailer.getId()));
            return String.format("http://img.youtube.com/vi/%1$s/0.jpg", trailer.getKey());
        } else {
            throw new UnsupportedOperationException("Only YouTube is supported!");
        }
    }


    private interface PopularImageWidth {
        String getWidthString();
        int getMaxWidth();
    }


    public enum PopularPosterWidth implements PopularImageWidth {
        W92(92), W154(154), W185(185), W342(342), W500(500), W780(780), ORIGINAL(Integer.MAX_VALUE);

        public final int maxWidth;
        PopularPosterWidth(int maxWidth) {
            this.maxWidth = maxWidth;
        }
        public int getMaxWidth() {
            return this.maxWidth;
        }
        public String getWidthString() {
            return (this == ORIGINAL) ? "original" : "w" + this.maxWidth;
        }
    }

    public enum PopularBackdropWidth implements PopularImageWidth {
        W300(300), W780(780), W1280(1280), ORIGINAL(Integer.MAX_VALUE);

        public final int maxWidth;
        PopularBackdropWidth(int maxWidth) {
            this.maxWidth = maxWidth;
        }
        public int getMaxWidth() {
            return this.maxWidth;
        }
        public String getWidthString() {
            return (this == ORIGINAL) ? "original" : "w" + this.maxWidth;
        }
    }

    private static PopularPosterWidth computeNextLowestPosterWidth(int posterWidth) {
        for (PopularPosterWidth enumWidth : PopularPosterWidth.values()) {
            if (0.8 * posterWidth <= enumWidth.maxWidth) {
                return enumWidth;
            }
        }
        return PopularPosterWidth.ORIGINAL;
    }

    private static PopularBackdropWidth computeNextLowestBackdropWidth(int backdropWidth) {
        for (PopularBackdropWidth enumWidth : PopularBackdropWidth.values()) {
            if (0.8 * backdropWidth <= enumWidth.maxWidth) {
                return enumWidth;
            }
        }
        return PopularBackdropWidth.ORIGINAL;
    }

    public static String buildPosterUrl(String posterPath, int posterWidth) {
        return buildImageUrl(posterPath, computeNextLowestPosterWidth(posterWidth));
    }

    public static String buildBackdropUrl(String backdropPath, int backdropWidth) {
        return buildImageUrl(backdropPath, computeNextLowestBackdropWidth(backdropWidth));
    }

    private static <T extends PopularImageWidth> String buildImageUrl(String imagePath, T tmdbImageWidth) {
        if (BuildConfig.DEBUG) {
//            Log.v("Picasso", "Loading image of width " + tmdbImageWidth.getMaxWidth() + "px");
        }
        String relativePath = tmdbImageWidth.getWidthString() + "/" + imagePath;
        return Uri.withAppendedPath(POPULAR_IMAGE_BASE_URI, relativePath).toString();
    }
}
