package com.tech.mymovietvshows.Adapter;

import static com.tech.mymovietvshows.MainActivity.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Database.DatabaseHelper;
import com.tech.mymovietvshows.Database.MovieTV;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;
import com.tech.mymovietvshows.TvShowsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteMovieTVAdapter extends RecyclerView.Adapter<FavoriteMovieTVAdapter.viewHolder> {

    Context context;
    List<MovieTV> movieTVList;

    public FavoriteMovieTVAdapter(Context context, List<MovieTV> movieTVList) {
        this.context = context;
        this.movieTVList = movieTVList;
    }

    @NonNull
    @Override
    public FavoriteMovieTVAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poster_rv_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieTVAdapter.viewHolder holder, int position) {

        MovieTV movieTV = movieTVList.get(position);

        String movieName = movieTV.getMovieName();
        String posterImage = movieTV.getPosterImage();
        float rating = movieTV.getRating();
        String releaseDate = movieTV.getReleaseDate();
        String type = movieTV.getType();

        int id = movieTV.getId();

        if (movieName != null) {
            holder.movieName.setText(movieName);
        }
        if (posterImage != null) {
            Picasso.get()
                    .load(posterImage)
                    .placeholder(R.drawable.image_loading)
                    .into(holder.posterImageView);
        }
        holder.ratingNo.setText(String.valueOf(rating));

        if (releaseDate != null) {
            holder.releaseDate.setText(releaseDate);
        }

        if(movieTV.getType().equals("movie")){
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
        }else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RetrofitInstance.getInstance().apiInterface.getTvShowsDetailsById(id, api).enqueue(new Callback<MovieDetailModel>() {
                        @Override
                        public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {
                            Log.d("debug", "On Response");
                            MovieDetailModel movieDetailModelResponse = response.body();

                            if (movieDetailModelResponse != null && !movieDetailModelResponse.getOverview().equals("")) {
                                Intent intent = new Intent(context, TvShowsDetailActivity.class);
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


        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);

        ArrayList<MovieTV> arrayList = (ArrayList<MovieTV>) databaseHelper.movieTVDAO().getAllMovieTV();

        //favorite button work
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.favBtn.getText().toString().equals("Watchlist")) {

                    databaseHelper.movieTVDAO().addTx(new MovieTV(id, posterImage, rating, movieName, releaseDate, type));

                    holder.favBtn.setText("Watchlisted");
                    holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);

                } else {
                    //remove data from favorite database
                    databaseHelper.movieTVDAO().deleteTx(new MovieTV(id, posterImage, rating, movieName, releaseDate, type));

                    holder.favBtn.setText("Watchlist");
                    holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                }
            }
        });

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getId() == movieTVList.get(position).getId()) {
                holder.favBtn.setText("Watchlisted");
                holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return movieTVList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView posterImageView;
        private final AppCompatTextView ratingNo;
        private final AppCompatTextView movieName;
        private final AppCompatTextView releaseDate;

        private final Button favBtn;


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
