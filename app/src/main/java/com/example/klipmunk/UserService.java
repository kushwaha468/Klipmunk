package com.example.klipmunk;



import com.google.gson.JsonObject;



import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface UserService {

    @POST("/login")
    Call<JsonObject>login(@Body HashMap params);

    @Headers("Content-Type: application/json")
    @POST("/register")
    Call<JsonObject>signup(@Body HashMap params);


}
