package com.example.retrofit.modelo.directions;

import java.util.List;

public class Route {

    private String weightName;
    private List<Leg> legs = null;
    private String geometry;
    private double distance;
    private int duration;
    private int weight;

    /**
     * No args constructor for use in serialization
     *
     */
    public Route() {
    }

    /**
     *
     * @param duration
     * @param weightName
     * @param distance
     * @param legs
     * @param weight
     * @param geometry
     */
    public Route(String weightName, List<Leg> legs, String geometry, double distance, int duration, int weight) {
        super();
        this.weightName = weightName;
        this.legs = legs;
        this.geometry = geometry;
        this.distance = distance;
        this.duration = duration;
        this.weight = weight;
    }

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
