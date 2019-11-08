package com.example.retrofit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.Interfaz.Jsonapi;
import com.example.retrofit.modelo.Respuesta;
import com.example.retrofit.modelo.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private TextView mytexteview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mytexteview = findViewById(R.id.test);
        getPost();
    }

    private void getPost(){
        Retrofit retrofit = new  Retrofit.Builder().baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Jsonapi jsonapi = retrofit.create(Jsonapi.class);

        Call<Respuesta> call = jsonapi.getPost();
        call.enqueue(new Callback<Respuesta>() {

            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

                if(!response.isSuccessful()){
                    mytexteview.setText("Codigo: "+response.code());
                    return;
                }
                Respuesta respuesta = response.body();

                for(Result result: respuesta.getResults() ){
                    String content = "";
                    content += "Name: "+ result.getName()+"\n";
                    content += "Rating: "+ result.getRating()+"\n";

                    mytexteview.append(content);
               }

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                mytexteview.setText(t.getMessage());
            }
        });
    }
}
