package com.example.retrofit.modelo;

import java.util.List;

public class Respuesta {


    private List<Result> results = null;


    /**
     *
     * @param results
     */
    public Respuesta(List<Result> results) {
        super();
        this.results = results;
    }

    public Respuesta() {

    }


    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


}
