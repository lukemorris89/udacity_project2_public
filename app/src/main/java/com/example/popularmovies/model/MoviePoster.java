package com.example.popularmovies.model;

//Creates MoviePoster objects used to populate the Main screen grid with movie images. Clicking on
//Clicking on a poster will take the user to the Movie Details screen for that movie using mId to
//generate the appropriate url for the API GET request
public class MoviePoster {
    private int mId;
    private String mPosterPath;

    public MoviePoster(int id, String posterPath) {
        mId = id;
        mPosterPath = posterPath;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public int getMovieId() {
        return mId;
    }
}

