package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Hall;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing Halls in the Cinema Manager application.
 */
public class HallController {

    /**
     * Creates a new hall in the database.
     *
     * @param hall the Hall object containing hall details
     * @throws SQLException if a database access error occurs
     */
    public void createHall(final Hall hall) throws SQLException {
        String query = "INSERT INTO hall (name, capacity) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hall.getName());
            stmt.setInt(2, hall.getCapacity());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all halls from the database.
     *
     * @return a list of Hall objects
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Retrieves a hall by its ID.
     *
     * @param id the ID of the hall
     * @return the Hall object if found, otherwise null
     * @throws SQLException if a database access error occurs
     */
    public Hall getHallById(final int id) throws SQLException {
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

    /**
     * Updates an existing hall in the database.
     *
     * @param hall the Hall object containing updated details
     * @throws SQLException if a database access error occurs
     */
    public void updateHall(final Hall hall) throws SQLException {
        String query = "UPDATE hall SET name = ?, capacity = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hall.getName());
            stmt.setInt(2, hall.getCapacity());
            stmt.setInt(3, hall.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a hall from the database.
     *
     * @param id the ID of the hall to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteHall(final int id) throws SQLException {
        String query = "DELETE FROM hall WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Searches halls by name.
     *
     * @param name the name to search for
     * @return a list of Hall objects matching the search criteria
     * @throws SQLException if a database access error occurs
     */
    public List<Hall> searchHalls(final String name) throws SQLException {
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

    /**
     * Retrieves halls by capacity.
     *
     * @param capacity the capacity to filter by
     * @return a list of Hall objects with the specified capacity
     * @throws SQLException if a database access error occurs
     */
    public List<Hall> getHallsByCapacity(final int capacity) throws SQLException {
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
