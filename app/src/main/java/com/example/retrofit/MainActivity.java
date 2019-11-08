package com.example.retrofit;

import android.graphics.text.LineBreaker;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtsalida;
    private Respuesta respuesta;
    private Button btn;
    private CheckBox c1, c2 , c3, c4;
    private String content = "";
    private ArrayList<Result> lista = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtsalida = findViewById(R.id.salida);
        btn = findViewById(R.id.btnpeticion);
        btn.setOnClickListener(this);
        c1 = findViewById(R.id.cbmuseo);
        c2 = findViewById(R.id.cbpark);
        c3 = findViewById(R.id.cbrest);
        c4 = findViewById(R.id.cbschool);
    }

    private void getdata(){
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
                    return;
                }
                respuesta = response.body();
                int i = 1;

                for(Result result: respuesta.getResults()){
                    content = "";
                    content += i + result.getName()+", ";
                   // content += "Rating: "+ result.getRating()+"\n";
                   // lista.add(result.getName());

                    txtsalida.append(content);
                    i++;
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

        switch(v.getId()) {

            case R.id.btnpeticion:
                btn.setEnabled(false);
                content = "";
                txtsalida.setText(content);
                if (c1.isChecked()){
                    String tipo = "museum";
                    getdata();
                    txtsalida.append(lista.get(1));
                }
                if (c2.isChecked()){
                    String tipo = "park";
                    getdata();
                }
                if (c3.isChecked()){

                    String tipo = "restaurant";
                    getdata();

                }
                if (c4.isChecked()){

                    String tipo = "school";
                    getdata();

                }else if(!c1.isChecked() && !c2.isChecked() && !c3.isChecked() && !c4.isChecked()  ) {
                    content = "Por favor \n seleccione una opcion.";
                    txtsalida.setText(content);
                    btn.setEnabled(true);
                    content = "";

                }



                break;
            default:

                break;
        }


    }

}
