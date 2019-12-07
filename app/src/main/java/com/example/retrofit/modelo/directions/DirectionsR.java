package com.example.retrofit.modelo.directions;
import java.util.List;

public class DirectionsR {
    private List<Route> routes = null;
    private List<Waypoint> waypoints = null;
    private String code;
    private String uuid;

    /**
     * No args constructor for use in serialization
     *
     */
    public DirectionsR() {
    }

    /**
     *
     * @param routes
     * @param code
     * @param waypoints
     * @param uuid
     */
    public DirectionsR(List<Route> routes, List<Waypoint> waypoints, String code, String uuid) {
        super();
        this.routes = routes;
        this.waypoints = waypoints;
        this.code = code;
        this.uuid = uuid;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
