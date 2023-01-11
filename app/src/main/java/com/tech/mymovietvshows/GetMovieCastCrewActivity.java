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
import com.tech.mymovietvshows.Adapter.ViewPagerCastCrewMovieAdapter;
import com.tech.mymovietvshows.Adapter.ViewPagerCreditDetailsAdapter;

import java.util.Objects;

public class GetMovieCastCrewActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private int personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_movie_cast_crew);

        tabLayout = findViewById(R.id.credit_tabLayout);
        viewPager = findViewById(R.id.credit_viewPager);
        toolbar = findViewById(R.id.creditToolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {

            if (intent.getExtras().getString("pass_personId") != null) {

                personId = Integer.parseInt(intent.getExtras().getString("pass_personId"));
                Log.d("personId", String.valueOf(personId));

            }
        }

        viewPager.setAdapter(new ViewPagerCastCrewMovieAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }

    public int sendPersonId() {
        return personId ;
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
        overridePendingTransition(R.anim.slide_from_top, R.anim.to_bottom);
    }

}