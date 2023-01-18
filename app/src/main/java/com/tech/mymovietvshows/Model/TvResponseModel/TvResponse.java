package com.tech.mymovietvshows.Model.TvResponseModel;

import java.util.List;

public class TvResponse {
    private boolean adult;
    private String backdrop_path;
    private List<CreatedBy>created_by;
    private String first_air_date;
    private List<TvGenres>genres;
    private String homepage;
    private int id;
    private boolean in_production;
    private String last_air_date;
    private List<LastEpisode>last_episode_to_air;
    private String name;
    private List<Networks>networks;
    private String next_episode_to_air;
    private int number_of_episodes;
    private int number_of_seasons;
    private String original_language;
    private String original_name;
    private String overview;
    private Double popularity;
    private String poster_path;
    private List<ProductionCompany>production_companies;
    private List<ProductionCountry>production_countries;
    private List<Seasons>seasons;
    private List<SpokenLanguage>spoken_languages;
    private String status;
    private String tagline;
    private String type;
    private Double vote_average;
    private int vote_count;

    public TvResponse() {
    }

    public TvResponse(boolean adult, String backdrop_path, List<CreatedBy> created_by, String first_air_date, List<TvGenres> genres, String homepage, int id, boolean in_production, String last_air_date, List<LastEpisode> last_episode_to_air, String name, List<Networks> networks, String next_episode_to_air, int number_of_episodes, int number_of_seasons, String original_language, String original_name, String overview, Double popularity, String poster_path, List<ProductionCompany> production_companies, List<ProductionCountry> production_countries, List<Seasons> seasons, List<SpokenLanguage> spoken_languages, String status, String tagline, String type, Double vote_average, int vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.created_by = created_by;
        this.first_air_date = first_air_date;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.in_production = in_production;
        this.last_air_date = last_air_date;
        this.last_episode_to_air = last_episode_to_air;
        this.name = name;
        this.networks = networks;
        this.next_episode_to_air = next_episode_to_air;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.original_language = original_language;
        this.original_name = original_name;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.production_companies = production_companies;
        this.production_countries = production_countries;
        this.seasons = seasons;
        this.spoken_languages = spoken_languages;
        this.status = status;
        this.tagline = tagline;
        this.type = type;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
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

    public List<CreatedBy> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(List<CreatedBy> created_by) {
        this.created_by = created_by;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public List<TvGenres> getGenres() {
        return genres;
    }

    public void setGenres(List<TvGenres> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public List<LastEpisode> getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(List<LastEpisode> last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Networks> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Networks> networks) {
        this.networks = networks;
    }

    public String getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(String next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
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

    public List<ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    public List<ProductionCountry> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(List<ProductionCountry> production_countries) {
        this.production_countries = production_countries;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Seasons> seasons) {
        this.seasons = seasons;
    }

    public List<SpokenLanguage> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(List<SpokenLanguage> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}


