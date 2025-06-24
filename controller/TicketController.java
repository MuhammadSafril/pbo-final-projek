package controller;

import db.DatabaseConnection;
import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TicketController {
    public static boolean addTicket(Ticket ticket) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO tickets (passenger_name, route_id, order_time) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ticket.getPassengerName());
            stmt.setInt(2, ticket.getRouteId());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(ticket.getOrderTime()));
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Gagal menambahkan tiket: " + e.getMessage());
            return false;
        }
    }
}
