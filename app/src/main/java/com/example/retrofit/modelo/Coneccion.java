package com.example.retrofit.modelo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Coneccion {

    public Retrofit getcoteccion(){
       Retrofit retrofit = new  Retrofit.Builder().baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       return retrofit;
    }



}
