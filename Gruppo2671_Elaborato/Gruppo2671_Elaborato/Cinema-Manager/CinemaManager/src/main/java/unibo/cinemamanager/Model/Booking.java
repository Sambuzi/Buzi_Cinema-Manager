package unibo.cinemamanager.Model;

/**
 * Represents a booking in the cinema management system.
 */
public final class Booking {

    private int id;
    private int userId;
    private int projectionId;
    private int seats;
    private Projection projection;

    /**
     * Gets the ID of the booking.
     *
     * @return the booking ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the booking.
     *
     * @param id the ID to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the user ID associated with the booking.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the booking.
     *
     * @param userId the user ID to set
     */
    public void setUserId(final int userId) {
        this.userId = userId;
    }

    /**
     * Gets the projection ID associated with the booking.
     *
     * @return the projection ID
     */
    public int getProjectionId() {
        return projectionId;
    }

    /**
     * Sets the projection ID associated with the booking.
     *
     * @param projectionId the projection ID to set
     */
    public void setProjectionId(final int projectionId) {
        this.projectionId = projectionId;
    }

    /**
     * Gets the number of seats booked.
     *
     * @return the number of seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Sets the number of seats booked.
     *
     * @param seats the number of seats to set
     */
    public void setSeats(final int seats) {
        this.seats = seats;
    }

    /**
     * Gets the projection associated with the booking.
     *
     * @return the projection
     */
    public Projection getProjection() {
        return projection;
    }

    /**
     * Sets the projection associated with the booking.
     *
     * @param projection the projection to set
     */
    public void setProjection(final Projection projection) {
        this.projection = projection;
    }
}
