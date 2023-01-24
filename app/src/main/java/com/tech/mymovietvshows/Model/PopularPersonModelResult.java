package com.tech.mymovietvshows.Model;

public class PopularPersonModelResult {

    private int id;
    private String known_for_department;
    private String name;
    private String profile_path;

    public PopularPersonModelResult() {
    }

    public PopularPersonModelResult(int id, String known_for_department, String name, String profile_path) {
        this.id = id;
        this.known_for_department = known_for_department;
        this.name = name;
        this.profile_path = profile_path;
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

    public String getProfile_path() {

        //create a baseUrl for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl+profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
