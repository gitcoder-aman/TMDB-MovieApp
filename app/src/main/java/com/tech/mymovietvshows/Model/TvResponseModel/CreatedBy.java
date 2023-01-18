package com.tech.mymovietvshows.Model.TvResponseModel;

public class CreatedBy {
    private String credit_id;
    private int gender;
    private Integer id;
    private String name;
    private String profile_path;

    public CreatedBy() {
    }

    public CreatedBy(String credit_id, int gender, Integer id, String name, String profile_path) {
        this.credit_id = credit_id;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        //create a baseUrl for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl+profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
