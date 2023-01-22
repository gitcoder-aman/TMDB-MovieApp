package com.tech.mymovietvshows.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Data access object
@Dao
public interface MovieTVDAO {

    @Query(" select * from favorite ")
    List<MovieTV>getAllMovieTV();

    @Insert
    void addTx(MovieTV movieTV);

    @Delete
    void deleteTx(MovieTV movieTV);

}
