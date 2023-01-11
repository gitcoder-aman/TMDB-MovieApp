package com.tech.mymovietvshows.Model;

import java.util.List;

public class getCreditMovieModel {

    private Integer id;

    List<getCastMovieModel> cast;
    List<getCrewMovieModel>crew;

    public getCreditMovieModel() {
    }

    public getCreditMovieModel(Integer id, List<getCastMovieModel> cast, List<getCrewMovieModel> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<getCastMovieModel> getCast() {
        return cast;
    }

    public void setCast(List<getCastMovieModel> cast) {
        this.cast = cast;
    }

    public List<getCrewMovieModel> getCrew() {
        return crew;
    }

    public void setCrew(List<getCrewMovieModel> crew) {
        this.crew = crew;
    }
}
