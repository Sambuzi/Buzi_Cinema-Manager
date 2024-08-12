package unibo.cinemamanager;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseOutput {
    public static void main(String[] args) {
        // Test di connessione al database
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connessione al database riuscita!");
            }
        } catch (SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());
        }

    }
}
