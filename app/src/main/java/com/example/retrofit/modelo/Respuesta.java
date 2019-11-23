package com.example.retrofit.modelo;

import java.util.List;

public class Respuesta {

    private String next_page_token;
    private List<Result> results = null;


    /**
     * No args constructor for use in serialization
     *
     */
    public Respuesta() {
    }

    /**
     *
     * @param next_page_token
     * @param results
     */
    public Respuesta(String next_page_token, List<Result> results) {
        super();
        this.next_page_token = next_page_token;
        this.results = results;
    }


    public String getNextPageToken() {
        return next_page_token;
    }

    public void setNextPageToken(String nextpagetoken) {
        this.next_page_token = next_page_token;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


}

