package com.tech.mymovietvshows.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.mymovietvshows.Model.MovieDetailsGenres;
import com.tech.mymovietvshows.R;

import java.util.List;

public class GenresRVAdapter extends RecyclerView.Adapter<GenresRVAdapter.ViewHolder> {

    List<MovieDetailsGenres>genres;
    Context context;

    public GenresRVAdapter(List<MovieDetailsGenres> genres, Context context) {
        this.genres = genres;
        this.context = context;
    }

    @NonNull
    @Override
    public GenresRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.genres_rv_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresRVAdapter.ViewHolder holder, int position) {

        MovieDetailsGenres movieDetailsGenres = genres.get(position);

        if(movieDetailsGenres.getName() != null){
            holder.genresText.setText(movieDetailsGenres.getName());
        }
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView genresText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            genresText = itemView.findViewById(R.id.genresText);
        }
    }
}
