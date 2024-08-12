package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Review;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewController {
    public List<Review> getAllReviews() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.id, r.user_id, u.first_name as username, r.movie_id, m.title as movie_title, r.rating, r.comment, r.review_date " +
                       "FROM reviews r " +
                       "JOIN users u ON r.user_id = u.id " +
                       "JOIN movies m ON r.movie_id = m.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setUserId(rs.getInt("user_id"));
                review.setUsername(rs.getString("username")); // Nome dell'utente
                review.setMovieId(rs.getInt("movie_id"));
                review.setMovieTitle(rs.getString("movie_title")); // Titolo del film
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setReviewDate(rs.getString("review_date"));
                reviews.add(review);
            }
        }
        return reviews;
    }

    public void createReview(Review review) throws SQLException {
        String query = "INSERT INTO reviews (user_id, movie_id, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getMovieId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.setString(5, review.getReviewDate());
            stmt.executeUpdate();
        }
    }

    public void updateReview(Review review) throws SQLException {
        String query = "UPDATE reviews SET user_id = ?, movie_id = ?, rating = ?, comment = ?, review_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getMovieId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.setString(5, review.getReviewDate());
            stmt.setInt(6, review.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteReview(int reviewId) throws SQLException {
        String query = "DELETE FROM reviews WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reviewId);
            stmt.executeUpdate();
        }
    }

    public List<Review> getReviewsByMovieId(int movieId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.id, r.user_id, u.first_name as username, r.movie_id, m.title as movie_title, r.rating, r.comment, r.review_date " +
                       "FROM reviews r " +
                       "JOIN users u ON r.user_id = u.id " +
                       "JOIN movies m ON r.movie_id = m.id " +
                       "WHERE r.movie_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setId(rs.getInt("id"));
                    review.setUserId(rs.getInt("user_id"));
                    review.setUsername(rs.getString("username")); // Nome dell'utente
                    review.setMovieId(rs.getInt("movie_id"));
                    review.setMovieTitle(rs.getString("movie_title")); // Titolo del film
                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    review.setReviewDate(rs.getString("review_date"));
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }

    public List<Review> getReviewsByUserId(int userId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.id, r.user_id, u.first_name as username, r.movie_id, m.title as movie_title, r.rating, r.comment, r.review_date " +
                       "FROM reviews r " +
                       "JOIN users u ON r.user_id = u.id " +
                       "JOIN movies m ON r.movie_id = m.id " +
                       "WHERE r.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setId(rs.getInt("id"));
                    review.setUserId(rs.getInt("user_id"));
                    review.setUsername(rs.getString("username")); // Nome dell'utente
                    review.setMovieId(rs.getInt("movie_id"));
                    review.setMovieTitle(rs.getString("movie_title")); // Titolo del film
                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    review.setReviewDate(rs.getString("review_date"));
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }

    
}
