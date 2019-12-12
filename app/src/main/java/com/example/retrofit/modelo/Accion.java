package com.example.retrofit.modelo;

import android.util.Log;

import com.example.retrofit.Interfaz.Peticion;
import com.example.retrofit.modelo.directions.DirectionsR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;

public class Accion {
    private ArrayList<Result> lista = new ArrayList<>();
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
        });

    }

    //Mapbox

    public void getDirection(int i, String local) {

        int destino = i;

        String local1= local;
        String local2 = coordenadas(destino);
        DirectionsR res = null;
        Call<DirectionsR> callDir = servicem.getPostdirection(local1, local2, keyapim);
        try {
            res = callDir.execute().body();
            lista.get(destino).setTiempo(res.getRoutes().get(0).getDuration());
            lista.get(destino).setTiempototal(tiempototal(destino));
            Log.d("Duracion", ""+lista.get(destino).getName()+lista.get(destino).getTiempo());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int tiempototal(int i){

        int total = 0;
        int tiempo = 0;
            String tipo = lista.get(i).getTypes().get(0);
            switch (tipo){
                case "museum":
                    tiempo = 5400;
                    total = (lista.get(i).getTiempo()*2)+tiempo;
                    break;
                case "park":
                    tiempo = 18000;
                    total = (lista.get(i).getTiempo()*2)+tiempo;
                    break;
                case "restaurant":
                    tiempo = 10800;
                    total = (lista.get(i).getTiempo()*2)+tiempo;
                    break;
                case "school":
                    tiempo = 2400;
                    total = (lista.get(i).getTiempo()*2)+tiempo;
                    break;
            }



        return total;

    }

    public void getduration(String local){

         int a = lista.size()-1;

         for(int i = 0;i<=a; i++ ){
                 getDirection(i, local);
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
        for (int i = 0; i < lista.size(); i++) {
                resultados += id + ".-  a "+ lista.get(i).getName() +": "+ Ctiempo(lista.get(i).getTiempototal())+ " Min. | \n ";
            id++;
        }
        return resultados;

    }

    public double Ctiempo(double tiempo){
        return tiempo/60 ;
    }

    //Generales

    public String imprimir (){

        String resultados = "";
        id = 1;
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setTiempototal(tiempototal(i));
            if (i + 1 < lista.size()) {
                resultados += id + " " + lista.get(i).getName() + lista.get(i).getTiempototal()+ " | \n ";
            }
            else{
                resultados += id + " " + lista.get(i).getName() + lista.get(i).getTiempototal();
            }
            id++;
        }
        return resultados;

    }

    public String itinerario(){
        String resultados = "";
        String res = "";
        int capacidad_mochila = 172800;


        Ag ag = new Ag(capacidad_mochila, lista, lista.size());

        res = ag.ejecutar();
        Log.d("in", res);
        for (int i = 0; i <lista.size();i++){
            if(res.charAt(i) == '1'){
                resultados += lista.get(i).getName()+" "+(lista.get(i).getTiempototal())/60+"\n";
            }
        }
        return resultados;

    }


}
