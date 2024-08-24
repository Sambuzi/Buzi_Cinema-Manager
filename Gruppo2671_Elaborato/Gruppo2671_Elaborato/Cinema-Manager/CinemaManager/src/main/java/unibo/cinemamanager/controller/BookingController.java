package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Booking;
import unibo.cinemamanager.Model.Projection;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling booking operations.
 */
public class BookingController {

    /**
     * Creates a new booking in the database.
     *
     * @param booking the booking to be created
     * @throws SQLException if a database access error occurs
     */
    public void createBooking(final Booking booking) throws SQLException {
        String query = "INSERT INTO bookings (user_id, projection_id, seats) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getProjectionId());
            stmt.setInt(3, booking.getSeats());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a booking from the database.
     *
     * @param bookingId the ID of the booking to be deleted
     * @throws SQLException if a database access error occurs
     */
    public void deleteBooking(final int bookingId) throws SQLException {
        String query = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a list of bookings made by a specific user.
     *
     * @param userId the ID of the user
     * @return a list of bookings made by the user
     * @throws SQLException if a database access error occurs
     */
    public List<Booking> getBookingsByUserId(final int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT b.id, b.user_id, b.projection_id, b.seats, "
                     + "p.movie_id, m.title as movie_title, p.projection_time "
                     + "FROM bookings b "
                     + "JOIN projections p ON b.projection_id = p.id "
                     + "JOIN movies m ON p.movie_id = m.id "
                     + "WHERE b.user_id = ?";
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
