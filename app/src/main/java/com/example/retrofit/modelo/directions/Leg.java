package com.example.retrofit.modelo.directions;

import java.util.List;

public class Leg {

    private String summary;
    private List<Object> steps = null;
    private double distance;
    private int duration;
    private int weight;

    /**
     * No args constructor for use in serialization
     *
     */
    public Leg() {
    }

    /**
     *
     * @param summary
     * @param duration
     * @param distance
     * @param weight
     * @param steps
     */
    public Leg(String summary, List<Object> steps, double distance, int duration, int weight) {
        super();
        this.summary = summary;
        this.steps = steps;
        this.distance = distance;
        this.duration = duration;
        this.weight = weight;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Object> getSteps() {
        return steps;
    }

    public void setSteps(List<Object> steps) {
        this.steps = steps;
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
