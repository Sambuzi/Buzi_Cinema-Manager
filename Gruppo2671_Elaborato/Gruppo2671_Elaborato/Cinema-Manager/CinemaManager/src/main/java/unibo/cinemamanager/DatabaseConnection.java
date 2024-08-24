package unibo.cinemamanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections.
 */
public final class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/CinemaDB";
    private static final String USER = "root";
    private static final String PASSWORD = "Ravenna2001@";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DatabaseConnection() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Establishes and returns a connection to the database.
     *
     * @return a {@link Connection} object for the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
