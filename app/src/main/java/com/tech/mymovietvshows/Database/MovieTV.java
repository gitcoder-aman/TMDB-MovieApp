package com.tech.mymovietvshows.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "favorite")
public class MovieTV {
    @PrimaryKey
    @ColumnInfo(name = "movieTvID")
    private int id;

    @ColumnInfo(name = "posterImage")
    private String posterImage;

    @ColumnInfo(name = "rating")
    private float rating;

    @ColumnInfo(name = "MovieName")
    private String movieName;

    @ColumnInfo(name = "releaseDate")
    private String releaseDate;


    //main Constructor
    public MovieTV(int id, String posterImage, float rating, String movieName, String releaseDate) {
        this.id = id;
        this.posterImage = posterImage;
        this.rating = rating;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
    }

//    @Ignore
//    MovieTV(String title, String amount){  // for inserting data no require id
//        this.title = title;
//        this.amount = amount;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
