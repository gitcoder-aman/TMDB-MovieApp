package com.tech.mymovietvshows.Model;


import java.util.List;

public class TrendingPopularTopRatedMovieModel {

    private Integer page;

    private List<TrendingPopularTopRatedMovieResultModel> results;

    private Integer total_pages;
    private Integer total_results;

    public TrendingPopularTopRatedMovieModel() {
    }

    public TrendingPopularTopRatedMovieModel(Integer page, List<TrendingPopularTopRatedMovieResultModel> results, Integer total_pages, Integer total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public Integer getPage() {
        return page;
    }

    public List<TrendingPopularTopRatedMovieResultModel> getResults() {
        return results;
    }

    public void setResults(List<TrendingPopularTopRatedMovieResultModel> results) {
        this.results = results;
    }

    public void setPage(Integer page) {
        this.page = page;
    }



    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }
}
