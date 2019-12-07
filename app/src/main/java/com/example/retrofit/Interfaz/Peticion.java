package com.example.retrofit.Interfaz;

import com.example.retrofit.modelo.Respuesta;
import com.example.retrofit.modelo.directions.DirectionsR;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("directions/v5/mapbox/walking/{local1};{local2}?")

    Call<DirectionsR> getPostdirection(
            @Path("local1") String local1,
            @Path("local2") String local2,
            @Query("access_token") String paget);
}
