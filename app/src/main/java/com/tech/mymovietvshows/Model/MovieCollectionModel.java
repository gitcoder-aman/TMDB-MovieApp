package com.tech.mymovietvshows.Model;

import java.util.List;

public class MovieCollectionModel {

    private Integer id;
    private String name;
    private String overview;
    private String backdrop_path;
    private String poster_path;

    private List<UpcomingNowMovieResultModel> parts;

    public MovieCollectionModel() {
    }

    public MovieCollectionModel(Integer id, String name, String overview, String backdrop_path, String poster_path, List<UpcomingNowMovieResultModel> parts) {
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.parts = parts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop_path() {
        //create a baseUrl for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl+backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {

        //create a baseUrl for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl+poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<UpcomingNowMovieResultModel> getParts() {
        return parts;
    }

    public void setParts(List<UpcomingNowMovieResultModel> parts) {
        this.parts = parts;
    }
}
