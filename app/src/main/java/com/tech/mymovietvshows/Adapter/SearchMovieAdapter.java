package com.tech.mymovietvshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Model.MovieResponseResults;
import com.tech.mymovietvshows.R;

import java.util.List;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.viewHolder> {

    Context context;
    List<MovieResponseResults> movieResponseResultsList;

    public SearchMovieAdapter(Context context, List<MovieResponseResults> movieResponseResultsList) {
        this.context = context;
        this.movieResponseResultsList = movieResponseResultsList;
    }

    @NonNull
    @Override
    public SearchMovieAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poster_rv_layout1, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieAdapter.viewHolder holder, int position) {

        MovieResponseResults movieResponseResults = movieResponseResultsList.get(position);

        if (movieResponseResults != null) {
            Picasso.get()
                    .load(movieResponseResults.getPoster_path())
                    .placeholder(R.drawable.image_loading)
                    .into(holder.posterImageView);

            holder.ratingNo.setText(String.valueOf(movieResponseResults.getVote_average()));

            if (movieResponseResults.getTitle() != null) {
                holder.movieName.setText(movieResponseResults.getTitle());
            } else {
                holder.movieName.setText(movieResponseResults.getName());
            }
            if(movieResponseResults.getRelease_date()!=null){
            holder.releaseDate.setText(movieResponseResults.getRelease_date());
            }else{
                holder.releaseDate.setText(movieResponseResults.getFirst_air_date());
            }
        }
    }

    @Override
    public int getItemCount() {
        return movieResponseResultsList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView posterImageView;
        private final AppCompatTextView ratingNo;
        private final AppCompatTextView movieName;
        private final AppCompatTextView releaseDate;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.poster_imageView);
            ratingNo = itemView.findViewById(R.id.rating_no);
            movieName = itemView.findViewById(R.id.movie_name);
            releaseDate = itemView.findViewById(R.id.releaseDate);
        }
    }
}
