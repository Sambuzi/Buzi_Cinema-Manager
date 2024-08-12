package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.MovieController;
import unibo.cinemamanager.Model.Movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AdminViewMoviesFrame extends JFrame {
    private JTable moviesTable;
    private DefaultTableModel tableModel;
    private JButton backButton; // Dichiarazione del pulsante Back
    private AdminMainFrame adminMainFrame; // Riferimento a AdminMainFrame

    public AdminViewMoviesFrame(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame; // Inizializzazione di AdminMainFrame

        setTitle("View Movies");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra degli strumenti
        JToolBar toolBar = new JToolBar();
        backButton = new JButton("Back");
        toolBar.add(backButton);
        add(toolBar, BorderLayout.NORTH);

        // Colonne della tabella
        String[] columnNames = {"ID", "Title", "Description", "Release Date", "Genere", "Duration"};
        tableModel = new DefaultTableModel(columnNames, 0);
        moviesTable = new JTable(tableModel);
        moviesTable.setFillsViewportHeight(true);
        moviesTable.setRowHeight(30);

        // Personalizza l'intestazione della tabella
        JTableHeader header = moviesTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.BLACK);

        // Recupera i dati dei film dal database e popolano la tabella
        loadMovies();

        JScrollPane scrollPane = new JScrollPane(moviesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Aggiungi azione al pulsante Back
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiudi il frame corrente
                adminMainFrame.setVisible(true); // Torna a AdminMainFrame
            }
        });
    }

    private void loadMovies() {
        MovieController movieController = new MovieController();
        try {
            List<Movie> movies = movieController.getAllMovies();
            tableModel.setRowCount(0); // Clear existing data
            for (Movie movie : movies) {
                Object[] rowData = {
                        movie.getId(),
                        movie.getTitle(),
                        movie.getDescription(),
                        movie.getReleaseDate(),
                        movie.getGenre(),
                        movie.getDuration()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading movies: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
