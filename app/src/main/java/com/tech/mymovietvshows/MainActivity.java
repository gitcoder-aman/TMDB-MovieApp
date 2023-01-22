package com.tech.mymovietvshows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.tech.mymovietvshows.Fragment.FavoriteFragment;
import com.tech.mymovietvshows.Fragment.HomeFragment;
import com.tech.mymovietvshows.Fragment.ProfileFragment;
import com.tech.mymovietvshows.Fragment.SearchFragment;
import com.tech.mymovietvshows.Fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;
    private LottieAnimationView lottieHome;
    private LottieAnimationView lottieSearch;
    private LottieAnimationView lottieVideo;
    public static LottieAnimationView lottieFav;
    private LottieAnimationView lottieProfile;


    public static String api = "ac28a3498de90c46b11f31bda02b8b97";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());  //Lock font size of system setting
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

    public void adjustFontScale(Configuration configuration)
    {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }


    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContainer, fragment);
        fragmentTransaction.commit();
    }
}