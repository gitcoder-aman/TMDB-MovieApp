package com.tech.mymovietvshows.Model;

public class MovieCreditsCrewModel {

    private boolean adult;
    private int gender;
    private int id;
    private String known_for_department;
    private String name;
    private String original_name;
    private Double popularity;
    private String profile_path;
    private String credit_id;
    private String department;
    private String job;

    public MovieCreditsCrewModel() {
    }

    public MovieCreditsCrewModel(boolean adult, int gender, int id, String known_for_department, String name, String original_name, Double popularity, String profile_path, String credit_id, String department, String job) {
        this.adult = adult;
        this.gender = gender;
        this.id = id;
        this.known_for_department = known_for_department;
        this.name = name;
        this.original_name = original_name;
        this.popularity = popularity;
        this.profile_path = profile_path;
        this.credit_id = credit_id;
        this.department = department;
        this.job = job;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getProfile_path() {

        //create a baseUrl for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl+profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
