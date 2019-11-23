package com.example.retrofit.Interfaz;

import com.example.retrofit.modelo.Respuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Peticion {


    //restaurant pagetoken

    //String lugar = "";

    @GET("maps/api/place/nearbysearch/json?")

    Call<Respuesta> getPost(
            @Query("location") String local,
            @Query("radius") int radio,
            @Query("type") String tipo,
            @Query("key") String key,
            @Query("pagetoken") String paget);

   /* @GET("maps/api/place/nearbysearch/json?")

    Call<Respuesta> getPost2(
            @Query("location") String local,
            @Query("radius") int radio,
            @Query("type") String tipo,
            @Query("key") String key,
            @Query("pagetoken") String paget);*/








}