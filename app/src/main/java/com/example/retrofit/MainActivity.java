package com.example.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.Interfaz.Peticion;
import com.example.retrofit.modelo.Coneccion;
import com.example.retrofit.modelo.Respuesta;
import com.example.retrofit.modelo.Result;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtsalida;

    private Button btn;
    private String content = "", key ="AIzaSyATEjX-mkhIyxKki7QZpjLX7UUMiQZUWWg" , local = "20.127422, -98.731714";
    private int ban=0, radio = 1500, id = 1;
    private List<Result> lista = new ArrayList<>();
    private CheckBox c1,c2,c3,c4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtsalida = findViewById(R.id.salida);
        btn = findViewById(R.id.btnpeticion);
        btn.setOnClickListener(this);
        c1 = findViewById(R.id.cbmuseum);
        c2 = findViewById(R.id.cbrest);
        c3 = findViewById(R.id.cbpark);
        c4 = findViewById(R.id.cbschool);


}


    public void getData(final String tipo, String token){
        final String[] nextp = {token};
        Retrofit retrofit = new Coneccion().getcoteccion();
        final Peticion peticion = retrofit.create(Peticion.class);

        Call<Respuesta> call = peticion.getPost(local,radio, tipo, key, nextp[0]);
        call.enqueue(new Callback<Respuesta>() {

            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                Respuesta respuesta = null;
                if(!response.isSuccessful()){
                    txtsalida.setText("Codigo: "+response.code());
                    btn.setEnabled(true);

                } else{

                    respuesta = response.body();
                    //
                    nextp[0] = respuesta.getNextPageToken();
                    for(Result result: respuesta.getResults() ){
                        lista.add(result);
                        Log.d("Lugar :" ,result.getName() + result.getRating());
                    }


                }
                if(respuesta.getNextPageToken() == null ){
                    ban = 1;
                    btn.setEnabled(true);

                }else{
                    Log.d("token " , nextp[0]);
                    getData(tipo, nextp[0]);
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                txtsalida.setText(t.getMessage());
            }
        });

    }


    @Override
    public void onClick(View v) {
        String lugar = "";
        String next = "";
        id = 1;
        if(ban == 0){
            lista.clear();
           // next = "CqQCHQEAADBUBZuRK2Stpmv1v-rzRntgC99uA9McqqEKor1YURcNXz5D6otBf6wbqgUdcB7jeQhYQQYJtJQG-PQucEzbgIJMBAKQ6EgPzTNzkmzLWwehxfhk6FaVFJAKlt7jI83zhdlCJc9oIUmvDc00fF7diKrOTLbDm-zdKqefq1ao9pCNk8k2Am6yu0_xp7b2Fe13qEGQEgvS11xZZ9hX_t2WWGeaD3msLTp7drQM6KiFqyPywNlxP0xMQODsMo2LCoiwGrrTJxpEJZ5ePbkYJ85Um3MBueja9ZLk_us7m-4WFAoJhZoWishFLE1dROrIMwvS73ye-7ItnmD7mfzoAprp3aKMbY15VBeFHGhJzeeFhuimzpq8-OmQLdip0Mv6sQrqBxIQqxDS1w4p8fo0zk9pqozv4RoUMC3QiqOL1eqSiOHdD_NBWMW1KgU";
            btn.setEnabled(false);
            if (c1.isChecked()  ){
                lugar = "museum";

                getData(lugar, next);
            }
            if (c2.isChecked() ){
                lugar = "restaurant";
                getData(lugar, next);
            }
            if (c3.isChecked()){
                lugar = "park";
                getData(lugar, next);
            }
            if (c4.isChecked() ){
                lugar = "school";
                getData(lugar, next);
            }else if (!c1.isChecked() && !c2.isChecked() && !c3.isChecked() && !c4.isChecked()){
                txtsalida.setText("Selecciona un lugar.");
                btn.setEnabled(true);
            }
        }else {
            imprimir();
            ban = 0;
            lista.clear();
        }


    }


    public void imprimir (){

        for (int i=0; i< lista.size(); i++){
            Log.d("Lugar :" ,id +" "+ lista.get(i).getName() +" :"+ lista.get(i).getRating() );
            id++;
        }


        String resultados = "";
        ordenar();
        id = 1;
        for (int i = 0; i < lista.size(); i++) {
            if (i + 1 < lista.size()) {
                resultados += id + " " + lista.get(i).getName() + lista.get(i).getRating() + lista.get(i).getGeometry().getLocation().getLat()+ " | \n ";
            }
            else{
                resultados += id + " " + lista.get(i).getName() + lista.get(i).getRating();
            }
            id++;
        }

        txtsalida.setText(resultados);
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
