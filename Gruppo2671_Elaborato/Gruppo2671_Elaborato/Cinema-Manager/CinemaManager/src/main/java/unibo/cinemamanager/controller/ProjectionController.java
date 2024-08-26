package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Projection;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing projections.
 */
public final class ProjectionController {

    private static final int MOVIE_ID_INDEX = 1;
    private static final int PROJECTION_DATE_INDEX = 2;
    private static final int PROJECTION_TIME_INDEX = 3;
    private static final int HALL_INDEX = 4;
    private static final int AVAILABLE_SEATS_INDEX = 5;
    private static final int PROJECTION_ID_INDEX = 6;

    /**
     * Retrieves all projections from the database.
     *
     * @return a list of all projections
     * @throws SQLException if a database access error occurs
     */
    public List<Projection> getAllProjections() throws SQLException {
        List<Projection> projections = new ArrayList<>();
        String query = "SELECT p.id, p.movie_id, m.title, p.projection_date, p.projection_time, "
                     + "p.hall, p.available_seats FROM projections p "
                     + "JOIN movies m ON p.movie_id = m.id";
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
                projection.setAvailableSeats(rs.getInt("available_seats"));
                projections.add(projection);
            }
        }
        return projections;
    }

    /**
     * Creates a new projection in the database.
     *
     * @param projection the projection to create
     * @throws SQLException if a database access error occurs
     */
    public void createProjection(final Projection projection) throws SQLException {
        String query = "INSERT INTO projections (movie_id, projection_date, projection_time, "
                     + "hall, available_seats) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(MOVIE_ID_INDEX, projection.getMovieId());
            stmt.setString(PROJECTION_DATE_INDEX, projection.getProjectionDate());
            stmt.setString(PROJECTION_TIME_INDEX, projection.getProjectionTime());
            stmt.setString(HALL_INDEX, projection.getHall());
            stmt.setInt(AVAILABLE_SEATS_INDEX, projection.getAvailableSeats());
            stmt.executeUpdate();
        }
    }

    /**
     * Updates an existing projection in the database.
     *
     * @param projection the projection to update
     * @throws SQLException if a database access error occurs
     */
    public void updateProjection(final Projection projection) throws SQLException {
        String query = "UPDATE projections SET movie_id = ?, projection_date = ?, projection_time = ?, "
                     + "hall = ?, available_seats = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(MOVIE_ID_INDEX, projection.getMovieId());
            stmt.setString(PROJECTION_DATE_INDEX, projection.getProjectionDate());
            stmt.setString(PROJECTION_TIME_INDEX, projection.getProjectionTime());
            stmt.setString(HALL_INDEX, projection.getHall());
            stmt.setInt(AVAILABLE_SEATS_INDEX, projection.getAvailableSeats());
            stmt.setInt(PROJECTION_ID_INDEX, projection.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a projection from the database.
     *
     * @param projectionId the ID of the projection to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteProjection(final int projectionId) throws SQLException {
        String query = "DELETE FROM projections WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectionId);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a projection by its ID.
     *
     * @param projectionId the ID of the projection to retrieve
     * @return the projection with the specified ID
     * @throws SQLException if a database access error occurs
     */
    public Projection getProjectionById(final int projectionId) throws SQLException {
        Projection projection = new Projection();
        String query = "SELECT p.id, p.movie_id, m.title, p.projection_date, p.projection_time, "
                     + "p.hall, p.available_seats FROM projections p "
                     + "JOIN movies m ON p.movie_id = m.id WHERE p.id = ?";
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
                    projection.setAvailableSeats(rs.getInt("available_seats"));
                }
            }
        }
        return projection;
    }

    /**
     * Updates the available seats for a projection.
     *
     * @param projectionId the ID of the projection to update
     * @param seatsToBook  the number of seats to book
     * @throws SQLException if a database access error occurs
     */
    public void updateAvailableSeats(final int projectionId, final int seatsToBook) throws SQLException {
        String query = "UPDATE projections SET available_seats = available_seats - ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seatsToBook);
            stmt.setInt(2, projectionId);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all projections for a specific movie by movie ID.
     *
     * @param movieId the ID of the movie
     * @return a list of projections for the specified movie
     * @throws SQLException if a database access error occurs
     */
    public List<Projection> getProjectionsByMovieId(final int movieId) throws SQLException {
        List<Projection> projections = new ArrayList<>();
        String query = "SELECT p.id, p.movie_id, m.title, p.projection_date, p.projection_time, "
                     + "p.hall, p.available_seats FROM projections p "
                     + "JOIN movies m ON p.movie_id = m.id WHERE p.movie_id = ?";
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
                    projection.setAvailableSeats(rs.getInt("available_seats"));
                    projections.add(projection);
                }
            }
        }
        return projections;
    }

    /**
     * Retrieves all projections for a specific date.
     *
     * @param date the date of the projections
     * @return a list of projections for the specified date
     * @throws SQLException if a database access error occurs
     */
    public List<Projection> getProjectionsByDate(final String date) throws SQLException {
        List<Projection> projections = new ArrayList<>();
        String query = "SELECT p.id, p.movie_id, m.title, p.projection_date, p.projection_time, "
                     + "p.hall, p.available_seats FROM projections p "
                     + "JOIN movies m ON p.movie_id = m.id WHERE p.projection_date = ?";
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
                    projection.setAvailableSeats(rs.getInt("available_seats"));
                    projections.add(projection);
                }
            }
        }
        return projections;
    }
    
}
