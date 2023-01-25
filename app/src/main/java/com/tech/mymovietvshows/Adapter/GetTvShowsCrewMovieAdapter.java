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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.GetTvShowCastMovieModel;
import com.tech.mymovietvshows.Model.GetTvShowCrewMovieModel;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;
import com.tech.mymovietvshows.TvShowsDetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTvShowsCrewMovieAdapter extends RecyclerView.Adapter<GetTvShowsCrewMovieAdapter.viewHolder> {

    Context context;
    List<GetTvShowCrewMovieModel>getTvShowsCrewMovieModelList;

    public GetTvShowsCrewMovieAdapter(Context context, List<GetTvShowCrewMovieModel> getTvShowsCrewMovieModelList) {
        this.context = context;
        this.getTvShowsCrewMovieModelList = getTvShowsCrewMovieModelList;
    }

    @NonNull
    @Override
    public GetTvShowsCrewMovieAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.poster_rv_layout1,parent,false );
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetTvShowsCrewMovieAdapter.viewHolder holder, int position) {

        GetTvShowCrewMovieModel getTvShowCrewMovieModel = getTvShowsCrewMovieModelList.get(position);

        if (getTvShowCrewMovieModel != null) {

            Picasso.get()
                    .load(getTvShowCrewMovieModel.getPoster_path())
                    .placeholder(R.drawable.image_loading)
                    .into(holder.posterImageView);

            holder.ratingNo.setText(String.valueOf(getTvShowCrewMovieModel.getVote_average()));
            holder.movieName.setText(getTvShowCrewMovieModel.getName());
            holder.releaseDate.setText(getTvShowCrewMovieModel.getFirst_air_date());

            int tv_id = getTvShowCrewMovieModel.getId();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RetrofitInstance.getInstance().apiInterface.getTvShowsDetailsById(tv_id, api).enqueue(new Callback<MovieDetailModel>() {
                        @Override
                        public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {
                            Log.d("debug", "On Response");
                            MovieDetailModel movieDetailModelResponse = response.body();

                            if (movieDetailModelResponse != null && !movieDetailModelResponse.getOverview().equals("")) {
                                Intent intent = new Intent(context, TvShowsDetailActivity.class);
                                intent.putExtra("id", String.valueOf(tv_id));
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
        return getTvShowsCrewMovieModelList.size();
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
