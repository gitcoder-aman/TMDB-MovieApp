package com.tech.mymovietvshows.Model;

import java.util.List;

public class GetTvShowMovieModel {

    private Integer id;

    private List<GetTvShowCastMovieModel>cast;
    private List<GetTvShowCrewMovieModel>crew;

    public GetTvShowMovieModel() {
    }

    public GetTvShowMovieModel(Integer id, List<GetTvShowCastMovieModel> cast, List<GetTvShowCrewMovieModel> crew) {
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

    public List<GetTvShowCastMovieModel> getCast() {
        return cast;
    }

    public void setCast(List<GetTvShowCastMovieModel> cast) {
        this.cast = cast;
    }

    public List<GetTvShowCrewMovieModel> getCrew() {
        return crew;
    }

    public void setCrew(List<GetTvShowCrewMovieModel> crew) {
        this.crew = crew;
    }
}
