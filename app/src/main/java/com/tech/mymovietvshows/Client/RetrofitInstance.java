package com.tech.mymovietvshows.Client;

import com.tech.mymovietvshows.Interface.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static RetrofitInstance instance;
    public ApiInterface apiInterface;

    private final static String BASE_URL = "https://api.themoviedb.org/3/";

    public RetrofitInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       apiInterface =  retrofit.create(ApiInterface.class);
    }

    public static RetrofitInstance getInstance() {

        if(instance == null){
            instance = new RetrofitInstance();
        }
        return instance;
    }

}
