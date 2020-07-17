package com.example.popularmovies.model;

// Creates Movie objects which will be displayed on the Movie Details screen
public class Movie {
    private String mTitle;
    private String mReleaseDate;
    private String mOverview;
    private String mPosterPath;
    private double mVoteAverage;

    public Movie(String title, String releaseDate, String overview, String posterPath, double voteAverage) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mOverview = overview;
        mPosterPath = posterPath;
        mVoteAverage = voteAverage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }
}
