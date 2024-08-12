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

public class MoviesFrame extends JFrame {
    private JTable moviesTable;
    private DefaultTableModel tableModel;
    private JButton backButton; // Dichiarazione del pulsante Back
    private UserMainFrame userMainFrame; // Riferimento a UserMainFrame

    public MoviesFrame(UserMainFrame userMainFrame) {
        this.userMainFrame = userMainFrame; // Inizializzazione di UserMainFrame

        setTitle("View Movies");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Barra degli strumenti
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        toolBar.add(backButton);
        mainPanel.add(toolBar, BorderLayout.NORTH);

        // Colonne della tabella
        String[] columnNames = {"ID", "Title", "Description", "Release Date", "Genre", "Duration"};
        tableModel = new DefaultTableModel(columnNames, 0);
        moviesTable = new JTable(tableModel);
        moviesTable.setFillsViewportHeight(true);
        moviesTable.setRowHeight(30);
        moviesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        moviesTable.setSelectionBackground(new Color(184, 207, 229));
        moviesTable.setSelectionForeground(Color.BLACK);
        moviesTable.setGridColor(Color.LIGHT_GRAY);

        // Personalizza l'intestazione della tabella
        JTableHeader header = moviesTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(200, 200, 200)); // Colore di sfondo chiaro per contrasto
        header.setForeground(Color.BLACK); // Colore del testo nero
        header.setOpaque(true);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Recupera i dati dei film dal database e popolano la tabella
        loadMovies();

        JScrollPane scrollPane = new JScrollPane(moviesTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Aggiungi mainPanel al frame
        add(mainPanel);

        // Aggiungi barra di stato
        JLabel statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        // Aggiorna barra di stato
        statusLabel.setText("Movies loaded successfully.");

        // Aggiungi azione al pulsante Back
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiudi il frame corrente
                userMainFrame.setVisible(true); // Torna a UserMainFrame
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
