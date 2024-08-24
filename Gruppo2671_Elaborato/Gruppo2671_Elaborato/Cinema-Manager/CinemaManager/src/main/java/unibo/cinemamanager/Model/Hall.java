package unibo.cinemamanager.Model;

/**
 * Represents a hall in the cinema.
 */
public final class Hall {

    private int id;
    private String name;
    private int capacity;

    /**
     * Gets the ID of the hall.
     *
     * @return the hall ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the hall.
     *
     * @param id the ID to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the name of the hall.
     *
     * @return the name of the hall
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the hall.
     *
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the capacity of the hall.
     *
     * @return the capacity of the hall
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the hall.
     *
     * @param capacity the capacity to set
     */
    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns a string representation of the hall.
     *
     * @return the name of the hall
     */
    @Override
    public String toString() {
        return name;
    }
}
