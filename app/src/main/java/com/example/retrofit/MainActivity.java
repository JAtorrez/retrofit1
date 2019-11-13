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


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtsalida;
    private Respuesta respuesta;
    private Button btn;
    private String content = "";
    private int ban=0;
    private List<Result> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtsalida = findViewById(R.id.salida);
        btn = findViewById(R.id.btnpeticion);
        btn.setOnClickListener(this);
    }

    public void getData(){
        Retrofit retrofit = new  Retrofit.Builder().baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Jsonapi jsonapi = retrofit.create(Jsonapi.class);

        Call<Respuesta> call = jsonapi.getPost();
        call.enqueue(new Callback<Respuesta>() {

            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

                if(!response.isSuccessful()){
                    txtsalida.setText("Codigo: "+response.code());
                    btn.setEnabled(true);

                    return;
                } else{
                    respuesta = response.body();
                    for(Result result: respuesta.getResults() ){
                        lista.add(result);
                    }
                    ban = 1;


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
        if(ban == 0){
            btn.setEnabled(false);
            getData();
        }else{
            txtsalida.append(lista.get(19).getName());

        }


    }

}
