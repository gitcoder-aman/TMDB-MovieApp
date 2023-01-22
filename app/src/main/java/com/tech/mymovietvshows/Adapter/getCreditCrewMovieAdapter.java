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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Database.DatabaseHelper;
import com.tech.mymovietvshows.Database.MovieTV;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.getCastMovieModel;
import com.tech.mymovietvshows.Model.getCrewMovieModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getCreditCrewMovieAdapter extends RecyclerView.Adapter<getCreditCrewMovieAdapter.viewHolder> {

    Context context;
    List<getCrewMovieModel> getCrewMovieModelList;


    public getCreditCrewMovieAdapter(Context context, List<getCrewMovieModel> getCrewMovieModelList) {
        this.context = context;
        this.getCrewMovieModelList = getCrewMovieModelList;
    }

    @NonNull
    @Override
    public getCreditCrewMovieAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.poster_rv_layout1, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull getCreditCrewMovieAdapter.viewHolder holder, int position) {

        getCrewMovieModel getCrewMovieModel = getCrewMovieModelList.get(position);

        if (getCrewMovieModel != null) {

            String posterImage = getCrewMovieModel.getPoster_path();
            String movieName = getCrewMovieModel.getOriginal_title();
            float rating = getCrewMovieModel.getVote_average();
            String releaseDate = getCrewMovieModel.getRelease_date();

            Picasso.get()
                    .load(getCrewMovieModel.getPoster_path())
                    .placeholder(R.drawable.image_loading)
                    .into(holder.posterImageView);

            holder.ratingNo.setText(String.valueOf(rating));

            if (movieName != null) {
                holder.movieName.setText(movieName);
            }

            if (releaseDate != null) {
                holder.releaseDate.setText(getCrewMovieModel.getRelease_date());
            }

            int movie_id = getCrewMovieModel.getId();

            DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);

            ArrayList<MovieTV> arrayList = (ArrayList<MovieTV>) databaseHelper.movieTVDAO().getAllMovieTV();

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

            //favorite button work
            holder.favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.favBtn.getText().toString().equals("Watchlist")) {

                        databaseHelper.movieTVDAO().addTx(new MovieTV(movie_id, posterImage, rating, movieName, releaseDate));

                        holder.favBtn.setText("Watchlisted");
                        holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);

                    } else {
                        //remove data from favorite database
                        databaseHelper.movieTVDAO().deleteTx(new MovieTV(movie_id, posterImage, rating, movieName, releaseDate));

                        holder.favBtn.setText("Watchlist");
                        holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                    }
                }
            });

            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getId() == getCrewMovieModelList.get(position).getId()) {
                    holder.favBtn.setText("Watchlisted");
                    holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return getCrewMovieModelList.size();
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
