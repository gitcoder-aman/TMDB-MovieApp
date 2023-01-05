package com.tech.mymovietvshows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

public class UpcomingNowRVAdapter extends RecyclerView.Adapter<UpcomingNowRVAdapter.viewHolder> {

    Context context;
    List<UpcomingNowMovieResultModel>upcomingMovieResultModelList;

    public UpcomingNowRVAdapter(Context context, List<UpcomingNowMovieResultModel> upcomingMovieResultModelList) {
        this.context = context;
        this.upcomingMovieResultModelList = upcomingMovieResultModelList;
    }

    @NonNull
    @Override
    public UpcomingNowRVAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(context).inflate(R.layout.poster_rv_layout,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingNowRVAdapter.viewHolder holder, int position) {

        UpcomingNowMovieResultModel upcomingMovieResultModel = upcomingMovieResultModelList.get(position);

        String posterPath = upcomingMovieResultModel.getPoster_path();
        Picasso.get()
                .load(posterPath)
                .placeholder(R.drawable.image_loading)
                .into(holder.posterImageView);

        holder.ratingNo.setText(String.valueOf(upcomingMovieResultModel.getVote_average()));
        holder.movieName.setText(upcomingMovieResultModel.getTitle());

        if (upcomingMovieResultModel.getRelease_date() != null) {

            holder.releaseDate.setText(upcomingMovieResultModel.getRelease_date());
            holder.releaseDate.setVisibility(View.VISIBLE);
        } else {
            holder.releaseDate.setVisibility(View.GONE);
        }

        int id = upcomingMovieResultModel.getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("id",String.valueOf(id));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return upcomingMovieResultModelList.size();
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
