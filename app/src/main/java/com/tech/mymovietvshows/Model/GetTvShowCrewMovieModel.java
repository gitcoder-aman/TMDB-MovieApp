package com.tech.mymovietvshows.Model;

public class GetTvShowCrewMovieModel {

    private boolean adult;
    private Integer id;
    private String backdrop_path;
    private String credit_id;
    private int episode_count;
    private String first_air_date;
    private String name;
    private String original_language;
    private String original_name;
    private String overview;
    private Double popularity;
    private String poster_path;
    private String department;
    private String job;
    private double vote_average;
    private int vote_count;

    public GetTvShowCrewMovieModel() {
    }

    public GetTvShowCrewMovieModel(Integer id,boolean adult, String backdrop_path, String credit_id, int episode_count, String first_air_date, String name, String original_language, String original_name, String overview, Double popularity, String poster_path, String department, String job, double vote_average, int vote_count) {
        this.adult = adult;
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.credit_id = credit_id;
        this.episode_count = episode_count;
        this.first_air_date = first_air_date;
        this.name = name;
        this.original_language = original_language;
        this.original_name = original_name;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.department = department;
        this.job = job;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(int episode_count) {
        this.episode_count = episode_count;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
