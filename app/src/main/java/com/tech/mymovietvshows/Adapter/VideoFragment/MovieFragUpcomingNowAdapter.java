package com.tech.mymovietvshows.Adapter.VideoFragment;

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
import com.tech.mymovietvshows.Model.MovieVideosModel;
import com.tech.mymovietvshows.Model.MovieVideosResults;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieResultModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;
import com.tech.mymovietvshows.R;
import com.tech.mymovietvshows.VideoPlayActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragUpcomingNowAdapter extends RecyclerView.Adapter<MovieFragUpcomingNowAdapter.viewHolder> {
    Context context;
    List<UpcomingNowMovieResultModel> upcomingNowMovieResultModelList;

    public MovieFragUpcomingNowAdapter(Context context, List<UpcomingNowMovieResultModel> upcomingNowMovieResultModelList) {
        this.context = context;
        this.upcomingNowMovieResultModelList = upcomingNowMovieResultModelList;
    }

    @NonNull
    @Override
    public MovieFragUpcomingNowAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_rv_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFragUpcomingNowAdapter.viewHolder holder, int position) {

        UpcomingNowMovieResultModel upcomingNowMovieResultModel = upcomingNowMovieResultModelList.get(position);

        if (upcomingNowMovieResultModel != null) {

            if (upcomingNowMovieResultModel.getTitle() != null) {
                holder.movie_title.setText(upcomingNowMovieResultModel.getTitle());
            }else{
                holder.movie_title.setText(upcomingNowMovieResultModel.getTitle());
            }

            Picasso.get()
                    .load(upcomingNowMovieResultModel.getBackdrop_path())
                    .placeholder(R.drawable.image_loading)
                    .into(holder.youtube_thumbnail);

            int movie_id = upcomingNowMovieResultModel.getId();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RetrofitInstance.getInstance().apiInterface.getMovieVideosById(movie_id, api).enqueue(new Callback<MovieVideosModel>() {
                        @Override
                        public void onResponse(@NonNull Call<MovieVideosModel> call, @NonNull Response<MovieVideosModel> response) {

                            MovieVideosModel movieVideosModel = response.body();
                            if (movieVideosModel != null) {
                                List<MovieVideosResults> movieVideosResultsList = movieVideosModel.getResults();

                                if (movieVideosResultsList != null && !movieVideosResultsList.isEmpty()) {

                                    Intent intent = new Intent(context, VideoPlayActivity.class);
                                    ArrayList<MovieVideosResults> movieVideosResultsArrayList = new ArrayList<>(movieVideosResultsList);
                                    //put the current video position and video list

                                    intent.putExtra("position", String.valueOf(0));  //this 0 position of MovieVideoResult model 
                                    intent.putExtra("video", movieVideosResultsArrayList);
                                    context.startActivity(intent);
                                } else {
                                    Toast.makeText(context, "Movie Videos have not available.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MovieVideosModel> call, @NonNull Throwable t) {
                            Log.d("videoFrag", "On Response fail when getting video through id");
                        }
                    });
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return upcomingNowMovieResultModelList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView youtube_thumbnail;
        private final AppCompatTextView movie_title;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            youtube_thumbnail = itemView.findViewById(R.id.youtube_thumbnail);
            movie_title = itemView.findViewById(R.id.movie_title);
        }
    }
}
