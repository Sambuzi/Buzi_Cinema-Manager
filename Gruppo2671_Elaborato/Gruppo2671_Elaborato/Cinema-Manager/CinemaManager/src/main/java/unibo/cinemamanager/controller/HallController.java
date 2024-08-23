package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Hall;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallController {

    // Metodo per creare una nuova sala
    public void createHall(Hall hall) throws SQLException {
        String query = "INSERT INTO hall (name, capacity) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hall.getName());
            stmt.setInt(2, hall.getCapacity());
            stmt.executeUpdate();
        }
    }

    // Metodo per ottenere tutte le sale
    public List<Hall> getAllHalls() throws SQLException {
        List<Hall> halls = new ArrayList<>();
        String query = "SELECT * FROM hall";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Hall hall = new Hall();
                hall.setId(rs.getInt("id"));
                hall.setName(rs.getString("name"));
                hall.setCapacity(rs.getInt("capacity"));
                halls.add(hall);
            }
        }
        return halls;
    }

    // Metodo per ottenere una sala per ID
    public Hall getHallById(int id) throws SQLException {
        String query = "SELECT * FROM hall WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Hall hall = new Hall();
                    hall.setId(rs.getInt("id"));
                    hall.setName(rs.getString("name"));
                    hall.setCapacity(rs.getInt("capacity"));
                    return hall;
                }
            }
        }
        return null;
    }

    // Metodo per aggiornare una sala esistente
    public void updateHall(Hall hall) throws SQLException {
        String query = "UPDATE hall SET name = ?, capacity = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hall.getName());
            stmt.setInt(2, hall.getCapacity());
            stmt.setInt(3, hall.getId());
            stmt.executeUpdate();
        }
    }

    // Metodo per eliminare una sala
    public void deleteHall(int id) throws SQLException {
        String query = "DELETE FROM hall WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Metodo per cercare sale per nome
    public List<Hall> searchHalls(String name) throws SQLException {
        List<Hall> halls = new ArrayList<>();
        String query = "SELECT * FROM hall WHERE name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Hall hall = new Hall();
                    hall.setId(rs.getInt("id"));
                    hall.setName(rs.getString("name"));
                    hall.setCapacity(rs.getInt("capacity"));
                    halls.add(hall);
                }
            }
        }
        return halls;
    }

    // Metodo per ottenere sale per capacità
    public List<Hall> getHallsByCapacity(int capacity) throws SQLException {
        List<Hall> halls = new ArrayList<>();
        String query = "SELECT * FROM hall WHERE capacity = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, capacity);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Hall hall = new Hall();
                    hall.setId(rs.getInt("id"));
                    hall.setName(rs.getString("name"));
                    hall.setCapacity(rs.getInt("capacity"));
                    halls.add(hall);
                }
            }
        }
        return halls;
    }
}
