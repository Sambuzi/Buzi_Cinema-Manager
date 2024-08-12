package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Booking;
import unibo.cinemamanager.Model.Projection;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingController {
    public void createBooking(Booking booking) throws SQLException {
        String query = "INSERT INTO bookings (user_id, projection_id, seats) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getProjectionId());
            stmt.setInt(3, booking.getSeats());
            stmt.executeUpdate();
        }
    }

    public void deleteBooking(int bookingId) throws SQLException {
        String query = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            stmt.executeUpdate();
        }
    }

    public List<Booking> getBookingsByUserId(int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT b.id, b.user_id, b.projection_id, b.seats, p.movie_id, m.title as movie_title, p.projection_time " +
                       "FROM bookings b " +
                       "JOIN projections p ON b.projection_id = p.id " +
                       "JOIN movies m ON p.movie_id = m.id " +
                       "WHERE b.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setId(rs.getInt("id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setProjectionId(rs.getInt("projection_id"));
                    booking.setSeats(rs.getInt("seats"));

                    Projection projection = new Projection();
                    projection.setId(rs.getInt("projection_id"));
                    projection.setMovieTitle(rs.getString("movie_title"));
                    projection.setProjectionTime(rs.getString("projection_time"));
                    booking.setProjection(projection);

                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }
}
