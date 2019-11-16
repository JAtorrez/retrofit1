package com.example.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.Interfaz.Jsonapi;
import com.example.retrofit.modelo.Respuesta;
import com.example.retrofit.modelo.Result;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// probando el git
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtsalida;
    private Respuesta respuesta;
    private Button btn;
    private String content = "", key ="AIzaSyATEjX-mkhIyxKki7QZpjLX7UUMiQZUWWg" , local = "20.127422, -98.731714";
    private int ban=0, next = 1, radio = 1500;
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

    public void getData(String tipo){
        Retrofit retrofit = new  Retrofit.Builder().baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Jsonapi jsonapi = retrofit.create(Jsonapi.class);

        Call<Respuesta> call = jsonapi.getPost(local,radio, tipo, key);
        call.enqueue(new Callback<Respuesta>() {

            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

                if(!response.isSuccessful()){
                    txtsalida.setText("Codigo: "+response.code());
                    btn.setEnabled(true);

                } else{
                    respuesta = response.body();
                    for(Result result: respuesta.getResults() ){
                        lista.add(result);
                        /*String content = "";
                        content += "Name: "+ result.getName()+"\n";
                        content += "Rating: "+ result.getRating()+"\n";

                        txtsalida.append(content);*/
                    }
                    ban = 1;
                    //next = 1;


                }
                btn.setEnabled(true);

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                txtsalida.setText(t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View v) {
        String lugar="";
        if(ban == 0){
            lista.clear();
            btn.setEnabled(false);
            if (c1.isChecked()  ){
                lugar = "museum";
                getData(lugar);
            }
            if (c2.isChecked() ){
                lugar = "restaurant";
                getData(lugar);
            }
            if (c3.isChecked()){
                lugar = "park";
                getData(lugar);
            }
            if (c4.isChecked() ){
                lugar = "school";
                getData(lugar);
            }else{
                txtsalida.setText("Selecciona un lugar.");
                btn.setEnabled(true);
            }
        }else{
             //txtsalida.append(lista.get(19).getName());
            imprimir();
            ban = 0;
        }


    }


    public void imprimir (){
        String resultados = "";
        for (int i = 0; i < lista.size(); i++)
            if(i + 1 < lista.size())
                resultados += lista.get(i).getName() + " | \n ";
            else
                resultados += lista.get(i).getName();

        txtsalida.setText(resultados);
    }

}
