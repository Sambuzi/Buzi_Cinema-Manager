package unibo.cinemamanager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe di test per verificare la connessione al database.
 */
public final class DatabaseOutput {

    // Costruttore privato per prevenire l'istanza della classe
    private DatabaseOutput() {
        // Questa classe non deve essere istanziata
    }

    /**
     * Metodo principale per eseguire il test di connessione al database.
     *
     * @param args i parametri della riga di comando, non utilizzati
     */
    public static void main(final String[] args) {
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
