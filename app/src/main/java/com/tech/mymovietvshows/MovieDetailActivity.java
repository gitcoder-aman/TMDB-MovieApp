package com.tech.mymovietvshows;

import static com.tech.mymovietvshows.MainActivity.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Adapter.GenresRVAdapter;
import com.tech.mymovietvshows.Adapter.MovieCastRVAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieCollectionModel;
import com.tech.mymovietvshows.Model.MovieCreditsCastModel;
import com.tech.mymovietvshows.Model.MovieCreditsModel;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.MovieDetailsBelongToCollection;
import com.tech.mymovietvshows.Model.MovieDetailsGenres;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private LinearLayoutCompat backdropPosterLayout;
    private LinearLayoutCompat detailLinearLayout;
    private LinearLayoutCompat collectionLayout;

    private AppCompatImageView detailBackdropPoster;
    private AppCompatImageView detailPosterImageView;
    private AppCompatTextView detailMovie_title;
    private AppCompatTextView detailVoteCount;
    private AppCompatTextView detailRating_no;
    private AppCompatTextView detail_overview;
    private AppCompatTextView collectionTextview;
    private RecyclerView detailGenresRV;

    //collection part
    private AppCompatImageView detailCollectionPoster;
    private AppCompatTextView detailCollectionName;
    private AppCompatTextView detailCollectionGenresName;

    private RecyclerView creditRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        backdropPosterLayout = findViewById(R.id.backdropPosterLayout);
        collectionLayout = findViewById(R.id.collectionLayout);
        detailBackdropPoster = findViewById(R.id.detailBackdropPoster);
        detailMovie_title = findViewById(R.id.detailMovie_title);
        detailPosterImageView = findViewById(R.id.detailPosterImageView);
        detailVoteCount = findViewById(R.id.detailVoteCount);
        detailRating_no = findViewById(R.id.detailRating_no);
        detailGenresRV = findViewById(R.id.detailGenresRV);
        detail_overview = findViewById(R.id.detail_overview);
        detailLinearLayout = findViewById(R.id.detailLinearLayout);
        collectionTextview = findViewById(R.id.collectionTextview);

        detailCollectionPoster = findViewById(R.id.detailCollectionPoster);
        detailCollectionName = findViewById(R.id.detailCollectionName);
        detailCollectionGenresName = findViewById(R.id.detailCollectionGenresName);

        creditRecyclerView = findViewById(R.id.CreditRecyclerView);

        creditRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        detailGenresRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {

            if (intent.getExtras().getString("id") != null) {

                int movie_id = Integer.parseInt(intent.getExtras().getString("id"));

                Log.d("debug", String.valueOf(movie_id));
                //Movie Details Call
                RetrofitInstance.getInstance().apiInterface.getMovieDetailsById(movie_id, api)
                        .enqueue(new Callback<MovieDetailModel>() {
                            @Override
                            public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {

                                Log.d("debug", "On Response");
                                MovieDetailModel movieDetailModelResponse = response.body();

                                if (movieDetailModelResponse != null) {
                                    prepareMovieDetails(movieDetailModelResponse);

                                } else {
                                    detailLinearLayout.setVisibility(View.GONE);
                                    Log.d("debug", "movie Detail NULL");
                                    Toast.makeText(MovieDetailActivity.this, "Movie Detail NULL", Toast.LENGTH_SHORT).show();
                                }

                                //to get collection id
                                assert movieDetailModelResponse != null;
                                MovieDetailsBelongToCollection movieDetailsBelongToCollection = movieDetailModelResponse.getBelongs_to_collection();
                                List<MovieDetailsGenres>  genresList = movieDetailModelResponse.getGenres(); // for get genres name

                                if (movieDetailsBelongToCollection != null && genresList != null) {

                                        CallGenresListShow(genresList);
                                        GenresRVAdapter adapter = new GenresRVAdapter(genresList, MovieDetailActivity.this);
                                        detailGenresRV.setAdapter(adapter);

                                    Integer collection_id = movieDetailsBelongToCollection.getId();
                                    CallMovieCollection(collection_id);
                                } else{
                                    collectionLayout.setVisibility(View.GONE);
                                    Log.d("debug", "Movie Collection is Null");
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<MovieDetailModel> call, @NonNull Throwable t) {
                                Log.d("debug", "OnResponse Fail");
                            }
                        });

                //Cast and crew of movie set
                RetrofitInstance.getInstance().apiInterface.getMovieCreditsById(movie_id,api).enqueue(new Callback<MovieCreditsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieCreditsModel> call, @NonNull Response<MovieCreditsModel> response) {

                        MovieCreditsModel movieCreditModel = response.body();

                        if(movieCreditModel != null){
                            List<MovieCreditsCastModel>movieCreditsCastModelList = movieCreditModel.getCast();

                            if(movieCreditsCastModelList != null && !movieCreditsCastModelList.isEmpty()){
                                MovieCastRVAdapter adapter = new MovieCastRVAdapter(MovieDetailActivity.this,movieCreditsCastModelList);
                                creditRecyclerView.setAdapter(adapter);

                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                creditRecyclerView.setLayoutAnimation(controller);
                                creditRecyclerView.scheduleLayoutAnimation();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieCreditsModel> call, @NonNull Throwable t) {
                        Log.d("movie_detail", "On Response Fail");
                    }
                });
            }
        }
    }

    private void CallGenresListShow(List<MovieDetailsGenres> genresList) {

        if (genresList != null && genresList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < genresList.size(); i++) {

                if (i == genresList.size() - 1) {
                    stringBuilder.append(genresList.get(i).getName());
                } else {
                    stringBuilder.append(genresList.get(i).getName()).append(", ");
                }
            }
            detailCollectionGenresName.setText(stringBuilder.toString());
        }
    }

    private void CallMovieCollection(Integer collection_id) {

        RetrofitInstance.getInstance().apiInterface.getMovieCollectionById(collection_id, api).enqueue(new Callback<MovieCollectionModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieCollectionModel> call, @NonNull Response<MovieCollectionModel> response) {

                MovieCollectionModel movieCollectionModel = response.body();

                if (movieCollectionModel != null) {

                    Picasso.get()
                            .load(movieCollectionModel.getPoster_path())
                            .placeholder(R.drawable.image_loading)
                            .into(detailCollectionPoster);

                    if (movieCollectionModel.getName() != null) {

                        detailCollectionName.setText(movieCollectionModel.getName());
                    }

                    collectionTextview.setVisibility(View.VISIBLE);
                    collectionLayout.setVisibility(View.VISIBLE);

                    List<UpcomingNowMovieResultModel> movieCollectionPart = movieCollectionModel.getParts();

                    if (movieCollectionPart != null && !movieCollectionPart.isEmpty()) {

                        collectionLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MovieDetailActivity.this,CollectionDetailActivity.class);
                                intent.putExtra("collection_id", String.valueOf(collection_id));
                                startActivity(intent);
                                MovieDetailActivity.this.overridePendingTransition(R.anim.slide_from_bottom,R.anim.to_top);
                            }
                        });

                    } else {
                        detailCollectionName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); // remove next button
                        Toast.makeText(MovieDetailActivity.this, "No any Collections", Toast.LENGTH_SHORT).show();
                        Log.d("collectionDebug", "Movie Collection Part is NULL");
                    }
                } else {
                    collectionLayout.setVisibility(View.GONE);
                    collectionTextview.setVisibility(View.GONE);
                    Log.d("collectionDebug", "Movie Collection is NULL");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCollectionModel> call, @NonNull Throwable t) {
                Log.d("collectionDebug", "On Response Fail");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void prepareMovieDetails(MovieDetailModel movieDetailModelResponse) {

        if (movieDetailModelResponse.getBackdrop_path() != null) {

            Picasso.get()
                    .load(movieDetailModelResponse.getBackdrop_path())
                    .placeholder(R.drawable.image_loading)
                    .into(detailBackdropPoster);

            backdropPosterLayout.setVisibility(View.VISIBLE);
        } else {
            backdropPosterLayout.setVisibility(View.GONE);
        }

        if (movieDetailModelResponse.getOriginal_title() != null) {
            detailMovie_title.setText(movieDetailModelResponse.getOriginal_title());
        }
        if (movieDetailModelResponse.getPoster_path() != null) {
            Picasso.get().load(movieDetailModelResponse.getPoster_path())
                    .placeholder(R.drawable.image_loading)
                    .into(detailPosterImageView);
        }
        if (movieDetailModelResponse.getVote_count() > 0) {
            detailVoteCount.setText("(" + movieDetailModelResponse.getVote_count() + ")");
        }else{
            detailVoteCount.setText("(" + movieDetailModelResponse.getVote_count() + ")");
            detailVoteCount.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.rating_blank,0,0,0);
        }
        if (movieDetailModelResponse.getVote_average() != null) {
            detailRating_no.setText(String.valueOf(movieDetailModelResponse.getVote_average()));
        }

        if (movieDetailModelResponse.getOverview() != null) {
            detail_overview.setText(movieDetailModelResponse.getOverview());
        }


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}