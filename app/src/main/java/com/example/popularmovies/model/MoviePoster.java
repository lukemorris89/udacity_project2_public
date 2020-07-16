package com.example.popularmovies.model;

public class MoviePoster {
    private int mId;
    private String mPosterPath;

    public MoviePoster() {
    }

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

