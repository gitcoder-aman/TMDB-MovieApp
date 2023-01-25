package com.tech.mymovietvshows;

import static com.tech.mymovietvshows.MainActivity.api;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.mymovietvshows.Adapter.GenresRVAdapter;
import com.tech.mymovietvshows.Adapter.MovieCreditRVAdapter;
import com.tech.mymovietvshows.Adapter.MovieVideoAdapter;
import com.tech.mymovietvshows.Adapter.RecommendTvShowsAdapter;
import com.tech.mymovietvshows.Adapter.SimilarTvShowsAdapter;
import com.tech.mymovietvshows.Client.RetrofitInstance;
import com.tech.mymovietvshows.Model.MovieCreditsCastModel;
import com.tech.mymovietvshows.Model.MovieCreditsModel;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.MovieDetailProductCompany;
import com.tech.mymovietvshows.Model.MovieDetailSpokenLanguage;
import com.tech.mymovietvshows.Model.MovieDetailsGenres;
import com.tech.mymovietvshows.Model.MovieVideosModel;
import com.tech.mymovietvshows.Model.MovieVideosResults;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieModel;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieResultModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsDetailActivity extends AppCompatActivity {

    private LinearLayoutCompat backdropPosterLayout;
    private LinearLayoutCompat detailLinearLayout;
    private LinearLayoutCompat creditLayout;

    private AppCompatImageView detailBackdropPoster;
    private AppCompatImageView detailPosterImageView;
    private AppCompatTextView detailMovie_title;
    private AppCompatTextView detailVoteCount;
    private AppCompatTextView detailRating_no;
    private AppCompatTextView detail_overview;
    private AppCompatTextView credit_seeAll;
    private RecyclerView detailGenresRV;

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

    private Toolbar detailToolbar;

    private ProgressBar progressDetailTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        backdropPosterLayout = findViewById(R.id.backdropPosterLayout);
        creditLayout = findViewById(R.id.creditLayout);
        detailBackdropPoster = findViewById(R.id.detailBackdropPoster);
        detailMovie_title = findViewById(R.id.detailMovie_title);
        detailPosterImageView = findViewById(R.id.detailPosterImageView);
        detailVoteCount = findViewById(R.id.detailVoteCount);
        detailRating_no = findViewById(R.id.detailRating_no);
        detailGenresRV = findViewById(R.id.detailGenresRV);
        detail_overview = findViewById(R.id.detail_overview);
        detailLinearLayout = findViewById(R.id.detailLinearLayout);
        credit_seeAll = findViewById(R.id.credit_seeAll);
        progressDetailTV = findViewById(R.id.progressDetailMovie);

        creditRecyclerView = findViewById(R.id.CreditRecyclerView);
        videoRecyclerView = findViewById(R.id.videoRecyclerView);
        videoLinearLayout = findViewById(R.id.videoLinearLayout);
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

                int tv_id = Integer.parseInt(intent.getExtras().getString("id"));

                Log.d("debug", String.valueOf(tv_id));


                //Tv Show Detail Call
                RetrofitInstance.getInstance().apiInterface.getTvShowsDetailsById(tv_id, api)
                        .enqueue(new Callback<MovieDetailModel>() {
                            @Override
                            public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {

                                progressDetailTV.setVisibility(View.GONE);
                                detailLinearLayout.setVisibility(View.VISIBLE);

                                Log.d("debug", "On Response");
                                MovieDetailModel tvDetailModelResponse = response.body();

                                if (tvDetailModelResponse != null) {
                                    prepareTvDetails(tvDetailModelResponse);
                                    posterDetailLL.setVisibility(View.VISIBLE);
                                    infoLinearLayout.setVisibility(View.VISIBLE);

                                } else {
                                    detailLinearLayout.setVisibility(View.GONE);
                                    infoLinearLayout.setVisibility(View.GONE);
                                    Log.d("debug", "movie Detail NULL");
                                    Toast.makeText(TvShowsDetailActivity.this, "Movie Detail NULL", Toast.LENGTH_SHORT).show();
                                }

                                //genres set of tv
                                List<MovieDetailsGenres> tvGenresList = tvDetailModelResponse.getGenres();
                                if (tvGenresList != null && !tvGenresList.isEmpty()) {

                                    GenresRVAdapter adapter = new GenresRVAdapter(tvGenresList, TvShowsDetailActivity.this);
                                    detailGenresRV.setAdapter(adapter);
                                    detailGenresRV.setVisibility(View.VISIBLE);
                                } else {
                                    detailGenresRV.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<MovieDetailModel> call, @NonNull Throwable t) {
                                Log.d("debug", "OnResponse Fail");
                            }
                        });


                //Cast and crew of movie set
                RetrofitInstance.getInstance().apiInterface.getTvShowsCreditsById(tv_id, api).enqueue(new Callback<MovieCreditsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieCreditsModel> call, @NonNull Response<MovieCreditsModel> response) {

                        MovieCreditsModel movieCreditModel = response.body();

                        if (movieCreditModel != null) {
                            List<MovieCreditsCastModel> movieCreditsCastModelList = movieCreditModel.getCast();

                            if (movieCreditsCastModelList != null && !movieCreditsCastModelList.isEmpty()) {
                                MovieCreditRVAdapter adapter = new MovieCreditRVAdapter(TvShowsDetailActivity.this, movieCreditsCastModelList);
                                creditRecyclerView.setAdapter(adapter);

                                creditLayout.setVisibility(View.VISIBLE);
                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(TvShowsDetailActivity.this, R.anim.layout_slide_right);
                                creditRecyclerView.setLayoutAnimation(controller);
                                creditRecyclerView.scheduleLayoutAnimation();
                            } else {
                                creditLayout.setVisibility(View.GONE);
                            }
                        } else {
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
                        Intent intent1 = new Intent(TvShowsDetailActivity.this, MovieCreditActivity.class);
                        intent1.putExtra("credit_movieId", String.valueOf(tv_id));
                        intent1.putExtra("creditType", "tv");
                        Log.d("credit", "pass " + tv_id);
                        startActivity(intent1);
                    }
                });

                //Tv Video show
                RetrofitInstance.getInstance().apiInterface.getTvVideosById(tv_id, api).enqueue(new Callback<MovieVideosModel>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieVideosModel> call, @NonNull Response<MovieVideosModel> response) {

                        MovieVideosModel movieVideosModel = response.body();
                        if (movieVideosModel != null) {

                            List<MovieVideosResults> movieVideosResultsList = movieVideosModel.getResults();

                            if (movieVideosResultsList != null && !movieVideosResultsList.isEmpty()) {

                                MovieVideoAdapter adapter = new MovieVideoAdapter(TvShowsDetailActivity.this, movieVideosResultsList);
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

                //show recommend tv shows in recyclerview

                RetrofitInstance.getInstance().apiInterface.getRecommendationsTvShowById(tv_id, api).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {

                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel recommendMovieResponse = response.body();

                        if (recommendMovieResponse != null) {

                            List<TrendingPopularTopRatedMovieResultModel> recommendMovieList = recommendMovieResponse.getResults();

                            if (recommendMovieList != null && !recommendMovieList.isEmpty()) {
                                RecommendTvShowsAdapter adapter = new RecommendTvShowsAdapter(TvShowsDetailActivity.this, recommendMovieList);
                                recommendRecycler.setAdapter(adapter);

                                recommend_linearLayout.setVisibility(View.VISIBLE);
                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(TvShowsDetailActivity.this, R.anim.layout_slide_right);
                                recommendRecycler.setLayoutAnimation(controller);
                                recommendRecycler.scheduleLayoutAnimation();
                            } else {
                                recommend_linearLayout.setVisibility(View.GONE);
                            }
                        } else {
                            recommend_linearLayout.setVisibility(View.GONE);
                            Toast.makeText(TvShowsDetailActivity.this, "Movie Detail Null.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Throwable t) {
                        Log.d("recommend", "On Response fail");
                    }
                });
                //show similar movie in recycler view

                RetrofitInstance.getInstance().apiInterface.getSimilarTvShowById(tv_id, api).enqueue(new Callback<TrendingPopularTopRatedMovieModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TrendingPopularTopRatedMovieModel> call, @NonNull Response<TrendingPopularTopRatedMovieModel> response) {

                        TrendingPopularTopRatedMovieModel similarMovieResponse = response.body();

                        if (similarMovieResponse != null) {

                            List<TrendingPopularTopRatedMovieResultModel> similarMovieList = similarMovieResponse.getResults();

                            if (similarMovieList != null && !similarMovieList.isEmpty()) {
                                SimilarTvShowsAdapter adapter = new SimilarTvShowsAdapter(TvShowsDetailActivity.this, similarMovieList);
                                similarRecycler.setAdapter(adapter);

                                similar_linearLayout.setVisibility(View.VISIBLE);
                                //Create some animation view item loading
                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(TvShowsDetailActivity.this, R.anim.layout_slide_right);
                                similarRecycler.setLayoutAnimation(controller);
                                similarRecycler.scheduleLayoutAnimation();
                            } else {
                                similar_linearLayout.setVisibility(View.GONE);
                                Toast.makeText(TvShowsDetailActivity.this, "Movie details not available.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            similar_linearLayout.setVisibility(View.GONE);
                            Toast.makeText(TvShowsDetailActivity.this, "Movie Detail Null.", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("SetTextI18n")
    private void prepareTvDetails(MovieDetailModel movieDetailModelResponse) {

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