package com.tech.mymovietvshows.Model;

public class UpcomingNowMovieResultModel {

    private boolean adult;
    private String backdrop_path;
    private Integer id;
    private String original_language;
    private String overview;
    private Double popularity;
    private String poster_path;
    private String title;
    private boolean video;
    private Double vote_average;
    private Integer vote_count;
    private String original_title;
    private String release_date;

    public UpcomingNowMovieResultModel() {
    }

    public UpcomingNowMovieResultModel(boolean adult, String backdrop_path, Integer id, String original_language, String overview, Double popularity, String poster_path, String title, boolean video, Double vote_average, Integer vote_count, String original_title, String release_date) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.original_language = original_language;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.original_title = original_title;
        this.release_date = release_date;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        //create a baseUrl for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl+backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {

        //create a baseUrl for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl+poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
