package com.tech.mymovietvshows.Adapter;

import static com.tech.mymovietvshows.MainActivity.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.MovieDetailsGenres;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;
import com.tech.mymovietvshows.MovieDetailActivity;
import com.tech.mymovietvshows.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionIncludeRVAdapter extends RecyclerView.Adapter<CollectionIncludeRVAdapter.viewHolder> {

    Context context;
    List<UpcomingNowMovieResultModel>collectionPartList;

    public CollectionIncludeRVAdapter(Context context, List<UpcomingNowMovieResultModel> collectionPartList) {
        this.context = context;
        this.collectionPartList = collectionPartList;
    }

    @NonNull
    @Override
    public CollectionIncludeRVAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.collection_include_layout, parent,false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CollectionIncludeRVAdapter.viewHolder holder, int position) {

        UpcomingNowMovieResultModel collectionPartModel = collectionPartList.get(position);

        if (collectionPartModel.getBackdrop_path() != null && collectionPartModel.getPoster_path() != null) {

            String name = collectionPartModel.getOriginal_title();
            int voteCount = collectionPartModel.getVote_count();
            String poster = collectionPartModel.getPoster_path();


            if (name != null) {
                holder.includeName.setText(name);
                holder.includeName.setVisibility(View.VISIBLE);
            } else {
                holder.includeName.setVisibility(View.GONE);
            }
            if (voteCount > 0) {
                holder.includeVoteCount.setText("(" + voteCount + ")");
            } else {
                holder.includeVoteCount.setText("(" + voteCount + ")");
                holder.includeVoteCount.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.rating_blank, 0, 0, 0);
            }
            if (poster != null) {
                Picasso.get()
                        .load(collectionPartModel.getPoster_path())
                        .into(holder.includesPoster);
                holder.includesPoster.setVisibility(View.VISIBLE);
            } else {
                holder.includesPoster.setVisibility(View.GONE);
            }
        }else {
            holder.rootLinearLayout.setLayoutParams(holder.params);  //skip the layout item
        }

        RetrofitInstance.getInstance().apiInterface.getMovieDetailsById(collectionPartModel.getId(),api).enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {

                MovieDetailModel movieDetailModel = response.body();
                if(movieDetailModel != null){
                    List<MovieDetailsGenres> genresList = movieDetailModel.getGenres();

                    if(genresList != null && genresList.size() > 0){
                        String genresString = callGenresListShow(genresList);
                        holder.includeGenres.setText(genresString);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailModel> call, @NonNull Throwable t) {
                Log.d("genres", "Response fail");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = collectionPartModel.getId();
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("id", String.valueOf(id));
                context.startActivity(intent);
            }
        });
    }

    private String callGenresListShow(List<MovieDetailsGenres> genresList) {

            StringBuilder stringBuilder = new StringBuilder();
        if (genresList != null && genresList.size() > 0) {

            for (int i = 0; i < genresList.size(); i++) {

                if (i == genresList.size() - 1) {
                    stringBuilder.append(genresList.get(i).getName());
                } else {
                    stringBuilder.append(genresList.get(i).getName()).append(", ");
                }
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public int getItemCount() {
        return collectionPartList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView includeName;
        private final AppCompatTextView includeGenres;
        private final AppCompatTextView includeVoteCount;
        private final AppCompatImageView includesPoster;
        private final LinearLayoutCompat rootLinearLayout;
        public LinearLayout.LayoutParams params;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            includeGenres = itemView.findViewById(R.id.includeGenres);
            includeName = itemView.findViewById(R.id.includeName);
            includeVoteCount = itemView.findViewById(R.id.includeVoteCount);
            includesPoster = itemView.findViewById(R.id.includesPoster);

            params = new LinearLayout.LayoutParams(0, 0);
            rootLinearLayout = itemView.findViewById(R.id.rootLinearLayout);
        }
    }
}
