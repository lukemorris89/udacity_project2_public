package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.JsonUtils;
import com.example.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private String apiKey;
    private int movieId;

    private RelativeLayout mMovieDetailsLayout;
    private ImageView mPosterImageView;
    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mVoteAverageTextView;
    private TextView mOverviewTextView;
    private TextView mErrorTextView;
    private ProgressBar mMovieDetailsLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        apiKey = getString(R.string.api_key);
        movieId = getIntent().getIntExtra("id", 0);

        mMovieDetailsLayout = (RelativeLayout) findViewById(R.id.movie_details_layout);
        LinearLayout movieDetailsLinearLayout = (LinearLayout) findViewById(R.id.movie_details);

        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) movieDetailsLinearLayout.getLayoutParams();
        params.height = (int) Math.ceil(screenHeight * 0.3);

        mPosterImageView = (ImageView) findViewById(R.id.movie_detail_image);
        mTitleTextView = (TextView) findViewById(R.id.movie_detail_title);
        mReleaseDateTextView = (TextView) findViewById(R.id.movie_detail_release_date);
        mVoteAverageTextView = (TextView) findViewById(R.id.movie_detail_vote_average);
        mOverviewTextView = (TextView) findViewById(R.id.movie_detail_overview);
        mErrorTextView = (TextView) findViewById(R.id.tv_details_error_empty);
        mMovieDetailsLoadingBar = (ProgressBar) findViewById(R.id.movie_details_loading_bar);

        if (!NetworkUtils.isOnline(this)) {
            mMovieDetailsLoadingBar.setVisibility(View.INVISIBLE);
            mErrorTextView.setText(R.string.no_network_connection);
        }
        else {
            loadMovieData(movieId);
        }
    }

    public class FetchMovieDataTask extends AsyncTask<Integer, Void, Movie> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMovieDetailsLoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie doInBackground(Integer... params) {
            movieId = params[0];
            String ROOT_MOVIE_URL = "https://api.themoviedb.org/3/movie/";
            Uri uri = Uri.parse(ROOT_MOVIE_URL + movieId)
                    .buildUpon()
                    .appendQueryParameter("api_key", apiKey)
                    .build();
            return JsonUtils.parseMovie(uri.toString());
        }

        @Override
        protected void onPostExecute(Movie movie) {
            mMovieDetailsLoadingBar.setVisibility(View.INVISIBLE);
            if (movie != null) {
                setMovieData(movie);
                showMovieDetailsView();
            }
            else {
                showErrorMessage();
            }
        }
    }

    public void loadMovieData(int id) {
        showMovieDetailsView();
        new FetchMovieDataTask().execute(id);
    }

    private void showMovieDetailsView() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mMovieDetailsLayout.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mMovieDetailsLayout.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    public void setMovieData(Movie movie) {
        String ROOT_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
        String posterUrl = ROOT_POSTER_URL + movie.getPosterPath();
        Picasso.get().load(posterUrl).into(mPosterImageView);

        mTitleTextView.setText(movie.getTitle());
        mReleaseDateTextView.setText(movie.getReleaseDate());
        mVoteAverageTextView.setText(String.valueOf(movie.getVoteAverage()));
        mOverviewTextView.setText(movie.getOverview());
    }
}