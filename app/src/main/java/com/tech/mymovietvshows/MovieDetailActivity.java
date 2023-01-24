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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Adapter.GenresRVAdapter;
import com.tech.mymovietvshows.Adapter.MovieCreditRVAdapter;
import com.tech.mymovietvshows.Adapter.MovieVideoAdapter;
import com.tech.mymovietvshows.Adapter.RecommendMovieAdapter;
import com.tech.mymovietvshows.Adapter.SimilarMovieAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieCollectionModel;
import com.tech.mymovietvshows.Model.MovieCreditsCastModel;
import com.tech.mymovietvshows.Model.MovieCreditsModel;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.MovieDetailProductCompany;
import com.tech.mymovietvshows.Model.MovieDetailSpokenLanguage;
import com.tech.mymovietvshows.Model.MovieDetailsBelongToCollection;
import com.tech.mymovietvshows.Model.MovieDetailsGenres;
import com.tech.mymovietvshows.Model.MovieVideosModel;
import com.tech.mymovietvshows.Model.MovieVideosResults;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieModel;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieResultModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieResultModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private LinearLayoutCompat backdropPosterLayout;
    private LinearLayoutCompat detailLinearLayout;
    private LinearLayoutCompat collectionLayout;
    private LinearLayoutCompat creditLayout;

    private AppCompatImageView detailBackdropPoster;
    private AppCompatImageView detailPosterImageView;
    private AppCompatTextView detailMovie_title;
    private AppCompatTextView detailVoteCount;
    private AppCompatTextView detailRating_no;
    private AppCompatTextView detail_overview;
    private AppCompatTextView collectionTextview;
    private AppCompatTextView credit_seeAll;
    private RecyclerView detailGenresRV;

    //collection part
    private AppCompatImageView detailCollectionPoster;
    private AppCompatTextView detailCollectionName;
    private AppCompatTextView detailCollectionGenresName;

    private RecyclerView creditRecyclerView;
    private RecyclerView videoRecyclerView;
    private RecyclerView recommendRecycler;
    private RecyclerView similarRecycler;
    private LinearLayoutCompat videoLinearLayout;
    private LinearLayoutCompat recommend_linearLayout;
    private LinearLayoutCompat similar_linearLayout;

    //information
    private AppCompatTextView detailReleaseDate;
    private AppCompatTextView detailLanguage;
    private AppCompatTextView detailBudget;
    private AppCompatTextView detailRevenue;
    private AppCompatTextView detailProductComp;
    private LinearLayoutCompat infoLinearLayout;
    private LinearLayoutCompat posterDetailLL;

    private AppCompatTextView runtime;

    private Toolbar detailToolbar;
    private ProgressBar progressDetailMovie;


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
        progressDetailMovie = findViewById(R.id.progressDetailMovie);
        collectionTextview = findViewById(R.id.collectionTextview);
        credit_seeAll = findViewById(R.id.credit_seeAll);
        runtime = findViewById(R.id.detail_runtime);

        detailCollectionPoster = findViewById(R.id.detailCollectionPoster);
        detailCollectionName = findViewById(R.id.detailCollectionName);
        detailCollectionGenresName = findViewById(R.id.detailCollectionGenresName);

        creditRecyclerView = findViewById(R.id.CreditRecyclerView);
        videoRecyclerView = findViewById(R.id.videoRecyclerView);
        videoLinearLayout = findViewById(R.id.videoLinearLayout);
        creditLayout = findViewById(R.id.creditLayout);
        recommend_linearLayout = findViewById(R.id.recommend_linearLayout);
        similar_linearLayout = findViewById(R.id.similar_linearLayout);

        //information id find
        detailReleaseDate = findViewById(R.id.detailReleaseDate);
        detailLanguage = findViewById(R.id.detailLanguage);
        detailBudget = findViewById(R.id.detailBudget);
        detailRevenue = findViewById(R.id.detailRevenue);
        detailProductComp = findViewById(R.id.detailProductComp);
        infoLinearLayout = findViewById(R.id.infoLinearLayout);
        posterDetailLL = findViewById(R.id.posterDetailLL);

        recommendRecycler = findViewById(R.id.recommendRecycler);
        similarRecycler = findViewById(R.id.similarRecycler);

        detailToolbar = findViewById(R.id.detailToolbar);

        creditRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        detailGenresRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recommendRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        similarRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setSupportActionBar(detailToolbar);
        detailToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

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

                                progressDetailMovie.setVisibility(View.GONE);
                                detailLinearLayout.setVisibility(View.VISIBLE);

                                Log.d("debug", "On Response");
                                MovieDetailModel movieDetailModelResponse = response.body();

                                if (movieDetailModelResponse != null) {
                                    prepareMovieDetails(movieDetailModelResponse);
                                    posterDetailLL.setVisibility(View.VISIBLE);
                                    infoLinearLayout.setVisibility(View.VISIBLE);

                                } else {
                                    detailLinearLayout.setVisibility(View.GONE);
                                    infoLinearLayout.setVisibility(View.GONE);
                                    Log.d("debug", "movie Detail NULL");
                                    Toast.makeText(MovieDetailActivity.this, "Movie Detail NULL", Toast.LENGTH_SHORT).show();
                                }

                                //to get collection id
                                assert movieDetailModelResponse != null;
                                MovieDetailsBelongToCollection movieDetailsBelongToCollection = movieDetailModelResponse.getBelongs_to_collection();
                                List<MovieDetailsGenres> genresList = movieDetailModelResponse.getGenres(); // for get genres name

                                if (movieDetailsBelongToCollection != null && genresList != null) {

                                    CallGenresListShow(genresList);
                                    GenresRVAdapter adapter = new GenresRVAdapter(genresList, MovieDetailActivity.this);
                                    detailGenresRV.setAdapter(adapter);
                                    detailGenresRV.setVisibility(View.VISIBLE);

                                    Integer collection_id = movieDetailsBelongToCollection.getId();
                                    CallMovieCollection(collection_id);
                                } else {
                                    detailGenresRV.setVisibility(View.GONE);
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
                RetrofitInstance.getInstance().apiInterface.getMovieCreditsById(movie_id, api).enqueue(new Callback<MovieCreditsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieCreditsModel> call, @NonNull Response<MovieCreditsModel> response) {

                        MovieCreditsModel movieCreditModel = response.body();

                        if (movieCreditModel != null) {
                            List<MovieCreditsCastModel> movieCreditsCastModelList = movieCreditModel.getCast();

                            if (movieCreditsCastModelList != null && !movieCreditsCastModelList.isEmpty()) {
                                MovieCreditRVAdapter adapter = new MovieCreditRVAdapter(MovieDetailActivity.this, movieCreditsCastModelList);
                                creditRecyclerView.setAdapter(adapter);

                                creditLayout.setVisibility(View.VISIBLE);
                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                creditRecyclerView.setLayoutAnimation(controller);
                                creditRecyclerView.scheduleLayoutAnimation();
                            }else{
                                creditLayout.setVisibility(View.GONE);
                            }
                        }else{
                            creditLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieCreditsModel> call, @NonNull Throwable t) {
                        Log.d("movie_detail", "On Response Fail");
                    }
                });

                credit_seeAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(MovieDetailActivity.this, MovieCreditActivity.class);
                        intent1.putExtra("credit_movieId", String.valueOf(movie_id));
                        intent1.putExtra("creditType", "movie");
                        Log.d("credit", "pass " + movie_id);
                        startActivity(intent1);
                    }
                });

                //Movie Video show
                RetrofitInstance.getInstance().apiInterface.getMovieVideosById(movie_id, api).enqueue(new Callback<MovieVideosModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieVideosModel> call, @NonNull Response<MovieVideosModel> response) {

                        MovieVideosModel movieVideosModel = response.body();
                        if (movieVideosModel != null) {

                            List<MovieVideosResults> movieVideosResultsList = movieVideosModel.getResults();

                            if (movieVideosResultsList != null && !movieVideosResultsList.isEmpty()) {

                                MovieVideoAdapter adapter = new MovieVideoAdapter(MovieDetailActivity.this, movieVideosResultsList);
                                videoRecyclerView.setAdapter(adapter);
                                videoLinearLayout.setVisibility(View.VISIBLE);
                            } else {
                                videoLinearLayout.setVisibility(View.GONE);
                            }
                        } else {
                            videoLinearLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieVideosModel> call, @NonNull Throwable t) {
                        Log.d("video", "On Response fail");
                    }
                });

                //show recommend movie in recyclerview


                RetrofitInstance.getInstance().apiInterface.getRecommendationsVideosById(movie_id, api).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {

                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel recommendMovieResponse = response.body();

                        if (recommendMovieResponse != null) {

                            List<TrendingPopularTopRatedMovieResultModel> recommendMovieList = recommendMovieResponse.getResults();

                            if (recommendMovieList != null && !recommendMovieList.isEmpty()) {
                                RecommendMovieAdapter adapter = new RecommendMovieAdapter(MovieDetailActivity.this, recommendMovieList);
                                recommendRecycler.setAdapter(adapter);

                                recommend_linearLayout.setVisibility(View.VISIBLE);
                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                recommendRecycler.setLayoutAnimation(controller);
                                recommendRecycler.scheduleLayoutAnimation();
                            } else {
                                recommend_linearLayout.setVisibility(View.GONE);
                            }
                        } else {
                            recommend_linearLayout.setVisibility(View.GONE);
                            Toast.makeText(MovieDetailActivity.this, "Movie Detail Null.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("recommend", "On Response fail");
                    }
                });
                //show similar movie in recycler view

                RetrofitInstance.getInstance().apiInterface.getSimilarVideosById(movie_id, api).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel similarMovieResponse = response.body();

                        if (similarMovieResponse != null) {

                            List<TrendingPopularTopRatedMovieResultModel> similarMovieList = similarMovieResponse.getResults();

                            if (similarMovieList != null && !similarMovieList.isEmpty()) {
                                SimilarMovieAdapter adapter = new SimilarMovieAdapter(MovieDetailActivity.this, similarMovieList);
                                similarRecycler.setAdapter(adapter);

                                similar_linearLayout.setVisibility(View.VISIBLE);
                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                similarRecycler.setLayoutAnimation(controller);
                                similarRecycler.scheduleLayoutAnimation();
                            } else {
                                similar_linearLayout.setVisibility(View.GONE);
                                Toast.makeText(MovieDetailActivity.this, "Movie details not available.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            similar_linearLayout.setVisibility(View.GONE);
                            Toast.makeText(MovieDetailActivity.this, "Movie Detail Null.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("recommend", "On Response fail");
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
                                Intent intent = new Intent(MovieDetailActivity.this, CollectionDetailActivity.class);
                                intent.putExtra("collection_id", String.valueOf(collection_id));
                                startActivity(intent);
                                MovieDetailActivity.this.overridePendingTransition(R.anim.slide_from_bottom, R.anim.to_top);
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
            detailToolbar.setTitle(movieDetailModelResponse.getOriginal_title());
            detailMovie_title.setText(movieDetailModelResponse.getOriginal_title());
        } else {
            detailToolbar.setTitle(movieDetailModelResponse.getName());
            detailMovie_title.setText(movieDetailModelResponse.getName());
        }

        if (movieDetailModelResponse.getPoster_path() != null) {
            Picasso.get().load(movieDetailModelResponse.getPoster_path())
                    .placeholder(R.drawable.image_loading)
                    .into(detailPosterImageView);
        }
        if (movieDetailModelResponse.getVote_count() > 0) {
            detailVoteCount.setText("(" + movieDetailModelResponse.getVote_count() + ")");
        } else {
            detailVoteCount.setText("(" + movieDetailModelResponse.getVote_count() + ")");
            detailVoteCount.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.rating_blank, 0, 0, 0);
        }
        if (movieDetailModelResponse.getVote_average() != null) {
            detailRating_no.setText(String.valueOf(movieDetailModelResponse.getVote_average()));
        }

        if (movieDetailModelResponse.getOverview() != null) {
            detail_overview.setText(movieDetailModelResponse.getOverview());
        }
        if(movieDetailModelResponse.getRuntime() != null){

            int runT = movieDetailModelResponse.getRuntime();

            int hours = (runT/60);
            int minutes = runT%60;

            String time = hours +"h " + minutes + "m";
            runtime.setText(time);
            runtime.setVisibility(View.VISIBLE);
        }

        //information LL
        if (movieDetailModelResponse.getRelease_date() != null) {
            detailReleaseDate.setText(Html.fromHtml("<b>" + "Release Date : " + "</b>" + "  " + movieDetailModelResponse.getRelease_date()));
        }

        List<MovieDetailSpokenLanguage> movieDetailSpokenLanguageList = movieDetailModelResponse.getSpoken_languages();
        if (movieDetailSpokenLanguageList != null && !movieDetailSpokenLanguageList.isEmpty()) {

            detailLanguage.setText(Html.fromHtml("<b>" + "Language : " + "</b>" + "  " + movieDetailSpokenLanguageList.get(0).getEnglish_name()));
        }
        if (movieDetailModelResponse.getBudget() != null) {

            detailBudget.setText(Html.fromHtml("<b>" + "Budget : " + "</b>" + "  " + movieDetailModelResponse.getBudget()));
        }
        if (movieDetailModelResponse.getRevenue() != null) {
            detailRevenue.setText(Html.fromHtml("<b>" + "Revenue : " + "</b>" + "  " + movieDetailModelResponse.getRevenue()));
        }
        List<MovieDetailProductCompany> movieDetailProductCompanies = movieDetailModelResponse.getProduction_companies();

        if (movieDetailProductCompanies != null && movieDetailProductCompanies.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < movieDetailProductCompanies.size(); i++) {

                if (i == movieDetailProductCompanies.size() - 1) {
                    stringBuilder.append(movieDetailProductCompanies.get(i).getName());
                } else {
                    stringBuilder.append(movieDetailProductCompanies.get(i).getName()).append(", ");
                }
            }
            detailProductComp.setText(Html.fromHtml("<b>" + "Product Company : " + "</b>" + "  " + stringBuilder));
        }


    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //When execute toolbar back pressed
        finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_top, R.anim.to_bottom);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}