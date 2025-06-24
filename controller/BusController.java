package controller;

import db.DatabaseConnection;
import model.Bus;

import java.sql.*;
import java.util.ArrayList;

public class BusController {
    public static ArrayList<Bus> getAllBuses() {
        ArrayList<Bus> busList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM buses")) {

            while (rs.next()) {
                Bus bus = new Bus(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getInt("total_seats")
                );
                busList.add(bus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busList;
    }

    public static boolean addBus(Bus bus) {
        String query = "INSERT INTO buses (name, type, total_seats) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bus.getName());
            stmt.setString(2, bus.getType());
            stmt.setInt(3, bus.getTotalSeats());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteBus(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM buses WHERE id=?")) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateBus(Bus bus) {
        String query = "UPDATE buses SET name=?, type=?, total_seats=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bus.getName());
            stmt.setString(2, bus.getType());
            stmt.setInt(3, bus.getTotalSeats());
            stmt.setInt(4, bus.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
