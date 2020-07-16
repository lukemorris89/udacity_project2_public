package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.JsonUtils;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    public static final String ROOT_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?api_key=";
    private static final String ROOT_MOVIE_DETAILS_URL = "https://api.themoviedb.org/3/movie/";
    private final String API_KEY = "c73a85c78fe4c2e9c721a9b4687df567";

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingBar;
    private TextView mErrorTextView;
    private ArrayList<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);

        mLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingBar = (ProgressBar) findViewById(R.id.loading_bar);
        mErrorTextView = (TextView) findViewById(R.id.tv_error_empty);

        if (!NetworkUtils.isOnline(this)) {
            mLoadingBar.setVisibility(View.INVISIBLE);
            mErrorTextView.setText("No internet connection.");
        }
        else {
            loadMovieData();
        }
    }

    @Override
    public void onClick(int id) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        startActivity(intent);

    }

    public class FetchMovieDataTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... string) {
            mMovieList = JsonUtils.parseMovieResults(ROOT_POPULAR_URL + API_KEY);
            return mMovieList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mLoadingBar.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMoviePostersView();
                mMovieAdapter.setMovieData(movies);
            }
            else {
                showErrorMessage();
            }
        }
    }

    private void loadMovieData() {
        showMoviePostersView();
        new FetchMovieDataTask().execute();
    }

    private void showMoviePostersView() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

}
