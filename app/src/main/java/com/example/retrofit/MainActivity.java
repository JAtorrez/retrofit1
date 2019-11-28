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
    private String content = "" , local = "20.127422, -98.731714", next = "";
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
        String lugar = "";
        id = 1;
        if(ban == 0){
            btn.setEnabled(false);
            if (c1.isChecked()  ){
                lugar = "museum";
               // getData(lugar, next);
                Tarea tarea = new Tarea(lugar);
                tarea.execute();
            }
            if (c2.isChecked() ){
                lugar = "restaurant";
                Tarea tarea = new Tarea(lugar);
                tarea.execute();
            }
            if (c3.isChecked()){
                lugar = "park";
                Tarea tarea = new Tarea(lugar);
                tarea.execute();
            }
            if (c4.isChecked() ){
                lugar = "school";
                Tarea tarea = new Tarea(lugar);
                tarea.execute();
            }else if (!c1.isChecked() && !c2.isChecked() && !c3.isChecked() && !c4.isChecked()){
                txtsalida.setText("Selecciona un lugar.");
                btn.setEnabled(true);
            }
        }else {
            txtsalida.setText(accion.imprimir());
            ban = 0;
        }


    }

    private class Tarea extends AsyncTask<Void, Integer, Boolean> {

        private String tipo;

        public Tarea(String tipo) {
            this.tipo = tipo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String next = "";
            accion.getData(local,radio, tipo, next);
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
                Toast.makeText(getBaseContext(), "peticion finalizada en AsyncTask", Toast.LENGTH_SHORT).show();
                ban = 1;
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
