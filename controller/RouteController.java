package controller;

import db.DatabaseConnection;
import model.Route;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RouteController {
    public static ArrayList<Route> getAllRoutes() {
        ArrayList<Route> routes = new ArrayList<>();
        String query = "SELECT * FROM routes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Route route = new Route(
                        rs.getInt("id"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getTimestamp("departure_time").toLocalDateTime(),
                        rs.getDouble("price"),
                        rs.getInt("bus_id")
                );
                routes.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    public static boolean addRoute(Route route) {
        String query = "INSERT INTO routes (origin, destination, departure_time, price, bus_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, route.getOrigin());
            stmt.setString(2, route.getDestination());
            stmt.setTimestamp(3, Timestamp.valueOf(route.getDepartureTime()));
            stmt.setDouble(4, route.getPrice());
            stmt.setInt(5, route.getBusId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateRoute(Route route) {
        String query = "UPDATE routes SET origin=?, destination=?, departure_time=?, price=?, bus_id=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, route.getOrigin());
            stmt.setString(2, route.getDestination());
            stmt.setTimestamp(3, Timestamp.valueOf(route.getDepartureTime()));
            stmt.setDouble(4, route.getPrice());
            stmt.setInt(5, route.getBusId());
            stmt.setInt(6, route.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteRoute(int id) {
        String query = "DELETE FROM routes WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
