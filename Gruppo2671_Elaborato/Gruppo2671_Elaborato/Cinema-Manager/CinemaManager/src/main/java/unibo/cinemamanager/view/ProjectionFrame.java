package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.MovieController;
import unibo.cinemamanager.controller.ProjectionController;
import unibo.cinemamanager.controller.HallController; // Importa il controller Hall
import unibo.cinemamanager.Model.Movie;
import unibo.cinemamanager.Model.Projection;
import unibo.cinemamanager.Model.Hall; // Importa il modello Hall

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectionFrame extends JFrame {
    private JTable projectionsTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private UserMainFrame userMainFrame;
    private JComboBox<Hall> hallComboBox; // ComboBox per le sale

    public ProjectionFrame(UserMainFrame userMainFrame) {
        this.userMainFrame = userMainFrame;

        setTitle("Available Projections");
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

        hallComboBox = new JComboBox<>();
        loadHalls();
        toolBar.add(new JLabel("Filter by Hall:"));
        toolBar.add(hallComboBox);

        mainPanel.add(toolBar, BorderLayout.NORTH);

        // Colonne della tabella
        String[] columnNames = {"Movie Title", "Projection Date", "Projection Time", "Hall"};
        tableModel = new DefaultTableModel(columnNames, 0);
        projectionsTable = new JTable(tableModel);
        projectionsTable.setFillsViewportHeight(true);
        projectionsTable.setRowHeight(30);
        projectionsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        projectionsTable.setSelectionBackground(new Color(184, 207, 229));
        projectionsTable.setSelectionForeground(Color.BLACK);
        projectionsTable.setGridColor(Color.LIGHT_GRAY);

        // Personalizza l'intestazione della tabella
        JTableHeader header = projectionsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.BLACK); // Imposta il colore del testo su nero fisso
        header.setOpaque(true);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Recupera i dati delle proiezioni dal database e popolano la tabella
        loadProjections(null); // Carica tutte le proiezioni inizialmente

        JScrollPane scrollPane = new JScrollPane(projectionsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Aggiungi azione al pulsante Back
        backButton.addActionListener(e -> {
            dispose();
            userMainFrame.setVisible(true);
        });

        // Aggiungi listener per filtrare le proiezioni in base alla sala selezionata
        hallComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hall selectedHall = (Hall) hallComboBox.getSelectedItem();
                loadProjections(selectedHall != null ? selectedHall.getName() : null);
            }
        });

        add(mainPanel);
    }

    private void loadHalls() {
        HallController hallController = new HallController();
        try {
            List<Hall> halls = hallController.getAllHalls();
            hallComboBox.addItem(null); // Aggiungi un'opzione per mostrare tutte le proiezioni
            for (Hall hall : halls) {
                hallComboBox.addItem(hall);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading halls: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadProjections(String hallNameFilter) {
        ProjectionController projectionController = new ProjectionController();
        MovieController movieController = new MovieController();
        try {
            List<Projection> projections = projectionController.getAllProjections();
            tableModel.setRowCount(0); // Pulisci la tabella
            Map<Integer, String> movieTitleMap = new HashMap<>();
            for (Projection projection : projections) {
                if (hallNameFilter == null || projection.getHall().equals(hallNameFilter)) {
                    if (!movieTitleMap.containsKey(projection.getMovieId())) {
                        Movie movie = movieController.getMovieById(projection.getMovieId());
                        if (movie != null) {
                            movieTitleMap.put(projection.getMovieId(), movie.getTitle());
                        }
                    }
                    String projectionDate = projection.getProjectionDate().split(" ")[0]; // Solo la data
                    tableModel.addRow(new Object[]{
                            movieTitleMap.get(projection.getMovieId()),
                            projectionDate,
                            projection.getProjectionTime(),
                            projection.getHall()
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading projections: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
