package unibo.cinemamanager.Model;

/**
 * Represents a user in the cinema management system.
 */
@SuppressWarnings("all")
public final class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String preferredGenres;
    private int priorityLevel;
    private String userType;

    /**
     * Gets the ID of the user.
     *
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the ID to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets the preferred genres of the user.
     *
     * @return the preferred genres
     */
    public String getPreferredGenres() {
        return preferredGenres;
    }

    /**
     * Sets the preferred genres of the user.
     *
     * @param preferredGenres the preferred genres to set
     */
    public void setPreferredGenres(final String preferredGenres) {
        this.preferredGenres = preferredGenres;
    }

    /**
     * Gets the priority level of the user.
     *
     * @return the priority level
     */
    public int getPriorityLevel() {
        return priorityLevel;
    }

    /**
     * Sets the priority level of the user.
     *
     * @param priorityLevel the priority level to set
     */
    public void setPriorityLevel(final int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    /**
     * Gets the user type.
     *
     * @return the user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the user type.
     *
     * @param userType the user type to set
     */
    public void setUserType(final String userType) {
        this.userType = userType;
    }
}
