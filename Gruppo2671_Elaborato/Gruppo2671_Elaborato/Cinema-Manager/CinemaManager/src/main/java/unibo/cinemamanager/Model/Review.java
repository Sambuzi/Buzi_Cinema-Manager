package unibo.cinemamanager.Model;

/**
 * Represents a review made by a user for a movie.
 */
public final class Review {

    private int id;
    private int userId;
    private String username;
    private int movieId;
    private String movieTitle;
    private int rating;
    private String comment;
    private String reviewDate;

    /**
     * Gets the ID of the review.
     *
     * @return the ID of the review
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the review.
     *
     * @param id the ID to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the user ID associated with the review.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the review.
     *
     * @param userId the user ID to set
     */
    public void setUserId(final int userId) {
        this.userId = userId;
    }

    /**
     * Gets the username of the user who made the review.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user who made the review.
     *
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets the movie ID associated with the review.
     *
     * @return the movie ID
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Sets the movie ID associated with the review.
     *
     * @param movieId the movie ID to set
     */
    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    /**
     * Gets the title of the movie associated with the review.
     *
     * @return the movie title
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Sets the title of the movie associated with the review.
     *
     * @param movieTitle the movie title to set
     */
    public void setMovieTitle(final String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * Gets the rating given in the review.
     *
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating given in the review.
     *
     * @param rating the rating to set
     */
    public void setRating(final int rating) {
        this.rating = rating;
    }

    /**
     * Gets the comment in the review.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment in the review.
     *
     * @param comment the comment to set
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }

    /**
     * Gets the date when the review was made.
     *
     * @return the review date
     */
    public String getReviewDate() {
        return reviewDate;
    }

    /**
     * Sets the date when the review was made.
     *
     * @param reviewDate the review date to set
     */
    public void setReviewDate(final String reviewDate) {
        this.reviewDate = reviewDate;
    }
}
