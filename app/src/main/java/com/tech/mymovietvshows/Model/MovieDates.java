package com.tech.mymovietvshows.Model;

public class MovieDates {

    private String maximum;
    private String minimum;

    public MovieDates() {
    }

    public MovieDates(String maximum, String minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }
}
