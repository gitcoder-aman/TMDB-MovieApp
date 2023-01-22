package com.tech.mymovietvshows.Adapter;

import static com.tech.mymovietvshows.MainActivity.lottieFav;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Database.DatabaseHelper;
import com.tech.mymovietvshows.Database.MovieTV;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.ArrayList;
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

        String movieName = upcomingMovieResultModel.getOriginal_title();
        String posterImage = upcomingMovieResultModel.getPoster_path();
        float rating = upcomingMovieResultModel.getVote_average();
        String releaseDate = upcomingMovieResultModel.getRelease_date();
        int id = upcomingMovieResultModel.getId();

        Picasso.get()
                .load(posterImage)
                .placeholder(R.drawable.image_loading)
                .into(holder.posterImageView);

        holder.ratingNo.setText(String.valueOf(rating));
        holder.movieName.setText(movieName);

        if (releaseDate != null) {

            holder.releaseDate.setText(releaseDate);
            holder.releaseDate.setVisibility(View.VISIBLE);
        } else {
            holder.releaseDate.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("id",String.valueOf(id));
                context.startActivity(intent);

            }
        });

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);

        ArrayList<MovieTV> arrayList = (ArrayList<MovieTV>) databaseHelper.movieTVDAO().getAllMovieTV();

        //favorite button work
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.favBtn.getText().toString().equals("Watchlist")) {

                    databaseHelper.movieTVDAO().addTx(new MovieTV(id, posterImage, rating, movieName, releaseDate));

                    holder.favBtn.setText("Watchlisted");
                    holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
                    lottieFav.playAnimation();


                } else {
                    //remove data from favorite database
                    databaseHelper.movieTVDAO().deleteTx(new MovieTV(id, posterImage, rating, movieName, releaseDate));

                    holder.favBtn.setText("Watchlist");
                    holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                }
            }
        });

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getId() == upcomingMovieResultModelList.get(position).getId()) {
                holder.favBtn.setText("Watchlisted");
                holder.favBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
            }
        }

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
