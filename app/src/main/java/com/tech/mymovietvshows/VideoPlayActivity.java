package com.tech.mymovietvshows;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;
import com.tech.mymovietvshows.Adapter.ExtraVideosRecyclerAdapter;

import com.tech.mymovietvshows.Model.MovieVideosResults;
import com.tech.mymovietvshows.Utils.FullScreenHelper;

import java.util.ArrayList;

public class VideoPlayActivity extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;
    private RecyclerView otherVideoRecyclerView;
    private ProgressBar progressPlay;
    private LinearLayoutCompat video_player_layout;
    private com.tech.mymovietvshows.Utils.FullScreenHelper fullScreenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Intent intent = getIntent();

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        fullScreenHelper = new FullScreenHelper(this);

        getLifecycle().addObserver(youTubePlayerView);

        AppCompatTextView videoTitle = findViewById(R.id.play_video_title);
        AppCompatTextView noResultFound = findViewById(R.id.no_result_found);
        progressPlay =findViewById(R.id.progressPlay);
        video_player_layout = findViewById(R.id.video_player_layout);

        otherVideoRecyclerView = findViewById(R.id.other_videos_recyclerView);

        otherVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Now get the arrayList and position

        if(intent != null && intent.getExtras() != null){

            ArrayList<MovieVideosResults>movieVideosModelArrayList = intent.getExtras().getParcelableArrayList("video");
            int position = Integer.parseInt(intent.getExtras().getString("position"));

            if(movieVideosModelArrayList != null && !movieVideosModelArrayList.isEmpty()){

                String videoKey = movieVideosModelArrayList.get(position).getKey();
                String title = movieVideosModelArrayList.get(position).getName();

                progressPlay.setVisibility(View.GONE);
                video_player_layout.setVisibility(View.VISIBLE);


                if (videoKey != null) {

                    YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                            super.onReady(youTubePlayer);
                            DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);
                            youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
                        }
                    };

                    // disable web ui
                    IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
                    youTubePlayerView.initialize(listener, options);

                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            super.onReady(youTubePlayer);

                            if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {

                                youTubePlayer.loadVideo(videoKey, 0);
                            } else {
                                youTubePlayer.cueVideo(videoKey, 0);
                            }
                        }
                    });

                    youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                        @Override
                        public void onYouTubePlayerEnterFullScreen() {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            fullScreenHelper.enterFullScreen();
                        }

                        @Override
                        public void onYouTubePlayerExitFullScreen() {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            fullScreenHelper.exitFullScreen();
                        }
                    });


                    //Load other videos in Recycler view

                    ArrayList<MovieVideosResults> movieVideosResultsArrayList1 = new ArrayList<>(movieVideosModelArrayList);

                    movieVideosResultsArrayList1.remove(position);

                    if (movieVideosResultsArrayList1.size() > 0) {

                        noResultFound.setVisibility(View.GONE);

                        ExtraVideosRecyclerAdapter extraVideosRecyclerAdapter = new ExtraVideosRecyclerAdapter(VideoPlayActivity.this, movieVideosResultsArrayList1);
                        otherVideoRecyclerView.setAdapter(extraVideosRecyclerAdapter);
                        otherVideoRecyclerView.setVisibility(View.VISIBLE);

                        //Create a Animation for the loading items
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(VideoPlayActivity.this, R.anim.layout_slide_bottom);
                        otherVideoRecyclerView.setLayoutAnimation(controller);
                        otherVideoRecyclerView.scheduleLayoutAnimation();


                    } else {

                        otherVideoRecyclerView.setVisibility(View.GONE);
                        noResultFound.setVisibility(View.VISIBLE);
                    }
                }
                if(title != null)
                videoTitle.setText(title);

            }
        }
    }
    //exit the full screen on back pressed
    @Override
    public void onBackPressed() {

        if (youTubePlayerView.isFullScreen()) {
            youTubePlayerView.exitFullScreen();
        } else {
            otherVideoRecyclerView.setVisibility(View.GONE);

            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }
}