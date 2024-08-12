package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Projection;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectionController {
    public List<Projection> getAllProjections() throws SQLException {
        List<Projection> projections = new ArrayList<>();
        String query = "SELECT p.id, p.movie_id, m.title, p.projection_date, p.projection_time, p.hall, p.available_seats " +
                       "FROM projections p " +
                       "JOIN movies m ON p.movie_id = m.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Projection projection = new Projection();
                projection.setId(rs.getInt("id"));
                projection.setMovieId(rs.getInt("movie_id"));
                projection.setMovieTitle(rs.getString("title"));
                projection.setProjectionDate(rs.getString("projection_date"));
                projection.setProjectionTime(rs.getString("projection_time"));
                projection.setHall(rs.getString("hall"));
                projection.setAvailableSeats(rs.getInt("available_seats")); // Nuovo campo
                projections.add(projection);
            }
        }
        return projections;
    }

    public void createProjection(Projection projection) throws SQLException {
        String query = "INSERT INTO projections (movie_id, projection_date, projection_time, hall, available_seats) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projection.getMovieId());
            stmt.setString(2, projection.getProjectionDate());
            stmt.setString(3, projection.getProjectionTime());
            stmt.setString(4, projection.getHall());
            stmt.setInt(5, projection.getAvailableSeats()); // Nuovo campo
            stmt.executeUpdate();
        }
    }

    public void updateProjection(Projection projection) throws SQLException {
        String query = "UPDATE projections SET movie_id = ?, projection_date = ?, projection_time = ?, hall = ?, available_seats = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projection.getMovieId());
            stmt.setString(2, projection.getProjectionDate());
            stmt.setString(3, projection.getProjectionTime());
            stmt.setString(4, projection.getHall());
            stmt.setInt(5, projection.getAvailableSeats()); // Nuovo campo
            stmt.setInt(6, projection.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteProjection(int projectionId) throws SQLException {
        String query = "DELETE FROM projections WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectionId);
            stmt.executeUpdate();
        }
    }

    public Projection getProjectionById(int projectionId) throws SQLException {
        Projection projection = new Projection();
        String query = "SELECT p.id, p.movie_id, m.title, p.projection_date, p.projection_time, p.hall, p.available_seats " +
                       "FROM projections p " +
                       "JOIN movies m ON p.movie_id = m.id " +
                       "WHERE p.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    projection.setId(rs.getInt("id"));
                    projection.setMovieId(rs.getInt("movie_id"));
                    projection.setMovieTitle(rs.getString("title"));
                    projection.setProjectionDate(rs.getString("projection_date"));
                    projection.setProjectionTime(rs.getString("projection_time"));
                    projection.setHall(rs.getString("hall"));
                    projection.setAvailableSeats(rs.getInt("available_seats")); // Nuovo campo
                }
            }
        }
        return projection;
    }

    public void updateAvailableSeats(int projectionId, int seatsToBook) throws SQLException {
        String query = "UPDATE projections SET available_seats = available_seats - ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seatsToBook);
            stmt.setInt(2, projectionId);
            stmt.executeUpdate();
        }
    }

    public List<Projection> getProjectionsByMovieId(int movieId) throws SQLException {
        List<Projection> projections = new ArrayList<>();
        String query = "SELECT p.id, p.movie_id, m.title, p.projection_date, p.projection_time, p.hall, p.available_seats " +
                       "FROM projections p " +
                       "JOIN movies m ON p.movie_id = m.id " +
                       "WHERE p.movie_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Projection projection = new Projection();
                    projection.setId(rs.getInt("id"));
                    projection.setMovieId(rs.getInt("movie_id"));
                    projection.setMovieTitle(rs.getString("title"));
                    projection.setProjectionDate(rs.getString("projection_date"));
                    projection.setProjectionTime(rs.getString("projection_time"));
                    projection.setHall(rs.getString("hall"));
                    projection.setAvailableSeats(rs.getInt("available_seats")); // Nuovo campo
                    projections.add(projection);
                }
            }
        }
        return projections;
    }

    public List<Projection> getProjectionsByDate(String date) throws SQLException {
        List<Projection> projections = new ArrayList<>();
        String query = "SELECT p.id, p.movie_id, m.title, p.projection_date, p.projection_time, p.hall, p.available_seats " +
                       "FROM projections p " +
                       "JOIN movies m ON p.movie_id = m.id " +
                       "WHERE p.projection_date = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Projection projection = new Projection();
                    projection.setId(rs.getInt("id"));
                    projection.setMovieId(rs.getInt("movie_id"));
                    projection.setMovieTitle(rs.getString("title"));
                    projection.setProjectionDate(rs.getString("projection_date"));
                    projection.setProjectionTime(rs.getString("projection_time"));
                    projection.setHall(rs.getString("hall"));
                    projection.setAvailableSeats(rs.getInt("available_seats")); // Nuovo campo
                    projections.add(projection);
                }
            }
        }
        return projections;
    }
}
