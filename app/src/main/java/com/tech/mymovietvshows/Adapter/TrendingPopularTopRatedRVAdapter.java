package com.tech.mymovietvshows.Adapter;

import static com.tech.mymovietvshows.MainActivity.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieResultModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrendingPopularTopRatedRVAdapter extends RecyclerView.Adapter<TrendingPopularTopRatedRVAdapter.ViewHolder> {

    Context context;
    List<TrendingPopularTopRatedMovieResultModel> trendingMovieResultModelList;

    public TrendingPopularTopRatedRVAdapter(Context context, List<TrendingPopularTopRatedMovieResultModel> trendingMovieResultModelList) {
        this.context = context;
        this.trendingMovieResultModelList = trendingMovieResultModelList;
    }

    @NonNull
    @Override
    public TrendingPopularTopRatedRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poster_rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingPopularTopRatedRVAdapter.ViewHolder holder, int position) {

        TrendingPopularTopRatedMovieResultModel trendingMovieResultModel = trendingMovieResultModelList.get(position);

        Log.d("debug", trendingMovieResultModel.getPoster_path());

        int id = trendingMovieResultModel.getId();

        Picasso.get()
                .load(trendingMovieResultModel.getPoster_path())
                .placeholder(R.drawable.image_loading)
                .into(holder.posterImageView);

        holder.ratingNo.setText(String.valueOf(trendingMovieResultModel.getVote_average()));

        if (trendingMovieResultModel.getOriginal_title() != null) {

            holder.movieName.setText(trendingMovieResultModel.getOriginal_title());
        } else {
            holder.movieName.setText(trendingMovieResultModel.getName());
        }

        if (trendingMovieResultModel.getRelease_date() != null) {

            holder.releaseDate.setText(trendingMovieResultModel.getRelease_date());
            holder.releaseDate.setVisibility(View.VISIBLE);
        } else if (trendingMovieResultModel.getFirst_air_date() != null) {

            holder.releaseDate.setText(trendingMovieResultModel.getFirst_air_date());
            holder.releaseDate.setVisibility(View.VISIBLE);
        } else {
            holder.releaseDate.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitInstance.getInstance().apiInterface.getMovieDetailsById(id, api).enqueue(new Callback<MovieDetailModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {
                        Log.d("debug", "On Response");
                        MovieDetailModel movieDetailModelResponse = response.body();

                        if (movieDetailModelResponse != null && !movieDetailModelResponse.getOverview().equals("")) {
                            Intent intent = new Intent(context, MovieDetailActivity.class);
                            intent.putExtra("id", String.valueOf(id));
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return trendingMovieResultModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView posterImageView;
        private final AppCompatTextView ratingNo;
        private final AppCompatTextView movieName;
        private final AppCompatTextView releaseDate;

        private final CardView rootLinearLayout;
        public LinearLayout.LayoutParams params;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.poster_imageView);
            ratingNo = itemView.findViewById(R.id.rating_no);
            movieName = itemView.findViewById(R.id.movie_name);
            releaseDate = itemView.findViewById(R.id.releaseDate);

            params = new LinearLayout.LayoutParams(0, 0);
            rootLinearLayout = itemView.findViewById(R.id.cardView);

        }
    }
}
