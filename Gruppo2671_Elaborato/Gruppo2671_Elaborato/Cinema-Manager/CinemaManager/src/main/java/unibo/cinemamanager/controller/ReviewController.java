package unibo.cinemamanager.controller;

import unibo.cinemamanager.Model.Review;
import unibo.cinemamanager.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles operations related to reviews in the cinema manager application.
 */
public final class ReviewController {

    private static final int RATING_INDEX = 3;
    private static final int COMMENT_INDEX = 4;
    private static final int REVIEW_DATE_INDEX = 5;
    private static final int REVIEW_ID_INDEX = 6;

    /**
     * Retrieves all reviews from the database.
     *
     * @return a list of all reviews
     * @throws SQLException if a database access error occurs
     */
    public List<Review> getAllReviews() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.id, r.user_id, u.first_name as username, r.movie_id, m.title as movie_title, "
                + "r.rating, r.comment, r.review_date "
                + "FROM reviews r "
                + "JOIN users u ON r.user_id = u.id "
                + "JOIN movies m ON r.movie_id = m.id";
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

    /**
     * Creates a new review in the database.
     *
     * @param review the review to create
     * @throws SQLException if a database access error occurs
     */
    public void createReview(final Review review) throws SQLException {
        String query = "INSERT INTO reviews (user_id, movie_id, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getMovieId());
            stmt.setInt(RATING_INDEX, review.getRating());
            stmt.setString(COMMENT_INDEX, review.getComment());
            stmt.setString(REVIEW_DATE_INDEX, review.getReviewDate());
            stmt.executeUpdate();
        }
    }

    /**
     * Updates an existing review in the database.
     *
     * @param review the review to update
     * @throws SQLException if a database access error occurs
     */
    public void updateReview(final Review review) throws SQLException {
        String query = "UPDATE reviews SET user_id = ?, movie_id = ?, rating = ?, comment = ?, review_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getMovieId());
            stmt.setInt(RATING_INDEX, review.getRating());
            stmt.setString(COMMENT_INDEX, review.getComment());
            stmt.setString(REVIEW_DATE_INDEX, review.getReviewDate());
            stmt.setInt(REVIEW_ID_INDEX, review.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a review from the database by its ID.
     *
     * @param reviewId the ID of the review to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteReview(final int reviewId) throws SQLException {
        String query = "DELETE FROM reviews WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reviewId);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves reviews by the specified movie ID.
     *
     * @param movieId the ID of the movie
     * @return a list of reviews for the specified movie
     * @throws SQLException if a database access error occurs
     */
    public List<Review> getReviewsByMovieId(final int movieId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.id, r.user_id, u.first_name as username, r.movie_id, m.title as movie_title, "
                + "r.rating, r.comment, r.review_date "
                + "FROM reviews r "
                + "JOIN users u ON r.user_id = u.id "
                + "JOIN movies m ON r.movie_id = m.id "
                + "WHERE r.movie_id = ?";
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

    /**
     * Retrieves reviews by the specified user ID.
     *
     * @param userId the ID of the user
     * @return a list of reviews by the specified user
     * @throws SQLException if a database access error occurs
     */
    public List<Review> getReviewsByUserId(final int userId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.id, r.user_id, u.first_name as username, r.movie_id, m.title as movie_title, "
                + "r.rating, r.comment, r.review_date "
                + "FROM reviews r "
                + "JOIN users u ON r.user_id = u.id "
                + "JOIN movies m ON r.movie_id = m.id "
                + "WHERE r.user_id = ?";
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

    //dammi i migliori film recensiti
    public List<Review> getBestReviewedMovies() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.movie_id, m.title as movie_title, AVG(r.rating) as avg_rating "
                + "FROM reviews r "
                + "JOIN movies m ON r.movie_id = m.id "
                + "GROUP BY r.movie_id, m.title "
                + "ORDER BY avg_rating DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Review review = new Review();
                review.setMovieId(rs.getInt("movie_id"));
                review.setMovieTitle(rs.getString("movie_title"));
                review.setRating(rs.getInt("avg_rating"));
                reviews.add(review);
            }
        }
        return reviews;
    }

    /**
     * Retrieves movies with an average rating above 4.0.
     *
     * @return a list of reviews for movies with average rating above 4.0
     * @throws SQLException if a database access error occurs
     */
    public List<Review> getMoviesWithPositiveReviews() {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.movie_id, m.title as movie_title, AVG(r.rating) as avg_rating "
                + "FROM reviews r "
                + "JOIN movies m ON r.movie_id = m.id "
                + "GROUP BY r.movie_id, m.title "
                + "HAVING avg_rating >= 4.0 "
                + "ORDER BY avg_rating DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Review review = new Review();
                review.setMovieId(rs.getInt("movie_id"));
                review.setMovieTitle(rs.getString("movie_title"));
                review.setRating(rs.getInt("avg_rating"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Retrieves movies with an average rating above 3.0.
     *
     * @return a list of reviews for movies with average rating above 3.0
     * @throws SQLException if a database access error occurs
     */
    public List<Review> getMoviesWithReviewsAboveThree() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.movie_id, m.title as movie_title, AVG(r.rating) as avg_rating "
                + "FROM reviews r "
                + "JOIN movies m ON r.movie_id = m.id "
                + "GROUP BY r.movie_id, m.title "
                + "HAVING avg_rating > 3.0 "
                + "ORDER BY avg_rating DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Review review = new Review();
                review.setMovieId(rs.getInt("movie_id"));
                review.setMovieTitle(rs.getString("movie_title"));
                review.setRating(rs.getInt("avg_rating"));
                reviews.add(review);
            }
        }
        return reviews;
    }

    /**
     * Retrieves movies with an average rating below 3.0.
     *
     * @return a list of reviews for movies with average rating below 3.0
     * @throws SQLException if a database access error occurs
     */
    public List<Review> getMoviesWithReviewsBelowThree() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.movie_id, m.title as movie_title, AVG(r.rating) as avg_rating "
                + "FROM reviews r "
                + "JOIN movies m ON r.movie_id = m.id "
                + "GROUP BY r.movie_id, m.title "
                + "HAVING avg_rating < 3.0 "
                + "ORDER BY avg_rating DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Review review = new Review();
                review.setMovieId(rs.getInt("movie_id"));
                review.setMovieTitle(rs.getString("movie_title"));
                review.setRating(rs.getInt("avg_rating"));
                reviews.add(review);
            }
        }
        return reviews;
}

}
