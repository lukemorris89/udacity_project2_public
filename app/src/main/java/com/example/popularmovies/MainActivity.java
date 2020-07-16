package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MoviePoster;
import com.example.popularmovies.utils.JsonUtils;
import com.example.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private String apiKey;

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingBar;
    private TextView mErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiKey = getString(R.string.api_key);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingBar = (ProgressBar) findViewById(R.id.loading_bar);
        mErrorTextView = (TextView) findViewById(R.id.tv_error_empty);

        if (!NetworkUtils.isOnline(this)) {
            mLoadingBar.setVisibility(View.INVISIBLE);
            mErrorTextView.setText(getText(R.string.no_network_connection));
        }
        else {
            loadMovieData();
        }
    }

    @Override
    public void onClick(int id) {
        Context context = this;
        Intent intentViewDetails = new Intent(context, MovieDetailsActivity.class);
        intentViewDetails.putExtra("id", id);
        startActivity(intentViewDetails);
    }

    public class FetchMoviePostersTask extends AsyncTask<String, Void, ArrayList<MoviePoster>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MoviePoster> doInBackground(String... string) {
            String ROOT_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?api_key=";
            return JsonUtils.parseMoviePosters(ROOT_POPULAR_URL + apiKey);
        }

        @Override
        protected void onPostExecute(ArrayList<MoviePoster> movies) {
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
        new FetchMoviePostersTask().execute();
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
