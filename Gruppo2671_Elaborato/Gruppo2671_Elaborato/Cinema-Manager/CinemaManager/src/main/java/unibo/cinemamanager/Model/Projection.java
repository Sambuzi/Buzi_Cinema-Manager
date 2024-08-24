package unibo.cinemamanager.Model;

/**
 * Represents a movie projection in the cinema management system.
 */
public final class Projection {

    private int id;
    private int movieId;
    private String movieTitle; // Title of the movie
    private String projectionDate;
    private String projectionTime;
    private String hall;
    private int availableSeats; // Available seats for the projection

    /**
     * Gets the ID of the projection.
     *
     * @return the projection ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the projection.
     *
     * @param id the ID to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the movie ID associated with the projection.
     *
     * @return the movie ID
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Sets the movie ID associated with the projection.
     *
     * @param movieId the movie ID to set
     */
    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    /**
     * Gets the title of the movie.
     *
     * @return the movie title
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Sets the title of the movie.
     *
     * @param movieTitle the movie title to set
     */
    public void setMovieTitle(final String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * Gets the date of the projection.
     *
     * @return the projection date
     */
    public String getProjectionDate() {
        return projectionDate;
    }

    /**
     * Sets the date of the projection.
     *
     * @param projectionDate the projection date to set
     */
    public void setProjectionDate(final String projectionDate) {
        this.projectionDate = projectionDate;
    }

    /**
     * Gets the time of the projection.
     *
     * @return the projection time
     */
    public String getProjectionTime() {
        return projectionTime;
    }

    /**
     * Sets the time of the projection.
     *
     * @param projectionTime the projection time to set
     */
    public void setProjectionTime(final String projectionTime) {
        this.projectionTime = projectionTime;
    }

    /**
     * Gets the hall where the projection will be shown.
     *
     * @return the hall name
     */
    public String getHall() {
        return hall;
    }

    /**
     * Sets the hall where the projection will be shown.
     *
     * @param hall the hall name to set
     */
    public void setHall(final String hall) {
        this.hall = hall;
    }

    /**
     * Gets the number of available seats for the projection.
     *
     * @return the number of available seats
     */
    public int getAvailableSeats() {
        return availableSeats;
    }

    /**
     * Sets the number of available seats for the projection.
     *
     * @param availableSeats the number of available seats to set
     */
    public void setAvailableSeats(final int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
