package com.example.retrofit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.modelo.Accion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtsalida;
    private Button btn;
    private String content = "" , local = "20.127422, -98.731714";
    private int ban=0, radio = 1500, id = 1;
    private CheckBox c1,c2,c3,c4;
    Accion accion = new Accion();


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
    @Override
    public void onClick(View v) {
        btn.setEnabled(false);

        switch(ban){
            case 0:
                btn.setText("Imprimir lugares");
                txtsalida.setText("");
                Tarea tarea = new Tarea();
                tarea.execute();
                break;
            case 1:
                txtsalida.setText(accion.imprimir());
                btn.setText("Obtener tiempos");
                ban = 2;
                btn.setEnabled(true);
                break;
            case 2:
                btn.setText("Imprimir tiempos");
                tarea = new Tarea();
                tarea.execute();
                break;
            case 3:
                txtsalida.setText(accion.imprimirRutas());
                ban = 0;
                btn.setText("Realizar peticion");
                btn.setEnabled(true);
                break;

        }
    }

    private class Tarea extends AsyncTask<Void, Integer, Boolean> {

        private boolean museum = false;
        private boolean rest = false;
        private boolean park = false;
        private boolean school = false;
        private String tipo="";
        private int t =0;

        public Tarea() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(ban == 0){
                if (c1.isChecked()  ){
                    museum = true;
                }
                if (c2.isChecked() ){
                    rest = true;
                }
                if (c3.isChecked()){
                    park = true;
                }
                if (c4.isChecked() ){
                    tipo = "school";
                    school = true;
                }
                t = 1;
            } else {
                t = 2;
            }


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String next = "";
            if(t == 1){

                if (museum == true){
                    tipo = "museum";
                    accion.getData(local,radio, tipo, next);
                }
                if (rest == true){
                    tipo = "restaurant";
                    accion.getData(local,radio, tipo, next);
                }
                if (park == true){
                    tipo = "park";
                    accion.getData(local,radio, tipo, next);
                }
                if (school == true){
                    tipo = "school";
                    accion.getData(local,radio, tipo, next);
                }
                ban = 1;
            }
            if(t == 2){
                ban = 3;
                accion.getduration();
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected void onPostExecute(Boolean resultado) {
            //super.onPostExecute(aVoid);
            if(resultado){
                Toast.makeText(getBaseContext(), "peticion finalizada", Toast.LENGTH_SHORT).show();
                btn.setEnabled(true);

            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(getBaseContext(), "Accion Larga Ha sido cancelada", Toast.LENGTH_SHORT).show();
        }


    }





}
