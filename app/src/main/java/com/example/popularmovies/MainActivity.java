package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);

        mLayoutManager = new GridLayoutManager(this, GridLayoutManager.DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);

        mLoadingBar = (ProgressBar) findViewById(R.id.loading_bar);
    }

    @Override
    public void onClick(String movieDetails) {
        Context context = this;
        Class destination = MovieDetailsActivity.class;
        Intent intentViewDetails = new Intent(context, destination);
        intentViewDetails.putExtra(Intent.EXTRA_TEXT, movieDetails);
        startActivity(intentViewDetails);
    }
}