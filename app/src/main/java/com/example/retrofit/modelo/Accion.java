package com.example.retrofit.modelo;

import android.util.Log;

import com.example.retrofit.Interfaz.Peticion;
import com.example.retrofit.modelo.directions.DirectionsR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;

public class Accion {
    private List<Result> lista = new ArrayList<>();
    private List<Ruta> rutas = new ArrayList<>();

    private Peticion serviceg = ServiceGeneratorGoogle.createService(Peticion.class);
    private Peticion servicem = ServiceGeneratorMapbox.createService(Peticion.class);
    private String keyapig="AIzaSyATEjX-mkhIyxKki7QZpjLX7UUMiQZUWWg";
    private String keyapim="pk.eyJ1IjoianRvcnJleiIsImEiOiJjazN2ejkxdmUwbjBrM2h0Nnh0eWNydXlyIn0.sk5MLHZHDXTi3PkzEuIGCQ";
    private int   id=1;

    //Google
    public void getData(String local, int radio, String type, String next) {

        Respuesta respuesta = null;
        Call<Respuesta> callSync = serviceg.getPost(local, radio, type, keyapig, next);
        try {
            respuesta = callSync.execute().body();
            next = respuesta.getNextPageToken();
            while (respuesta.getResults().isEmpty()){
                respuesta = callSync.clone().execute().body();
            }
            for (Result result : respuesta.getResults()) {
                if (result.getRating() != null) {
                    lista.add(result);
                    Log.d("lugar", "" + respuesta.getResults().get(1).getName());
                }

            }
            if (next != null) {
                getData(local, radio, type, next);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ordenar(){

        Collections.sort(lista, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                return new Double(o2.getRating()).compareTo(new Double(o1.getRating()));
            }
        })

    }

    //Mapbox

    public void getDirection(int i) {

        int inicio = i;
        int destino = i+1;

        String local1=coordenadas(inicio);
        String local2 = coordenadas(destino);
        DirectionsR res = null;
        Call<DirectionsR> callDir = servicem.getPostdirection(local1, local2, keyapim);
        try {
            res = callDir.execute().body();
            Ruta objruta = new Ruta(lista.get(inicio).getName(), lista.get(destino).getName(), res.getRoutes().get(0).getDuration());
            rutas.add(objruta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getduration(){

         int a = lista.size()-1;

         for(int i = 0;i<a; i++ ){
                 getDirection(i);
         }
    }

    public String coordenadas(int i){

        Double lng=0.0;
        Double lat=0.0;
        String coord="";

        lng = lista.get(i).getGeometry().getLocation().getLng();
        lat = lista.get(i).getGeometry().getLocation().getLat();

        coord = lng.toString()+","+lat.toString();
        Log.d("Coord", coord+"de: "+lista.get(i).getName() );

        return coord;

    }

    public String imprimirRutas (){

        String resultados = "";
        id = 1;
        for (int i = 0; i < rutas.size(); i++) {
            if (i + 1 < rutas.size()) {
                resultados += id + ".- De " + rutas.get(i).getInicio() +" a "+ rutas.get(i).getDestino() +": "+ Ctiempo(rutas.get(i).getDuration())+ " Min. | \n ";
            }
            id++;
        }
        return resultados;

    }

    public double Ctiempo(int tiempo){
        return tiempo/60 ;
    }

    //Generales

    public String imprimir (){

        String resultados = "";
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
        return resultados;

    }


}
