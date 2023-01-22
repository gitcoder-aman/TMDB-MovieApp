package com.tech.mymovietvshows.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = MovieTV.class,exportSchema = false,version = 1)

public abstract class DatabaseHelper extends RoomDatabase {

    private static final String DB_NAME = "favorite";
    private static DatabaseHelper instanse;

    public static synchronized DatabaseHelper getDB(Context context){  //synchronized --> first come first out. not given multiple access at a time.

        if(instanse == null){
            instanse = Room.databaseBuilder(context,DatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instanse;
    }

    public abstract MovieTVDAO movieTVDAO();
}
