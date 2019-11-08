package com.example.retrofit.Interfaz;

import com.example.retrofit.modelo.Respuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Jsonapi {



    @GET("maps/api/place/nearbysearch/json?location=20.127422, -98.731714&radius=1500&type=restaurant&key=AIzaSyATEjX-mkhIyxKki7QZpjLX7UUMiQZUWWg")

    Call<Respuesta> getPost();


}
