package unibo.cinemamanager;

/**
 * This class contains methods for authenticating admin credentials.
 * It provides a static method to check if the provided username and password match the admin credentials.
 */
public final class AdminCredentials {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "adminpass";

    // Private constructor to prevent instantiation
    private AdminCredentials() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Authenticates the provided username and password against the stored admin credentials.
     *
     * @param username the username to be authenticated, must be final.
     * @param password the password to be authenticated, must be final.
     * @return true if the credentials match, false otherwise
     */
    public static boolean authenticate(final String username, final String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}
