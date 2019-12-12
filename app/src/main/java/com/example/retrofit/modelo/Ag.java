package com.example.retrofit.modelo;

import java.util.ArrayList;
import java.util.Random;

public class Ag {

    /*
    Definición de las constantes del algoritmo genetico
     */
    static final int NUM_POBLACION = 100;
    static final int CICLOS = 500;
    static final double PROBABILIDAD_CRUCE = 0.65;
    static final double PROBABILIDAD_MUTACION = 0.01;

    /*
        Definición de las variables
     */

    private ArrayList<Result> lugares;
    private int TAM_INDIVIDUO;
    private double capacity;
    private double[] profits;
    private double[] weights;

    private String[] individuos;
    private double[] x;
    private double[] fx;

    private String mejorIndividuo;
    private double mejorGanancia;
    private double mejorPeso;

    private String padre1;
    private String padre2;

    private int indicePadre1;
    private int indicePadre2;

    private int auxIndice1;
    private int auxIndice2;

    private String hijo1;
    private String hijo2;

    private double xHijo1;
    private double xHijo2;

    private double fxHijo1;
    private double fxHijo2;

    private String[] poblacionIntermedia;
    private double[] xIntermedio;
    private double[] fxIntermedio;


    public Ag(double _capacity, ArrayList<Result> _lugares, int num_elementos) {
        //api = new API();
        //lugares = lugares_lista;
        capacity = _capacity;

        profits = new double[num_elementos];
        weights = new double[num_elementos];

        lugares = _lugares;

        individuos = new String[NUM_POBLACION];

        TAM_INDIVIDUO = lugares.size();
    }

    public String ejecutar() {

        capturarDatos();

/*        Log.d("AG", "PESOS");
        for (int i = 0; i < TAM_INDIVIDUO; i++) {
            Log.d("PESO", Double.toString(weights[i]));
            //Log.d("AG", "PROFIT " + i + ":  " + Double.toString(profits[i]));
        }
        Log.d("AG", "GANANCIAS");
        for (int i = 0; i < TAM_INDIVIDUO; i++) {
            Log.d("GANANCIA", Double.toString(profits[i]));
        }*/

        inicializacion();

        evaluacionTorneoPoblacion();

        /*Log.d("AG", "POBLACIÓN INICIAL");
        for (int i = 0; i < NUM_POBLACION; i++) {
            Log.d("AG", "INDIVIDUO " + i + ":  " + individuos[i]);
            Log.d("AG", "P IND " + i + ":  " + x[i]);
            Log.d("AG", "G IND " + i + ":  " + fx[i]);
        }*/

        for (int i = 0; i < CICLOS; i++) {

            inicializarValoresTorneo();

            seleccionTorneo();

            cruceTorneo();

            mutacionRuleta();

            evaluacionTorneoPoblacionIntermedia();

            reemplazoTorneo();

            /*if (i == CICLOS-1){
                Log.d("AG", "POBLACIÓN CICLO: " + i);
                for (int j = 0; j < NUM_POBLACION; j++) {
                    Log.d("AG", "INDIVIDUO " + j + ":  " + individuos[j]);
                    Log.d("AG", "P IND " + j + ":  " + x[j]);
                    Log.d("AG", "G IND " + j + ":  " + fx[j]);
                }
            }*/
        }

        obtenerMejorIndividuo(fx.length);


        return mejorIndividuo;

    }


    /*
        Metodo para reiniciar valores
     */

    private void capturarDatos() {

        for (int i = 0; i < TAM_INDIVIDUO; i++) {
            profits[i] = lugares.get(i).getRating();
            weights[i] = lugares.get(i).getTiempototal();
        }
    }

    private void inicializacion() {
        Random rnd = new Random();
        String individuo_aux;

        for (int i = 0; i < NUM_POBLACION; i++) {
            individuo_aux = "";

            for (int j = 0; j < TAM_INDIVIDUO; j++) {
                int aleatorio = rnd.nextInt(2);
                individuo_aux += Integer.toString(aleatorio);
            }

            individuos[i] = individuo_aux;
        }
    }

    private void inicializarValoresTorneo() {
        padre1 = "";
        padre2 = "";

        indicePadre1 = 0;
        indicePadre2 = 0;

        auxIndice1 = 0;
        auxIndice2 = 0;

        hijo1 = "";
        hijo2 = "";

        xHijo1 = 0;
        xHijo2 = 0;

        fxHijo1 = 0;
        fxHijo2 = 0;

        poblacionIntermedia = new String[4];
        xIntermedio = new double[4];
        fxIntermedio = new double[4];
    }

    private void evaluacionTorneoPoblacion() {
        Random rnd = new Random();
        Boolean knapsack_overfield = false;

        for (int i = 0; i < NUM_POBLACION; i++) {

            if (obtenerPeso(i) > capacity)
                knapsack_overfield = true;

            while (knapsack_overfield) {
                int j = rnd.nextInt(TAM_INDIVIDUO);
                individuos[i] = individuos[i].substring(0, j) + "0" + individuos[i].substring(j + 1);

                if (obtenerPeso(i) < capacity) {
                    knapsack_overfield = false;
                }
            }
        }

        x = new double[NUM_POBLACION];
        fx = new double[NUM_POBLACION];

        for (int i = 0; i < NUM_POBLACION; i++) {
            x[i] = obtenerPeso(i);
            fx[i] = funcionObjetivo(i);
        }
    }

    private double funcionObjetivo(int _individuo) {
        double ganancia = 0;

        for (int i = 0; i < TAM_INDIVIDUO; i++) {
            ganancia += (Integer.parseInt(individuos[_individuo].substring(i, i + 1)) * profits[i]);
        }

        return ganancia;
    }

    private double obtenerPeso(int _individuo) {
        double peso = 0;

        for (int i = 0; i < TAM_INDIVIDUO; i++) {
            peso += (Integer.parseInt(individuos[_individuo].substring(i, i + 1)) * weights[i]);
        }

        return peso;
    }

    private void seleccionTorneo() {
        Random rnd = new Random();

        //Seleccion Padre 1
        auxIndice1 = rnd.nextInt(NUM_POBLACION);
        auxIndice2 = rnd.nextInt(NUM_POBLACION);

        while (auxIndice1 == auxIndice2 || fx[auxIndice1] == fx[auxIndice2]) {
            auxIndice2 = rnd.nextInt(NUM_POBLACION);
        }

        if (fx[auxIndice1] > fx[auxIndice2]) {
            padre1 = individuos[auxIndice1];
            indicePadre1 = auxIndice1;
        } else if (fx[auxIndice1] < fx[auxIndice2]) {
            padre1 = individuos[auxIndice2];
            indicePadre1 = auxIndice2;
        }

        //Seleccion Padre 2
        auxIndice1 = rnd.nextInt(NUM_POBLACION);
        auxIndice2 = rnd.nextInt(NUM_POBLACION);

        while (auxIndice1 == auxIndice2 || fx[auxIndice1] == fx[auxIndice2]) {
            auxIndice2 = rnd.nextInt(NUM_POBLACION);
        }

        if (fx[auxIndice1] > fx[auxIndice2]) {

            padre2 = individuos[auxIndice1];
            indicePadre2 = auxIndice1;
        } else if (fx[auxIndice1] < fx[auxIndice2]) {
            padre2 = individuos[auxIndice2];
            indicePadre2 = auxIndice2;
        }
    }

    private String seleccionarPadre(int indice) {
        Random rnd = new Random();
        int auxIndice1 = rnd.nextInt(NUM_POBLACION);
        int auxIndice2 = rnd.nextInt(NUM_POBLACION);

        while (auxIndice1 == auxIndice2) {
            auxIndice2 = rnd.nextInt(NUM_POBLACION);
        }

        String padreSeleccionado = "";

        if (fx[auxIndice1] > fx[auxIndice2]) {

            padreSeleccionado = individuos[auxIndice1];
            if (indice == 1) {
                indicePadre1 = auxIndice1;
            }

            if (indice == 2) {
                indicePadre2 = auxIndice1;
            }
        } else if (fx[auxIndice1] < fx[auxIndice2]) {
            padreSeleccionado = individuos[auxIndice2];
            if (indice == 1) {
                indicePadre1 = auxIndice2;
            }

            if (indice == 2) {
                indicePadre2 = auxIndice2;
            }
        }

        return padreSeleccionado;
    }

    public void cruceTorneo() {
        Random rnd = new Random();
        hijo1 = padre1;
        hijo2 = padre2;

        String cortePadre1;
        String cortePadre2;
        String padre1_corte1;
        String padre1_corte2;
        String padre2_corte1;
        String padre2_corte2;

        double auxAleatorio = rnd.nextDouble();

        if (auxAleatorio < PROBABILIDAD_CRUCE) {

            int puntoCorte_1 = rnd.nextInt(lugares.size());
            int puntoCorte_2 = rnd.nextInt(lugares.size());

            while (puntoCorte_1 == puntoCorte_2) {
                puntoCorte_2 = rnd.nextInt(lugares.size());
            }

            if (puntoCorte_1 < puntoCorte_2) {
                cortePadre1 = padre1.substring(puntoCorte_1, puntoCorte_2 + 1);
                cortePadre2 = padre2.substring(puntoCorte_1, puntoCorte_2 + 1);

                hijo1 = padre1.substring(0, puntoCorte_1) + cortePadre2 + padre1.substring(puntoCorte_2 + 1);
                hijo2 = padre2.substring(0, puntoCorte_1) + cortePadre1 + padre2.substring(puntoCorte_2 + 1);
            } else if (puntoCorte_1 > puntoCorte_2) {
                padre1_corte1 = padre1.substring(0, puntoCorte_2 + 1);
                padre1_corte2 = padre1.substring(puntoCorte_1);
                padre2_corte1 = padre2.substring(0, puntoCorte_2 + 1);
                padre2_corte2 = padre2.substring(puntoCorte_1);

                hijo1 = padre2_corte1 + padre1.substring(puntoCorte_2 + 1, puntoCorte_2 + 1 + puntoCorte_1 - puntoCorte_2 - 1) + padre2_corte2;
                hijo2 = padre1_corte1 + padre1.substring(puntoCorte_2 + 1, puntoCorte_2 + 1 + puntoCorte_1 - puntoCorte_2 - 1) + padre1_corte2;
            }
        } else {
            hijo1 = padre1;
            hijo2 = padre2;
        }
    }

    private void mutacionRuleta() {
        Random rnd = new Random();

        for (int j = 0; j < TAM_INDIVIDUO; j++) {
            double auxAleatorio = rnd.nextDouble();

            if (auxAleatorio < PROBABILIDAD_MUTACION) {
                if (hijo1.charAt(j) == '0') {
                    hijo1 = hijo1.substring(0, j) + '1' + hijo1.substring(j + 1);
                } else if (hijo1.charAt(j) == '1') {
                    hijo1 = hijo1.substring(0, j) + '0' + hijo1.substring(j + 1);
                }
            }
        }

        for (int j = 0; j < TAM_INDIVIDUO; j++) {
            double auxAleatorio = rnd.nextDouble();

            if (auxAleatorio < PROBABILIDAD_MUTACION) {
                if (hijo2.charAt(j) == '0') {
                    hijo2 = hijo2.substring(0, j) + '1' + hijo2.substring(j + 1);
                } else if (hijo2.charAt(j) == '1') {
                    hijo2 = hijo2.substring(0, j) + '0' + hijo2.substring(j + 1);
                }
            }
        }
    }

    private void evaluacionTorneoPoblacionIntermedia() {
//        evaluacionHijos(hijo1, xHijo1, fxHijo1);
//        evaluacionHijos(hijo2, xHijo2, fxHijo2);

        Random rnd = new Random();
        Boolean knapsack_overfield = false;

        if (obtenerPeso(hijo1) > capacity)
            knapsack_overfield = true;

        while (knapsack_overfield) {
            int j = rnd.nextInt(TAM_INDIVIDUO);
            hijo1 = hijo1.substring(0, j) + "0" + hijo1.substring(j + 1);

            if (obtenerPeso(hijo1) < capacity) {
                knapsack_overfield = false;
            }
        }

        xHijo1 = obtenerPeso(hijo1);
        fxHijo1 = funcionObjetivo(hijo1);

        if (obtenerPeso(hijo2) > capacity)
            knapsack_overfield = true;

        while (knapsack_overfield) {
            int j = rnd.nextInt(TAM_INDIVIDUO);
            hijo2 = hijo2.substring(0, j) + "0" + hijo2.substring(j + 1);

            if (obtenerPeso(hijo2) < capacity) {
                knapsack_overfield = false;
            }
        }

        xHijo2 = obtenerPeso(hijo2);
        fxHijo2 = funcionObjetivo(hijo2);
    }

    private void evaluacionHijos(String hijo, double xHijo, double fxHijo) {
        Random rnd = new Random();
        Boolean knapsack_overfield = false;

        if (obtenerPeso(hijo) > capacity)
            knapsack_overfield = true;

        while (knapsack_overfield) {
            int j = rnd.nextInt(TAM_INDIVIDUO);
            hijo = hijo.substring(0, j) + "0" + hijo.substring(j + 1);

            if (obtenerPeso(hijo) < capacity) {
                knapsack_overfield = false;
            }
        }

        xHijo = obtenerPeso(hijo);
        fxHijo = funcionObjetivo(hijo);
    }

    private double funcionObjetivo(String _individuo) {
        double ganancia = 0;

        for (int i = 0; i < TAM_INDIVIDUO; i++) {
            ganancia += (Integer.parseInt(String.valueOf(_individuo.charAt(i))) * profits[i]);//(Integer.parseInt(_individuo.substring(i, i + 1)) * profits[i]);
        }

        return ganancia;
    }

    private double obtenerPeso(String _individuo) {
        double peso = 0;

        for (int i = 0; i < TAM_INDIVIDUO; i++) {
            //double aux = Integer.parseInt(_individuo.substring(i, i + 1)) * weights[i];
            peso += (Integer.parseInt(String.valueOf(_individuo.charAt(i))) * weights[i]);;
        }

        return peso;
    }

    private void reemplazoTorneo() {
        poblacionIntermedia[0] = padre1;
        poblacionIntermedia[1] = padre2;
        poblacionIntermedia[2] = hijo1;
        poblacionIntermedia[3] = hijo2;

        xIntermedio[0] = x[indicePadre1];
        xIntermedio[1] = x[indicePadre2];
        xIntermedio[2] = xHijo1;
        xIntermedio[3] = xHijo2;

        fxIntermedio[0] = fx[indicePadre1];
        fxIntermedio[1] = fx[indicePadre2];
        fxIntermedio[2] = fxHijo1;
        fxIntermedio[3] = fxHijo2;

        ordenar(poblacionIntermedia, xIntermedio, fxIntermedio);

        individuos[indicePadre1] = poblacionIntermedia[3];
        x[indicePadre1] = xIntermedio[3];
        fx[indicePadre1] = fxIntermedio[3];

        int diferencia = 0;
        int auxIndice = 0;

        for (int i = 0; i < 3; i++){
            int auxDif = hammingDistance(poblacionIntermedia[3], poblacionIntermedia[i]);
            if (auxDif > diferencia){
                diferencia = auxDif;
                auxIndice = i;
            }
        }

        individuos[indicePadre2] = poblacionIntermedia[auxIndice];
        x[indicePadre2] = xIntermedio[auxIndice];
        fx[indicePadre2] = fxIntermedio[auxIndice];

//        individuos[indicePadre2] = poblacionIntermedia[2];
//        x[indicePadre2] = xIntermedio[2];
//        fx[indicePadre2] = fxIntermedio[2];
    }

    private void ordenar(String[] _individuos, double[] _x, double[] _fx) {
        String auxIndividuo = "";
        double auxX = 0;
        double auxFx = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                if (_fx[i] > _fx[j]) {
                    auxIndividuo = _individuos[i];
                    auxX = _x[i];
                    auxFx = _fx[i];

                    _individuos[i] = _individuos[j];
                    _x[i] = _x[j];
                    _fx[i] = _fx[j];

                    _individuos[j] = auxIndividuo;
                    _x[j] = auxX;
                    _fx[j] = auxFx;
                }
            }
        }
    }

    private int hammingDistance(String individuo1, String individuo2){
        int count = 0;
        for (int i = 0; i < TAM_INDIVIDUO; i++)
        {
            if (individuo1.charAt(i) != individuo2.charAt(i))
                count++;
        }
        return count;
    }

    private void obtenerMejorIndividuo(int n) {

        for (int i = 0; i < n; i++) {
            if (fx[i] > mejorGanancia) {
                mejorIndividuo = individuos[i];
                mejorGanancia = fx[i];
                mejorPeso = x[i];
            }
        }

    }


}
