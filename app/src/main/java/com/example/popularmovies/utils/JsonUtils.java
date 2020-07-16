package com.example.popularmovies.utils;

import android.util.Log;

import com.example.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class JsonUtils {
    public static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static ArrayList<Movie> parseMovieResults(String stringUrl) {
        ArrayList<Movie> searchResultMovies = new ArrayList<>();
        try {
            URL url = NetworkUtils.buildUrl(stringUrl);
            JSONObject root = NetworkUtils.makeHttpRequest(url);
            Log.v(LOG_TAG, root.toString());
            JSONArray results = root.optJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String title = result.optString("original_title");
                String releaseDate = result.optString("release_date");
                String overview = result.optString("overview");
                String posterPath = result.optString("poster_path");
                double voteAverage = result.optDouble("vote_average");
                double popularity = result.optDouble("popularity");
                int id = result.optInt("id");

                Movie movie = new Movie(title, releaseDate, overview, posterPath, voteAverage, popularity, id);
                searchResultMovies.add(movie);
            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error retrieving movies: ", e);
        }
        return searchResultMovies;
    }
}

