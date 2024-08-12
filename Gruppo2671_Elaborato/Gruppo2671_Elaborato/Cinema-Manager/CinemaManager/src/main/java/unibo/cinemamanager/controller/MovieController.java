package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Movie;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieController {
    public void createMovie(Movie movie) throws SQLException {
        String query = "INSERT INTO movies (title, description, release_date, genre, duration) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setString(3, movie.getReleaseDate());
            stmt.setString(4, movie.getGenre());
            stmt.setInt(5, movie.getDuration());
            stmt.executeUpdate();
        }
    }

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

    // Aggiungi altri metodi come getMovieById, updateMovie, deleteMovie, etc.

    public Movie getMovieById(int id) throws SQLException {
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

    public void updateMovie(Movie movie) throws SQLException {
        String query = "UPDATE movies SET title = ?, description = ?, release_date = ?, genre = ?, duration = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setString(3, movie.getReleaseDate());
            stmt.setString(4, movie.getGenre());
            stmt.setInt(5, movie.getDuration());
            stmt.setInt(6, movie.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteMovie(int id) throws SQLException {
        String query = "DELETE FROM movies WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Movie> searchMovies(String title, String genre) throws SQLException {
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

    public List<Movie> getMoviesByGenre(String genre) throws SQLException {
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

    public List<Movie> getMoviesByDuration(int duration) throws SQLException {
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
}
