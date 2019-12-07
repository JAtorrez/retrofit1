package com.example.retrofit.modelo.directions;

import java.util.List;

public class Waypoint {

    private double distance;
    private String name;
    private List<Double> location = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Waypoint() {
    }

    /**
     *
     * @param distance
     * @param name
     * @param location
     */
    public Waypoint(double distance, String name, List<Double> location) {
        super();
        this.distance = distance;
        this.name = name;
        this.location = location;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

}
