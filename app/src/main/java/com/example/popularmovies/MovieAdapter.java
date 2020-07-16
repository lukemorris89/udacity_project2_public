package com.example.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private final String ROOT_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    private ArrayList<Movie> mMovieData;
    private MovieAdapterOnClickHandler mClickHandler;
    private ImageView mPosterView;
    private Context mContext;

    public interface MovieAdapterOnClickHandler {
        void onClick(int i);
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
        mPosterView = (ImageView) holder.itemView.findViewById(R.id.poster_view_main);
        mPosterView.setImageResource(R.drawable.ic_launcher_background);
        String posterUrl = ROOT_POSTER_URL + mMovieData.get(position).getPosterPath();
        Picasso.get().load(posterUrl).into(mPosterView);

    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public final ImageView mPosterView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            int id = mMovieData.get(position).getMovieId();
            mClickHandler.onClick(id);
        }
    }







    public void setMovieData(ArrayList<Movie> movies) {
        mMovieData = movies;
        notifyDataSetChanged();
    }
}
