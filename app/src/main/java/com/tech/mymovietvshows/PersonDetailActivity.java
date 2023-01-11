package com.tech.mymovietvshows;

import static com.tech.mymovietvshows.MainActivity.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Adapter.GetTvShowsCastMovieAdapter;
import com.tech.mymovietvshows.Adapter.getCreditCastMovieAdapter;
import com.tech.mymovietvshows.Adapter.getCreditCrewMovieAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.GetTvShowCastMovieModel;
import com.tech.mymovietvshows.Model.GetTvShowMovieModel;
import com.tech.mymovietvshows.Model.PersonDetailModel;
import com.tech.mymovietvshows.Model.getCastMovieModel;
import com.tech.mymovietvshows.Model.getCreditMovieModel;
import com.tech.mymovietvshows.Model.getCrewMovieModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailActivity extends AppCompatActivity {

    private AppCompatImageView person_imageView;
    private AppCompatTextView person_name;
    private AppCompatTextView known_for;
    private AppCompatTextView birth_place;
    private AppCompatTextView dob;
    private AppCompatTextView person_biography;
    private AppCompatTextView movie_seeAll;
    private LinearLayoutCompat biography_Linearlayout;
    private LinearLayoutCompat creditMovie_linearLayout;
    private LinearLayoutCompat tvShows_linearLayout;

    private RecyclerView moviesRecycler;
    private RecyclerView tvShowsRecycler;

    private Toolbar personToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        person_imageView = findViewById(R.id.person_imageView);
        person_name = findViewById(R.id.person_name);
        known_for = findViewById(R.id.known_for);
        birth_place = findViewById(R.id.birth_place);
        dob = findViewById(R.id.dob);
        person_biography = findViewById(R.id.person_biography);
        personToolbar = findViewById(R.id.personToolbar);
        biography_Linearlayout = findViewById(R.id.biography_Linearlayout);
        creditMovie_linearLayout = findViewById(R.id.creditMovie_linearLayout);
        tvShows_linearLayout = findViewById(R.id.TvShows_linearLayout);
        movie_seeAll = findViewById(R.id.movies_seeAll);

        moviesRecycler = findViewById(R.id.moviesRecycler);
        tvShowsRecycler = findViewById(R.id.tvShowsRecycler);

        moviesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tvShowsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setSupportActionBar(personToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {
            if (intent.getExtras().getString("person_id") != null) {

                int person_id = Integer.parseInt(intent.getExtras().getString("person_id"));
                Log.d("person_id", String.valueOf(person_id));

                RetrofitInstance.getInstance().apiInterface.getPersonDetailById(person_id, api).enqueue(new Callback<PersonDetailModel>() {
                    @Override
                    public void onResponse(@NonNull Call<PersonDetailModel> call, @NonNull Response<PersonDetailModel> response) {

                        PersonDetailModel personDetailModel = response.body();
                        if (personDetailModel != null) {
                            Picasso.get()
                                    .load(personDetailModel.getProfile_path())
                                    .placeholder(R.drawable.ic_person)
                                    .into(person_imageView);

                            person_name.setText(personDetailModel.getName());

                            personToolbar.setTitle(personDetailModel.getName());
                            personToolbar.setTitleTextColor(getResources().getColor(R.color.white));

                            if (personDetailModel.getBiography() != null) {
                                person_biography.setText(personDetailModel.getBiography());
                                biography_Linearlayout.setVisibility(View.VISIBLE);
                            } else {
                                biography_Linearlayout.setVisibility(View.GONE);
                            }
                            birth_place.setText(Html.fromHtml("<b>" + "Birthplace : " + "</b>" + "  " + personDetailModel.getPlace_of_birth()));
                            dob.setText(Html.fromHtml("<b>" + "DOB : " + "</b>" + "  " + personDetailModel.getBirthday()));

                            List<String> also_knownAsList = personDetailModel.getAlso_known_as();
                            if (also_knownAsList != null) {
                                StringBuilder stringBuilder = new StringBuilder();

                                for (int i = 0; i < also_knownAsList.size(); i++) {
                                    if (i == also_knownAsList.size() - 1) {
                                        stringBuilder.append(also_knownAsList.get(i));
                                    } else {
                                        stringBuilder.append(also_knownAsList.get(i)).append(", ");
                                    }
                                }
                                known_for.setText(Html.fromHtml("<b>" + "Known for : " + "</b>" + "  " + stringBuilder));
                            }

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PersonDetailModel> call, @NonNull Throwable t) {
                        Log.d("person", "On Response Fail");
                    }
                });

                //get credit movie detail for cast
                RetrofitInstance.getInstance().apiInterface.getMovieCreditsDetailById(person_id, api).enqueue(new Callback<getCreditMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<getCreditMovieModel> call, @NonNull Response<getCreditMovieModel> response) {

                        getCreditMovieModel getCreditMovieModel = response.body();
                        if (getCreditMovieModel != null) {
                            List<getCastMovieModel> getCastMovieModelList = getCreditMovieModel.getCast();

                            if (getCastMovieModelList != null && !getCastMovieModelList.isEmpty()) {

                                getCreditCastMovieAdapter adapter = new getCreditCastMovieAdapter(PersonDetailActivity.this, getCastMovieModelList);
                                moviesRecycler.setAdapter(adapter);
                                creditMovie_linearLayout.setVisibility(View.VISIBLE);

                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(PersonDetailActivity.this, R.anim.layout_slide_right);
                                moviesRecycler.setLayoutAnimation(controller);
                                moviesRecycler.scheduleLayoutAnimation();

                            } else {
                                creditMovie_linearLayout.setVisibility(View.GONE);
                            }
                        } else {
                            Log.d("person_credit", "credit movie null");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<getCreditMovieModel> call, @NonNull Throwable t) {
                        Log.d("person_credit", "On Response fail");
                    }
                });

                movie_seeAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(PersonDetailActivity.this, GetMovieCastCrewActivity.class);
                        intent1.putExtra("pass_personId", String.valueOf(person_id));
                        Log.d("personId", String.valueOf(person_id));
                        startActivity(intent1);
                    }
                });

                //tvShows movie here
                RetrofitInstance.getInstance().apiInterface.getTvCreditsDetailById(person_id,api).enqueue(new Callback<GetTvShowMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<GetTvShowMovieModel> call, @NonNull Response<GetTvShowMovieModel> response) {

                        GetTvShowMovieModel getTvShowMovieModel = response.body();
                        if(getTvShowMovieModel != null){
                            List<GetTvShowCastMovieModel>getTvShowCastMovieModelList = getTvShowMovieModel.getCast();
                            if(getTvShowCastMovieModelList != null && !getTvShowCastMovieModelList.isEmpty()){

                                GetTvShowsCastMovieAdapter adapter = new GetTvShowsCastMovieAdapter(PersonDetailActivity.this,getTvShowCastMovieModelList);
                                tvShowsRecycler.setAdapter(adapter);

                                tvShows_linearLayout.setVisibility(View.VISIBLE);
                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(PersonDetailActivity.this, R.anim.layout_slide_right);
                                moviesRecycler.setLayoutAnimation(controller);
                                moviesRecycler.scheduleLayoutAnimation();


                            }else{
                                tvShows_linearLayout.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GetTvShowMovieModel> call, @NonNull Throwable t) {
                        Log.d("tv", "On Response fail");
                    }
                });

            }
        }

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