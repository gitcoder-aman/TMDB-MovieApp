package com.tech.mymovietvshows.Model;

import java.util.List;

public class UpcomingNowMovieModel {

    private int page;
    private MovieDates dates;
    private List<UpcomingNowMovieResultModel>results;

    public UpcomingNowMovieModel() {
    }

    public UpcomingNowMovieModel(int page, MovieDates dates, List<UpcomingNowMovieResultModel> results) {
        this.page = page;
        this.dates = dates;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MovieDates getDates() {
        return dates;
    }

    public void setDates(MovieDates dates) {
        this.dates = dates;
    }

    public List<UpcomingNowMovieResultModel> getResults() {
        return results;
    }

    public void setResults(List<UpcomingNowMovieResultModel> results) {
        this.results = results;
    }
}
