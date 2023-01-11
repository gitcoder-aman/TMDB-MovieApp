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
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.getCastMovieModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getCreditCastMovieAdapter extends RecyclerView.Adapter<getCreditCastMovieAdapter.viewHolder> {

    Context context;
    List<getCastMovieModel>getCastMovieModelList;

    public getCreditCastMovieAdapter(Context context, List<getCastMovieModel> getCastMovieModelList) {
        this.context = context;
        this.getCastMovieModelList = getCastMovieModelList;
    }

    @NonNull
    @Override
    public getCreditCastMovieAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.poster_rv_layout,parent,false );
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull getCreditCastMovieAdapter.viewHolder holder, int position) {

        getCastMovieModel getCastMovieModel = getCastMovieModelList.get(position);

        if (getCastMovieModel != null) {

            Picasso.get()
                    .load(getCastMovieModel.getPoster_path())
                    .placeholder(R.drawable.image_loading)
                    .into(holder.posterImageView);

            holder.ratingNo.setText(String.valueOf(getCastMovieModel.getVote_average()));
            holder.movieName.setText(getCastMovieModel.getOriginal_title());
            holder.releaseDate.setText(getCastMovieModel.getRelease_date());

            int movie_id = getCastMovieModel.getId();

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
        return getCastMovieModelList.size();
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
