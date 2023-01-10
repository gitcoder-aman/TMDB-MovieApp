package com.tech.mymovietvshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Model.MovieVideosResults;
import com.tech.mymovietvshows.R;

import java.util.List;

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.viewHolder> {

    Context context;
    List<MovieVideosResults>movieVideosResultsList;

    public MovieVideoAdapter(Context context, List<MovieVideosResults> movieVideosResultsList) {
        this.context = context;
        this.movieVideosResultsList = movieVideosResultsList;
    }

    @NonNull
    @Override
    public MovieVideoAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(context).inflate(R.layout.video_rv_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVideoAdapter.viewHolder holder, int position) {

        MovieVideosResults movieVideosResults = movieVideosResultsList.get(position);

        if(movieVideosResults != null){
            String key = movieVideosResults.getKey();
            Picasso.get()
                    .load("https://img.youtube.com/vi/"+key+"/0.jpg")
                    .placeholder(R.drawable.image_loading)
                    .into(holder.youtube_thumbnail);

            holder.movie_title.setText(movieVideosResults.getName());
            holder.movie_type.setText(movieVideosResults.getType());
        }
    }

    @Override
    public int getItemCount() {
        return movieVideosResultsList.size();
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
