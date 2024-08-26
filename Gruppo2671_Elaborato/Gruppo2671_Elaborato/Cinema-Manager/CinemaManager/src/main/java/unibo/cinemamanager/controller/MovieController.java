package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Movie;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The MovieController class handles operations related to movies in the cinema manager application.
 */
public final class MovieController {

    private static final int MOVIE_DURATION_INDEX = 5;
    private static final int MOVIE_ID_INDEX = 6;

    /**
     * Creates a new movie in the database.
     *
     * @param movie the movie to be created
     * @throws SQLException if a database access error occurs
     */
    public void createMovie(final Movie movie) throws SQLException {
        String query = "INSERT INTO movies (title, description, release_date, genre, duration) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setString(3, movie.getReleaseDate());
            stmt.setString(4, movie.getGenre());
            stmt.setInt(MOVIE_DURATION_INDEX, movie.getDuration());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all movies from the database.
     *
     * @return a list of all movies
     * @throws SQLException if a database access error occurs
     */
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setDescription(rs.getString("description"));
                movie.setReleaseDate(rs.getString("release_date"));
                movie.setGenre(rs.getString("genre"));
                movie.setDuration(rs.getInt("duration"));
                movies.add(movie);
            }
        }
        return movies;
    }

    /**
     * Retrieves a movie by its ID.
     *
     * @param id the ID of the movie to retrieve
     * @return the movie with the specified ID, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Movie getMovieById(final int id) throws SQLException {
        String query = "SELECT * FROM movies WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Movie movie = new Movie();
                    movie.setId(rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setDescription(rs.getString("description"));
                    movie.setReleaseDate(rs.getString("release_date"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setDuration(rs.getInt("duration"));
                    return movie;
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing movie in the database.
     *
     * @param movie the movie to update
     * @throws SQLException if a database access error occurs
     */
    public void updateMovie(final Movie movie) throws SQLException {
        String query = "UPDATE movies SET title = ?, description = ?, release_date = ?, genre = ?, duration = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setString(3, movie.getReleaseDate());
            stmt.setString(4, movie.getGenre());
            stmt.setInt(MOVIE_DURATION_INDEX, movie.getDuration());
            stmt.setInt(MOVIE_ID_INDEX, movie.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a movie from the database by its ID.
     *
     * @param id the ID of the movie to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteMovie(final int id) throws SQLException {
        String query = "DELETE FROM movies WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Searches for movies by title and genre.
     *
     * @param title the title to search for
     * @param genre the genre to search for
     * @return a list of movies matching the search criteria
     * @throws SQLException if a database access error occurs
     */
    public List<Movie> searchMovies(final String title, final String genre) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE title LIKE ? AND genre LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + title + "%");
            stmt.setString(2, "%" + genre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setId(rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setDescription(rs.getString("description"));
                    movie.setReleaseDate(rs.getString("release_date"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setDuration(rs.getInt("duration"));
                    movies.add(movie);
                }
            }
        }
        return movies;
    }

    /**
     * Retrieves movies by genre.
     *
     * @param genre the genre to filter by
     * @return a list of movies with the specified genre
     * @throws SQLException if a database access error occurs
     */
    public List<Movie> getMoviesByGenre(final String genre) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE genre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, genre);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setId(rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setDescription(rs.getString("description"));
                    movie.setReleaseDate(rs.getString("release_date"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setDuration(rs.getInt("duration"));
                    movies.add(movie);
                }
            }
        }
        return movies;
    }

    /**
     * Retrieves movies by their duration.
     *
     * @param duration the duration to filter by
     * @return a list of movies with the specified duration
     * @throws SQLException if a database access error occurs
     */
    public List<Movie> getMoviesByDuration(final int duration) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE duration = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, duration);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setId(rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setDescription(rs.getString("description"));
                    movie.setReleaseDate(rs.getString("release_date"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setDuration(rs.getInt("duration"));
                    movies.add(movie);
                }
            }
        }
        return movies;
    }

    public List<Movie> getMoviesByIds(List<Integer> list) {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE id IN (";
        for (int i = 0; i < list.size(); i++) {
            query += "?";
            if (i < list.size() - 1) {
                query += ", ";
            }
        }
        query += ")";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < list.size(); i++) {
                stmt.setInt(i + 1, list.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setId(rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setDescription(rs.getString("description"));
                    movie.setReleaseDate(rs.getString("release_date"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setDuration(rs.getInt("duration"));
                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
