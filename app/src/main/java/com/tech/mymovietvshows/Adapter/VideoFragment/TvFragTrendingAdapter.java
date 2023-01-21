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
import com.tech.mymovietvshows.R;
import com.tech.mymovietvshows.VideoPlayActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvFragTrendingAdapter extends RecyclerView.Adapter<TvFragTrendingAdapter.viewHolder> {
    Context context;
    List<TrendingPopularTopRatedMovieResultModel> trendingPopularTopRatedMovieResultModelsList;

    public TvFragTrendingAdapter(Context context, List<TrendingPopularTopRatedMovieResultModel> trendingPopularTopRatedMovieResultModelsList) {
        this.context = context;
        this.trendingPopularTopRatedMovieResultModelsList = trendingPopularTopRatedMovieResultModelsList;
    }

    @NonNull
    @Override
    public TvFragTrendingAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_rv_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvFragTrendingAdapter.viewHolder holder, int position) {

        TrendingPopularTopRatedMovieResultModel trendingMovieVideoModel = trendingPopularTopRatedMovieResultModelsList.get(position);

        if (trendingMovieVideoModel != null) {

            if (trendingMovieVideoModel.getTitle() != null) {
                holder.movie_title.setText(trendingMovieVideoModel.getTitle());
            }else{
                holder.movie_title.setText(trendingMovieVideoModel.getName());
            }
            holder.movie_type.setText(trendingMovieVideoModel.getMedia_type());

            Picasso.get()
                    .load(trendingMovieVideoModel.getBackdrop_path())
                    .placeholder(R.drawable.image_loading)
                    .into(holder.youtube_thumbnail);

            int movie_id = trendingMovieVideoModel.getId();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RetrofitInstance.getInstance().apiInterface.getTvVideosById(movie_id, api).enqueue(new Callback<MovieVideosModel>() {
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
        return trendingPopularTopRatedMovieResultModelsList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView youtube_thumbnail;
        private final AppCompatTextView movie_title;
        private final AppCompatTextView movie_type;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            youtube_thumbnail = itemView.findViewById(R.id.youtube_thumbnail);
            movie_title = itemView.findViewById(R.id.movie_title);
            movie_type = itemView.findViewById(R.id.movie_type);
        }
    }
}
