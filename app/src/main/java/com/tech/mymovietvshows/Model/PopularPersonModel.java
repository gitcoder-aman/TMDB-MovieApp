package com.tech.mymovietvshows.Model;

import java.util.List;

public class PopularPersonModel {

    private int page;
    private int total_pages;
    private int total_results;

    private List<PopularPersonModelResult>results;

    public PopularPersonModel() {
    }

    public PopularPersonModel(int page, int total_pages, int total_results, List<PopularPersonModelResult> results) {
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<PopularPersonModelResult> getResults() {
        return results;
    }

    public void setResults(List<PopularPersonModelResult> results) {
        this.results = results;
    }
}
