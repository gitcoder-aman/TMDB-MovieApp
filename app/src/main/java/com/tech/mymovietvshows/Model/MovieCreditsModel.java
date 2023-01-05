package com.tech.mymovietvshows.Model;

import java.util.List;

public class MovieCreditsModel {

    private Integer id;

    private List<MovieCreditsCastModel>cast;
    private List<MovieCreditsCrewModel>crew;

    public MovieCreditsModel() {
    }

    public MovieCreditsModel(Integer id, List<MovieCreditsCastModel> cast, List<MovieCreditsCrewModel> crew) {
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

    public List<MovieCreditsCastModel> getCast() {
        return cast;
    }

    public void setCast(List<MovieCreditsCastModel> cast) {
        this.cast = cast;
    }

    public List<MovieCreditsCrewModel> getCrew() {
        return crew;
    }

    public void setCrew(List<MovieCreditsCrewModel> crew) {
        this.crew = crew;
    }
}
