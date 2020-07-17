package com.example.popularmovies.utils;

import android.util.Log;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MoviePoster;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JsonUtils {
    public static final String LOG_TAG = JsonUtils.class.getSimpleName();

    private JsonUtils() {

    }

    // Create new Movie object from the JSON retrieved from the String stringUrl provided.
    public static Movie parseMovie(String stringUrl) {
        Movie movie = null;
        try {
            // Build the URL to be queried using stringUrl input
            URL url = NetworkUtils.buildUrl(stringUrl);
            // Make a HTTP request to the url's endpoint and parse the data into a JSON object
            JSONObject root = NetworkUtils.makeHttpRequest(url);
            String title = root.optString("original_title");
//            Release Date is returned as format "YYYY-MM-dd" and so this is formatted to a more
//            human-readable format
            String releaseDateRaw = root.optString("release_date");
            String releaseDate = dateFormat(releaseDateRaw);
            String overview = root.optString("overview");
            String posterPath = root.optString("poster_path");
            double voteAverage = root.optDouble("vote_average");

            // Movie object created using retrieved data
            movie = new Movie(title, releaseDate, overview, posterPath, voteAverage);
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error retrieving movies: ", e);
        }
        return movie;
    }

    //Create new MoviePoster objects from the JSON retrieved from the String stringUrl provided.
    public static ArrayList<MoviePoster> parseMoviePosters(String stringUrl) {
        // Build an array list to store all movie posters used to populate Main screen grid
        ArrayList<MoviePoster> searchResultMovies = new ArrayList<>();
        try {
            // Build the URL to be queried using stringUrl input
            URL url = NetworkUtils.buildUrl(stringUrl);
            // Make a HTTP request to the url's endpoint and parse the data into a JSON object
            JSONObject root = NetworkUtils.makeHttpRequest(url);
            JSONArray results = root.optJSONArray("results");
            // Loop through all results and create a MoviePoster object for each
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.optJSONObject(i);
                String posterPath = result.optString("poster_path");
                int id = result.optInt("id");

//                MoviePoster object created using retireved data and added to ArrayList
                MoviePoster moviePoster = new MoviePoster(id, posterPath);
                searchResultMovies.add(moviePoster);
            }
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error retrieving movies: ", e);
        }
        return searchResultMovies;
    }

//    Format Release Date from "YYYY-MM-dd" to "d MMM YYYY"
    private static String dateFormat(String dateString) {
        String formattedDate = "";
        Locale locale = Locale.ENGLISH;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", locale).parse(dateString);
            return new SimpleDateFormat("dd MMM yyyy", locale).format(date);
        }
        catch (ParseException e) {
            Log.e(LOG_TAG, "Unable to parse date: ", e);
        }
        return formattedDate;
    }
}

