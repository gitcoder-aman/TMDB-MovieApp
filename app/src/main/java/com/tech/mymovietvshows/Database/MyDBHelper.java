package com.tech.mymovietvshows.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        String query = "create table favorite(id integer primary key , posterImage text , rating real , movieName text , releaseDate text )";
        String query = "create table favorite( posterImage text , movieName text )";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists favorite");
        onCreate(sqLiteDatabase);
    }

    //    public boolean insert_data(int id, String posterImage, float rating, String movieName, String releaseDate){
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put("movie_Id",id);
//        values.put("posterImage",posterImage);
//        values.put("Rating",rating);
//        values.put("Movie Name",movieName);
//        values.put("Release Date",releaseDate);
//
//        long r = database.insert("favorite",null,values);
//        if(r == -1){
//            return false;
//        }else{
//            return true;
//        }
//    }
    public boolean insert_data(String posterImage, String movieName) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("posterImage", posterImage);
        values.put("Movie Name", movieName);

        long r = database.insert("favorite", null, values);
        if (r == -1) {
            return false;
        } else {
            return true;
        }
    }
}
