package model;

import java.time.LocalDateTime;

public class Route {
    private int id;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private double price;
    private int busId;

    public Route(int id, String origin, String destination, LocalDateTime departureTime, double price, int busId) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.price = price;
        this.busId = busId;
    }

    public Route(String origin, String destination, LocalDateTime departureTime, double price, int busId) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.price = price;
        this.busId = busId;
    }

    public int getId() { return id; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public double getPrice() { return price; }
    public int getBusId() { return busId; }

    public void setId(int id) { this.id = id; }
}
