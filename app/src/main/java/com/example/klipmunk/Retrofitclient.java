package com.example.klipmunk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitclient {


    public static Retrofit retrofit = null;


    public static Retrofit getClient(String url) {

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


            return retrofit;
    }
}
