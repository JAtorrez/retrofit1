package com.example.retrofit.modelo;

public class Result {

    //private String id;
    private String name;
    private double rating;

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param rating
     * @param name
     ////* @param id
     */
    public Result (String id, String name, double rating) {
        super();
        //this.id = id;
        this.name = name;
        this.rating = rating;

    }

   /* public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }




}