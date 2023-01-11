package com.tech.mymovietvshows.Model;

import java.util.List;

//
public class PersonDetailModel {

    private String birthday;
    private String biography;
    private String deathday;
    private String gender;      //gender 2 mean male, and 1 mean female
    private String known_for_department;
    private String name;
    private String place_of_birth;
    private Integer id;

    private List<String> also_known_as;
    private float popularity;
    private String profile_path;
    private boolean adult;
    private String imdb_id;
    private String homepage;

    public PersonDetailModel() {
    }

    public PersonDetailModel(String birthday, String biography, String deathday, String gender, String known_for_department, String name, String place_of_birth, Integer id, List<String> also_known_as, float popularity, String profile_path, boolean adult, String imdb_id, String homepage) {
        this.birthday = birthday;
        this.biography = biography;
        this.deathday = deathday;
        this.gender = gender;
        this.known_for_department = known_for_department;
        this.name = name;
        this.place_of_birth = place_of_birth;
        this.id = id;
        this.also_known_as = also_known_as;
        this.popularity = popularity;
        this.profile_path = profile_path;
        this.adult = adult;
        this.imdb_id = imdb_id;
        this.homepage = homepage;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(List<String> also_known_as) {
        this.also_known_as = also_known_as;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getProfile_path() {

        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl+profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
