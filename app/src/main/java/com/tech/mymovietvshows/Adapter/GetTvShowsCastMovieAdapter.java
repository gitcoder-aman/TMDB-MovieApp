package com.tech.mymovietvshows.Adapter;

import static com.tech.mymovietvshows.MainActivity.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.GetTvShowCastMovieModel;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.getCastMovieModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTvShowsCastMovieAdapter extends RecyclerView.Adapter<GetTvShowsCastMovieAdapter.viewHolder> {

    Context context;
    List<GetTvShowCastMovieModel>getTvShowCastMovieModelList;

    public GetTvShowsCastMovieAdapter(Context context, List<GetTvShowCastMovieModel> getTvShowCastMovieModelList) {
        this.context = context;
        this.getTvShowCastMovieModelList = getTvShowCastMovieModelList;
    }

    @NonNull
    @Override
    public GetTvShowsCastMovieAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.poster_rv_layout1,parent,false );
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetTvShowsCastMovieAdapter.viewHolder holder, int position) {

        GetTvShowCastMovieModel getTvShowCastMovieModel = getTvShowCastMovieModelList.get(position);

        if (getTvShowCastMovieModel != null) {

            Picasso.get()
                    .load(getTvShowCastMovieModel.getPoster_path())
                    .placeholder(R.drawable.image_loading)
                    .into(holder.posterImageView);

            holder.ratingNo.setText(String.valueOf(getTvShowCastMovieModel.getVote_average()));
            holder.movieName.setText(getTvShowCastMovieModel.getName());
            holder.releaseDate.setText(getTvShowCastMovieModel.getFirst_air_date());

            int movie_id = getTvShowCastMovieModel.getId();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RetrofitInstance.getInstance().apiInterface.getMovieDetailsById(movie_id, api).enqueue(new Callback<MovieDetailModel>() {
                        @Override
                        public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {
                            Log.d("debug", "On Response");
                            MovieDetailModel movieDetailModelResponse = response.body();

                            if (movieDetailModelResponse != null && !movieDetailModelResponse.getOverview().equals("")) {
                                Intent intent = new Intent(context, MovieDetailActivity.class);
                                intent.putExtra("id", String.valueOf(movie_id));
                                context.startActivity(intent);

                            } else {

                                Log.d("debug", "movie Detail NULL");
                                Toast.makeText(context, "No any Detail have this Movie", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MovieDetailModel> call, @NonNull Throwable t) {
                            Log.d("debug", "On Response Fail");
                        }
                    });

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getTvShowCastMovieModelList.size();
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
