package com.tech.mymovietvshows.Adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.tech.mymovietvshows.VideoPlayActivity;

import java.util.ArrayList;
import java.util.List;

public class ExtraVideosRecyclerAdapter extends RecyclerView.Adapter<ExtraVideosRecyclerAdapter.ViewHolder> {

    Activity activity;
    List<MovieVideosResults> movieVideosResultsList;

    public ExtraVideosRecyclerAdapter(Activity activity, List<MovieVideosResults> movieVideosResultsList) {
        this.activity = activity;
        this.movieVideosResultsList = movieVideosResultsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(activity).inflate(R.layout.video_layout, parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MovieVideosResults movieVideosResults = movieVideosResultsList.get(position);

        String key = movieVideosResults.getKey();
        Picasso.get()
                .load("https://img.youtube.com/vi/"+key+"/0.jpg")
                .placeholder(R.drawable.image_loading)
                .into(holder.youTubeThumbnail);


        if(movieVideosResults.getName() != null)
            holder.videoTitle.setText(movieVideosResults.getName());

        if(movieVideosResults.getSite() != null)
            holder.videoSite.setText(movieVideosResults.getSite());

        if(movieVideosResults.getType() != null)
            holder.videoType.setText(movieVideosResults.getType());

        if(movieVideosResults.getSize() != null)
            holder.videoQuality.setText(String.valueOf(movieVideosResults.getSize()));

        //on itemView Click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, VideoPlayActivity.class);
                ArrayList<MovieVideosResults> movieVideosResultsArrayList = new ArrayList<>(movieVideosResultsList);

                //set Animation open the video
//                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.youTubeThumbnail,
//                        ViewCompat.getTransitionName(holder.youTubeThumbnail));

                //put the current video position and video list

                intent.putExtra("position", String.valueOf(holder.getAdapterPosition()));
                intent.putExtra("video", movieVideosResultsArrayList);
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieVideosResultsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final AppCompatTextView videoTitle;
        private final AppCompatImageView youTubeThumbnail;
        private final AppCompatTextView videoSite;
        private final AppCompatTextView videoQuality;
        private final AppCompatTextView videoType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            youTubeThumbnail = itemView.findViewById(R.id.youtube_thumbnail);
            videoTitle = itemView.findViewById(R.id.video_title);
            videoSite = itemView.findViewById(R.id.video_site);
            videoQuality = itemView.findViewById(R.id.video_quality);
            videoType = itemView.findViewById(R.id.video_type);

        }
    }
}
