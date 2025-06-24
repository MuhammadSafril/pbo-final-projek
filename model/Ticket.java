package model;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private String passengerName;
    private int routeId;
    private LocalDateTime orderTime;

    public Ticket(String passengerName, int routeId, LocalDateTime orderTime) {
        this.passengerName = passengerName;
        this.routeId = routeId;
        this.orderTime = orderTime;
    }

    public Ticket(int id, String passengerName, int routeId, LocalDateTime orderTime) {
        this.id = id;
        this.passengerName = passengerName;
        this.routeId = routeId;
        this.orderTime = orderTime;
    }

    public int getId() {
        return id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getRouteId() {
        return routeId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }
}
