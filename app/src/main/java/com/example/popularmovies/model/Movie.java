package com.example.popularmovies.model;

public class Movie {
    private String mTitle;
    private String mReleaseDate;
    private String mOverview;
    private String mPosterPath;
    private double mVoteAverage;
    private double mPopularity;
    private int mId;

    public Movie() {
    }

    public Movie(String title, String releaseDate, String overview, String posterPath, double voteAverage, double popularity, int id) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mOverview = overview;
        mPosterPath = posterPath;
        mVoteAverage = voteAverage;
        mPopularity = popularity;
        mId = id;
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

    public double getPopularity() {
        return mPopularity;
    }

    public int getMovieId() {
        return mId;
    }
}
