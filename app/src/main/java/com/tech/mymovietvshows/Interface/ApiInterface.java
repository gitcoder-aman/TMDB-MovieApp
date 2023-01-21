package com.tech.mymovietvshows.Interface;

import com.tech.mymovietvshows.Model.GetTvShowMovieModel;
import com.tech.mymovietvshows.Model.MovieCollectionModel;
import com.tech.mymovietvshows.Model.MovieCreditsModel;
import com.tech.mymovietvshows.Model.MovieDetailModel;
import com.tech.mymovietvshows.Model.MovieResponse;
import com.tech.mymovietvshows.Model.MovieVideosModel;
import com.tech.mymovietvshows.Model.PersonDetailModel;
import com.tech.mymovietvshows.Model.TrendingPopularTopRatedMovieModel;
import com.tech.mymovietvshows.Model.UpcomingNowMovieModel;
import com.tech.mymovietvshows.Model.getCreditMovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("trending/movie/day")
    Call<TrendingPopularTopRatedMovieModel> getTrendingMovie(@Query("api_key") String api_key);

    @GET("movie/popular")
    Call<TrendingPopularTopRatedMovieModel> getPopularMovie(@Query("api_key") String api_key);

    @GET("movie/upcoming")
    Call<UpcomingNowMovieModel> getUpcomingMovie(@Query("api_key") String api_key);

    @GET("movie/now_playing")
    Call<UpcomingNowMovieModel> getNowPlayingMovie(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<TrendingPopularTopRatedMovieModel> getTopRatedMovie(@Query("api_key") String api_key);

    //create a Service for movie Details

    @GET("movie/{movie_id}")
    Call<MovieDetailModel> getMovieDetailsById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    //Create a service for get all movie Collections

    @GET("collection/{collection_id}")
    Call<MovieCollectionModel> getMovieCollectionById(@Path("collection_id") int collection_id, @Query("api_key") String api_key);

    //create a Service for movie cast and crew

    @GET("movie/{movie_id}/credits")
    Call<MovieCreditsModel> getMovieCreditsById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    //create a Service for movie Videos

    @GET("movie/{movie_id}/videos")
    Call<MovieVideosModel> getMovieVideosById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
    @GET("tv/{tv_id}/videos")
    Call<MovieVideosModel> getTvVideosById(@Path("tv_id") int tv_id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/recommendations")
    Call<TrendingPopularTopRatedMovieModel> getRecommendationsVideosById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/similar")
    Call<TrendingPopularTopRatedMovieModel> getSimilarVideosById(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    //get people details
    @GET("person/{person_id}")
    Call<PersonDetailModel> getPersonDetailById(@Path("person_id") int person_id, @Query("api_key") String api_key);

    //get movie Credits details
    @GET("person/{person_id}/movie_credits")
    Call<getCreditMovieModel> getMovieCreditsDetailById(@Path("person_id") int person_id, @Query("api_key") String api_key);

    //get movie Credits details
    @GET("person/{person_id}/tv_credits")
    Call<GetTvShowMovieModel> getTvCreditsDetailById(@Path("person_id") int person_id, @Query("api_key") String api_key);

    @GET("search/movie")
    Call<MovieResponse> getMovieByQuery(@Query("api_key") String api_key, @Query("query") String query);

    @GET("search/tv")
    Call<MovieResponse> getTvByQuery(@Query("api_key") String api_key, @Query("query") String query);

    @GET("trending/movie/day")
    Call<TrendingPopularTopRatedMovieModel> getTrendingMovieDay(@Query("api_key") String api_key);

    @GET("trending/tv/day")
    Call<TrendingPopularTopRatedMovieModel> getTrendingTvDay(@Query("api_key") String api_key);

    @GET("tv/popular")
    Call<TrendingPopularTopRatedMovieModel> getPopularTv(@Query("api_key") String api_key);

    @GET("tv/airing_today")
    Call<TrendingPopularTopRatedMovieModel> getAiringTodayTv(@Query("api_key") String api_key);

    @GET("tv/on_the_air")
    Call<TrendingPopularTopRatedMovieModel> getOnTheAirTv(@Query("api_key") String api_key);

    @GET("tv/top_rated")
    Call<TrendingPopularTopRatedMovieModel> getTopRatedTv(@Query("api_key") String api_key);

    @GET("tv/{tv_id}/recommendations")
    Call<TrendingPopularTopRatedMovieModel> getRecommendationsTvShowById(@Path("tv_id") int tv_id, @Query("api_key") String api_key);

    @GET("tv/{tv_id}/similar")
    Call<TrendingPopularTopRatedMovieModel> getSimilarTvShowById(@Path("tv_id") int tv_id, @Query("api_key") String api_key);

    //create a Service for movie Details

    @GET("tv/{tv_id}")
    Call<MovieDetailModel> getTvShowsDetailsById(@Path("tv_id") int tv_id, @Query("api_key") String api_key);

    @GET("tv/{tv_id}/credits")
    Call<MovieCreditsModel> getTvShowsCreditsById(@Path("tv_id") int tv_id, @Query("api_key") String api_key);


}
