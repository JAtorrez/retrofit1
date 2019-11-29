package com.example.retrofit.modelo;

import android.util.Log;

import com.example.retrofit.Interfaz.Peticion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;

public class Accion {
    private List<Result> lista = new ArrayList<>();
    private Peticion service = ServiceGenerator.createService(Peticion.class);
    private String keyapi="AIzaSyATEjX-mkhIyxKki7QZpjLX7UUMiQZUWWg";
    private int   id=1;

    public void getData(String local, int radio, String type, String next) {

        Respuesta respuesta = null;
      //  Nextpage nextpagetoken = null;
        Call<Respuesta> callSync = service.getPost2(local, radio, type, keyapi, next);
        //Call<Nextpage> callnextpage = service.getnext2(local, radio, type, keyapi, next);
        try {
            respuesta = callSync.execute().body();
           // nextpagetoken = callnextpage.execute().body();
            next = respuesta.getNextPageToken();
            for (Result result : respuesta.getResults()) {
                if (result.getRating() != null) {
                    lista.add(result);
                    Log.d("lugar", "" + respuesta.getResults().get(1).getName());
                }

            }
            if (next != null) {
                Log.d("token3", "" + next);
                respuesta = null;

                Log.d("token1", next);
                try {
                    respuesta = callSync.clone().execute().body();
                    //nextpagetoken = callnextpage.execute().body();
                    next = respuesta.getNextPageToken();
                    Log.d("token2", "" + next);
                    for (Result result : respuesta.getResults()) {
                        if (result.getRating() != null) {
                            lista.add(result);
                            Log.d("lugar", "" + respuesta.getResults().get(1).getName());
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String imprimir (){

        for (int i=0; i< lista.size(); i++){
            Log.d("Lugar :" ,id +" "+ lista.get(i).getName() +" :"+ lista.get(i).getRating() );
            id++;
        }


        String resultados = "";
        ordenar();
        id = 1;
        for (int i = 0; i < lista.size(); i++) {
            if (i + 1 < lista.size()) {
                resultados += id + " " + lista.get(i).getName() + lista.get(i).getRating() + " | \n ";
            }
            else{
                resultados += id + " " + lista.get(i).getName() + lista.get(i).getRating();
            }
            id++;
        }
        lista.clear();
        return resultados;

    }

    public void ordenar(){

        Collections.sort(lista, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                return new Double(o2.getRating()).compareTo(new Double(o1.getRating()));
            }
        });

    }


}
