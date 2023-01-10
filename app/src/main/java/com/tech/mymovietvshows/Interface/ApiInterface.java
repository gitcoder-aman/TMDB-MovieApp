package com.tech.mymovietvshows.Interface;

import com.tech.mymovietvshows.Model.MovieCollectionModel;
import com.tech.mymovietvshows.Model.MovieCreditsModel;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.MovieVideosModel;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieModel;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieResultModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("trending/all/day")
    Call<TrendingPopularTopRatedMovieModel> getTrendingMovie(@Query("api_key") String api_key);

    @GET("movie/popular")
    Call<TrendingPopularTopRatedMovieModel>getPopularMovie(@Query("api_key") String api_key);

    @GET("movie/upcoming")
    Call<UpcomingNowMovieModel>getUpcomingMovie(@Query("api_key") String api_key);

    @GET("movie/now_playing")
    Call<UpcomingNowMovieModel>getNowPlayingMovie(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<TrendingPopularTopRatedMovieModel>getTopRatedMovie(@Query("api_key") String api_key);

    //create a Service for movie Details

    @GET("movie/{movie_id}")
    Call<MovieDetailModel>getMovieDetailsById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    //Create a service for get all movie Collections

    @GET("collection/{collection_id}")
    Call<MovieCollectionModel>getMovieCollectionById(@Path("collection_id") int collection_id, @Query("api_key") String api_key);

    //create a Service for movie cast and crew

    @GET("movie/{movie_id}/credits")
    Call<MovieCreditsModel>getMovieCreditsById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    //create a Service for movie Videos

    @GET("movie/{movie_id}/videos")
    Call<MovieVideosModel>getMovieVideosById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/recommendations")
    Call<TrendingPopularTopRatedMovieModel>getRecommendationsVideosById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/similar")
    Call<TrendingPopularTopRatedMovieModel>getSimilarVideosById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
}
