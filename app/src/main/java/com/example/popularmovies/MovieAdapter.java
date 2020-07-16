package com.example.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.model.MoviePoster;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private final String ROOT_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    private ArrayList<MoviePoster> mMoviePosters;
    private MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(int id);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.poster_view, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        ImageView posterView = (ImageView) holder.itemView.findViewById(R.id.poster_view_main);
        String posterUrl = ROOT_POSTER_URL + mMoviePosters.get(position).getPosterPath();
        Picasso.get().load(posterUrl).into(posterView);
    }

    @Override
    public int getItemCount() {
        if (mMoviePosters == null) {
            return 0;
        }
        return mMoviePosters.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public MovieAdapterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.v("CLICK POS: ", "" + position);

            int movieId = mMoviePosters.get(position).getMovieId();
            Log.v("CLICK ID: ", "" + movieId);
            mClickHandler.onClick(movieId);
        }
    }

    public void setMovieData(ArrayList<MoviePoster> movies) {
        mMoviePosters = movies;
        notifyDataSetChanged();
    }
}
