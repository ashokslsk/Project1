package com.ashokslsk.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ashokslsk.popularmovies.adapter.MovieAdapter;
import com.ashokslsk.popularmovies.network.Constants;
import com.ashokslsk.popularmovies.network.NetWorkCallHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashok.kumar on 03/02/16.
 */

public class MovieGridFragment extends Fragment {

    private static final String TAG = "MovieGridFragment";
    private JSONArray movieArray = null;

    //Movie String Array
    ArrayList<String> movieArrayStr = null;

    //Thumbnail path
    ArrayList<String> thumbnailPath = null;

    RecyclerView mMovieGrid;
    private GridLayoutManager mGridLayoutManagerVertical;

    public MovieGridFragment() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_fragment, container, false);
        setHasOptionsMenu(true);


        mMovieGrid = (RecyclerView) rootView.findViewById(R.id.movie_grid);
        mGridLayoutManagerVertical =
                new GridLayoutManager(getActivity(),
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL,
                        false);
        mMovieGrid.setLayoutManager(mGridLayoutManagerVertical);

        if (thumbnailPath != null) {
            mMovieGrid.setAdapter(new MovieAdapter(getActivity(), thumbnailPath, movieArrayStr));
        }

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mMovieGrid.setLayoutManager(new GridLayoutManager(getActivity(),
                    2, //The number of Columns in the grid
                    LinearLayoutManager.VERTICAL,
                    false));
        } else {
            mMovieGrid.setLayoutManager(new GridLayoutManager(getActivity(),
                    4, //The number of Columns in the grid
                    LinearLayoutManager.VERTICAL,
                    false));
        }

        return rootView;
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");

        if (savedInstanceState != null) {
            // Saved data
            movieArrayStr = savedInstanceState.getStringArrayList("movieArray");
            thumbnailPath = savedInstanceState.getStringArrayList("posterPaths");
        } else {

            if (isNetworkConnected() == false) {
                // no internet connection
                // generate a toast asking the user to connect to internet first
                Toast.makeText(getActivity(), "Not connected to the Internet", Toast.LENGTH_LONG).show();
            } else {
                new GetMoviesAsyncTask().execute();
            }
        }
    }


    public class GetMoviesAsyncTask extends AsyncTask<Void, Void, JSONArray> {

        private ProgressDialog dialog = new ProgressDialog(getActivity());
        String sortingOrder = null;


        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait..");
            this.dialog.show();
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(Void... params) {

            // find the sorting parameter
            // get user's preferred units setting
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sortingOrderPref = sharedPref.getString(getResources().getString(R.string.sort_order_key),
                    getResources().getString(R.string.sort_order_default));
            if (sortingOrderPref.equals(getResources().getString(R.string.sort_order_rating))) {
                sortingOrder = Constants.TAG_SORT_ON_RATINGS;
            } else {
                sortingOrder = Constants.TAG_SORT_ON_POPULARITY;
            }


            NetWorkCallHelper movieNetWorkRequest = new NetWorkCallHelper();
            // Making a request to url and getting response
            String jsonStr = movieNetWorkRequest.GetJsonResponse(Constants.MOVIE_BASE_URL, sortingOrder);
            Log.d(TAG, "doInBackground: " + jsonStr);


            try {
                getMovieDataFromJSON(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        private JSONArray getMovieDataFromJSON(String movieJSONStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MOVIE_LIST = "results";

            JSONObject movieJSON = new JSONObject(movieJSONStr);
            // movieJSON object has some extra details
            // but we need just the details of movies, so store them separately:
            movieArray = movieJSON.getJSONArray(MOVIE_LIST);
            return movieArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (movieArray == null)
                return; // nothing to do without any data
            // continue processing the array with movie details
            movieArrayStr = new ArrayList<String>();
            thumbnailPath = new ArrayList<String>();

            // now we need to send the poster paths to the ImageAdapter
            // for it to load images on to the GridView
            for (int i = 0; i < movieArray.length(); i++) {
                try {
                    JSONObject currentJSONObject = movieArray.getJSONObject(i);
                    thumbnailPath.add(currentJSONObject.getString(Constants.TAG_POSTER_PATH));
                    movieArrayStr.add(currentJSONObject.toString());
                } catch (JSONException e) {
                    return;
                }
            }
            if (thumbnailPath != null) {
                mMovieGrid.setAdapter(new MovieAdapter(getActivity(), thumbnailPath, movieArrayStr));
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        // save the two main required arrays
        outState.putStringArrayList(Constants.MOVIE_ARRAY_KEY, movieArrayStr);
        outState.putStringArrayList(Constants.POSTER_PATHS_KEY, thumbnailPath);
        Log.d(TAG, "onSaveInstanceState() called with: " + "outState = [" + outState.toString() + "]");

        // always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

}
