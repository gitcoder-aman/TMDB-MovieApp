package com.tech.mymovietvshows.Model;

import java.util.List;

public class MovieVideosModel {

    private Integer id;
    private List<MovieVideosResults>results;

    public MovieVideosModel() {
    }

    public MovieVideosModel(Integer id, List<MovieVideosResults> results) {
        this.id = id;
        this.results = results;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieVideosResults> getResults() {
        return results;
    }

    public void setResults(List<MovieVideosResults> results) {
        this.results = results;
    }

}
