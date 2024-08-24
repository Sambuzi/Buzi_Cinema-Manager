package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.MovieController;
import unibo.cinemamanager.Model.Movie;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Frame per visualizzare la lista dei film.
 */
public class AdminViewMoviesFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int ROW_HEIGHT = 30;
    private static final int HEADER_FONT_SIZE = 14;
    private static final int HEADER_BACKGROUND_RED = 70;
    private static final int HEADER_BACKGROUND_GREEN = 130;
    private static final int HEADER_BACKGROUND_BLUE = 180;

    private JTable moviesTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private final AdminMainFrame adminMainFrame;

    /**
     * Costruttore per inizializzare il frame AdminViewMoviesFrame.
     *
     * @param adminMainFrame riferimento al frame principale dell'admin
     */
    public AdminViewMoviesFrame(final AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;

        setTitle("View Movies");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra degli strumenti
        JToolBar toolBar = new JToolBar();
        backButton = new JButton("Back");
        toolBar.add(backButton);
        add(toolBar, BorderLayout.NORTH);

        // Colonne della tabella
        String[] columnNames = {"ID", "Title", "Description", "Release Date", "Genre", "Duration"};
        tableModel = new DefaultTableModel(columnNames, 0);
        moviesTable = new JTable(tableModel);
        moviesTable.setFillsViewportHeight(true);
        moviesTable.setRowHeight(ROW_HEIGHT);

        // Personalizza l'intestazione della tabella
        JTableHeader header = moviesTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, HEADER_FONT_SIZE));
        header.setBackground(new Color(HEADER_BACKGROUND_RED, HEADER_BACKGROUND_GREEN, HEADER_BACKGROUND_BLUE));
        header.setForeground(Color.BLACK);

        // Recupera i dati dei film dal database e popolano la tabella
        loadMovies();

        JScrollPane scrollPane = new JScrollPane(moviesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Aggiungi azione al pulsante Back
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
                adminMainFrame.setVisible(true);
            }
        });
    }

    /**
     * Carica i film dal database e popola la tabella.
     */
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
