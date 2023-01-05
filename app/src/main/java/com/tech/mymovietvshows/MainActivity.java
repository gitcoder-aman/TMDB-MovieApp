package com.tech.mymovietvshows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.tech.mymovietvshows.Adapter.UpcomingNowRVAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Fragment.FavoriteFragment;
import com.tech.mymovietvshows.Fragment.HomeFragment;
import com.tech.mymovietvshows.Fragment.ProfileFragment;
import com.tech.mymovietvshows.Fragment.SearchFragment;
import com.tech.mymovietvshows.Fragment.VideoFragment;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private LottieAnimationView lottieHome;
    private LottieAnimationView lottieSearch;
    private LottieAnimationView lottieVideo;
    private LottieAnimationView lottieFav;
    private LottieAnimationView lottieProfile;

    public static String api = "ac28a3498de90c46b11f31bda02b8b97";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        lottieHome = findViewById(R.id.homeView);
        lottieSearch = findViewById(R.id.searchView);
        lottieVideo = findViewById(R.id.videoView);
        lottieFav = findViewById(R.id.favorateView);
        lottieProfile = findViewById(R.id.profileView);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContainer, new HomeFragment());
        fragmentTransaction.commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    lottieHome.playAnimation();

                    lottieSearch.pauseAnimation();
                    lottieVideo.pauseAnimation();
                    lottieFav.pauseAnimation();
                    lottieProfile.pauseAnimation();
                    loadFragment(new HomeFragment());
                } else if (id == R.id.nav_search) {
                    lottieSearch.playAnimation();

                    lottieHome.pauseAnimation();
                    lottieVideo.pauseAnimation();
                    lottieFav.pauseAnimation();
                    lottieProfile.pauseAnimation();

                    loadFragment(new SearchFragment());
                } else if (id == R.id.nav_videos) {
                    lottieVideo.playAnimation();

                    lottieSearch.pauseAnimation();
                    lottieHome.pauseAnimation();
                    lottieFav.pauseAnimation();
                    lottieProfile.pauseAnimation();

                    loadFragment(new VideoFragment());
                } else if (id == R.id.nav_favorate) {
                    lottieFav.playAnimation();

                    lottieSearch.pauseAnimation();
                    lottieVideo.pauseAnimation();
                    lottieHome.pauseAnimation();
                    lottieProfile.pauseAnimation();

                    loadFragment(new FavoriteFragment());
                } else if(id == R.id.nav_profile){
                    lottieProfile.playAnimation();

                    lottieSearch.pauseAnimation();
                    lottieVideo.pauseAnimation();
                    lottieFav.pauseAnimation();
                    lottieHome.pauseAnimation();

                    loadFragment(new ProfileFragment());
                }

                return true;
            }
        });

    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContainer, fragment);
        fragmentTransaction.commit();
    }
}