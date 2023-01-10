package com.tech.mymovietvshows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.tech.mymovietvshows.Adapter.ViewPagerCreditDetailsAdapter;
import com.tech.mymovietvshows.Fragment.FragmentCastDetails;

import java.util.Objects;

public class MovieCreditActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private int movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_credit);


        tabLayout = findViewById(R.id.credit_tabLayout);
        viewPager = findViewById(R.id.credit_viewPager);
        toolbar = findViewById(R.id.creditToolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {

            if (intent.getExtras().getString("credit_movieId") != null) {

                movie_id = Integer.parseInt(intent.getExtras().getString("credit_movieId"));
                Log.d("credit", String.valueOf(movie_id));

            }
        }

        viewPager.setAdapter(new ViewPagerCreditDetailsAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }

    public int sendMovieId() {
        return movie_id;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //When execute toolbar back pressed
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_top, R.anim.to_bottom);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_top,R.anim.to_bottom);
    }
}