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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Database.DatabaseHelper;
import com.tech.mymovietvshows.Database.MovieTV;
import com.tech.mymovietvshows.Model.GetTvShowCastMovieModel;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.getCastMovieModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTvShowsCastMovieAdapter extends RecyclerView.Adapter<GetTvShowsCastMovieAdapter.viewHolder> {

    Context context;
    List<GetTvShowCastMovieModel> getTvShowCastMovieModelList;

    public GetTvShowsCastMovieAdapter(Context context, List<GetTvShowCastMovieModel> getTvShowCastMovieModelList) {
        this.context = context;
        this.getTvShowCastMovieModelList = getTvShowCastMovieModelList;
    }

    @NonNull
    @Override
    public GetTvShowsCastMovieAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.poster_rv_layout1, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetTvShowsCastMovieAdapter.viewHolder holder, int position) {

        GetTvShowCastMovieModel getTvShowCastMovieModel = getTvShowCastMovieModelList.get(position);

        if (getTvShowCastMovieModel != null) {

            String movieName = getTvShowCastMovieModel.getName();
            String posterImage = getTvShowCastMovieModel.getPoster_path();
            float rating = getTvShowCastMovieModel.getVote_average();
            String releaseDate = getTvShowCastMovieModel.getFirst_air_date();

            Picasso.get()
                    .load(posterImage)
                    .placeholder(R.drawable.image_loading)
                    .into(holder.posterImageView);


            holder.ratingNo.setText(String.valueOf(rating));
            if (movieName != null) {
                holder.movieName.setText(getTvShowCastMovieModel.getName());
            }
            if (releaseDate != null) {
                holder.releaseDate.setText(getTvShowCastMovieModel.getFirst_air_date());
            }

            int tv_id = getTvShowCastMovieModel.getId();

            DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);

            ArrayList<MovieTV> arrayList = (ArrayList<MovieTV>) databaseHelper.movieTVDAO().getAllMovieTV();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RetrofitInstance.getInstance().apiInterface.getMovieDetailsById(tv_id, api).enqueue(new Callback<MovieDetailModel>() {
                        @Override
                        public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {
                            Log.d("debug", "On Response");
                            MovieDetailModel movieDetailModelResponse = response.body();

                            if (movieDetailModelResponse != null && !movieDetailModelResponse.getOverview().equals("")) {
                                Intent intent = new Intent(context, MovieDetailActivity.class);
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

            //favorite button work
            holder.favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.favBtn.getText().toString().equals("Watchlist")) {

                        databaseHelper.movieTVDAO().addTx(new MovieTV(tv_id, posterImage, rating, movieName, releaseDate));

                        holder.favBtn.setText("Watchlisted");
                        holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);

                    } else {
                        //remove data from favorite database
                        databaseHelper.movieTVDAO().deleteTx(new MovieTV(tv_id, posterImage, rating, movieName, releaseDate));

                        holder.favBtn.setText("Watchlist");
                        holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                    }
                }
            });

            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getId() == getTvShowCastMovieModelList.get(position).getId()) {
                    holder.favBtn.setText("Watchlisted");
                    holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
                }
            }
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
        private final AppCompatButton favBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.poster_imageView);
            ratingNo = itemView.findViewById(R.id.rating_no);
            movieName = itemView.findViewById(R.id.movie_name);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            favBtn = itemView.findViewById(R.id.favoriteBtn);
        }
    }
}
