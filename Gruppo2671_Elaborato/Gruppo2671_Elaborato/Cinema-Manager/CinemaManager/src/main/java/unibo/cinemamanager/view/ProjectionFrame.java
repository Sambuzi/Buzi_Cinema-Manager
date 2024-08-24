package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.MovieController;
import unibo.cinemamanager.controller.ProjectionController;
import unibo.cinemamanager.controller.HallController;
import unibo.cinemamanager.Model.Movie;
import unibo.cinemamanager.Model.Projection;
import unibo.cinemamanager.Model.Hall;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a frame for viewing projections.
 */
public class ProjectionFrame extends JFrame {
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;
    private static final int BORDER_PADDING = 20;
    private static final int ROW_HEIGHT = 30;
    private static final int HEADER_FONT_SIZE = 16;
    private static final int BUTTON_FONT_SIZE = 14;
    private static final Color SELECTION_BACKGROUND_COLOR = new Color(184, 207, 229);
    private static final Color HEADER_BACKGROUND_COLOR = new Color(70, 130, 180);

    private JTable projectionsTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private UserMainFrame userMainFrame;
    private JComboBox<Hall> hallComboBox;

    /**
     * Constructs the ProjectionFrame.
     *
     * @param userMainFrame the main frame of the user, must be final.
     */
    public ProjectionFrame(final UserMainFrame userMainFrame) {
        this.userMainFrame = userMainFrame;

        setTitle("Available Projections");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(BORDER_PADDING, BORDER_PADDING));
        JPanel mainPanel = new JPanel(new BorderLayout(BORDER_PADDING, BORDER_PADDING));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_PADDING, BORDER_PADDING, BORDER_PADDING, BORDER_PADDING));
        mainPanel.setBackground(Color.WHITE);

        // Toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE));
        toolBar.add(backButton);

        hallComboBox = new JComboBox<>();
        loadHalls();
        toolBar.add(new JLabel("Filter by Hall:"));
        toolBar.add(hallComboBox);

        mainPanel.add(toolBar, BorderLayout.NORTH);

        // Table columns
        String[] columnNames = {"Movie Title", "Projection Date", "Projection Time", "Hall"};
        tableModel = new DefaultTableModel(columnNames, 0);
        projectionsTable = new JTable(tableModel);
        projectionsTable.setFillsViewportHeight(true);
        projectionsTable.setRowHeight(ROW_HEIGHT);
        projectionsTable.setFont(new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE));
        projectionsTable.setSelectionBackground(SELECTION_BACKGROUND_COLOR);
        projectionsTable.setSelectionForeground(Color.BLACK);
        projectionsTable.setGridColor(Color.LIGHT_GRAY);

        // Customize table header
        JTableHeader header = projectionsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, HEADER_FONT_SIZE));
        header.setBackground(HEADER_BACKGROUND_COLOR);
        header.setForeground(Color.BLACK);
        header.setOpaque(true);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Load projections
        loadProjections(null);

        JScrollPane scrollPane = new JScrollPane(projectionsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Back button action
        backButton.addActionListener(e -> {
            dispose();
            userMainFrame.setVisible(true);
        });

        // Hall filter action
        hallComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Hall selectedHall = (Hall) hallComboBox.getSelectedItem();
                loadProjections(selectedHall != null ? selectedHall.getName() : null);
            }
        });

        add(mainPanel);
    }

    /**
     * Loads the list of halls into the combo box.
     */
    private void loadHalls() {
        HallController hallController = new HallController();
        try {
            List<Hall> halls = hallController.getAllHalls();
            hallComboBox.addItem(null); // Option to show all projections
            for (Hall hall : halls) {
                hallComboBox.addItem(hall);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading halls: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads the projections based on the selected hall filter.
     *
     * @param hallNameFilter the name of the hall to filter projections by; null to show all projections
     */
    private void loadProjections(final String hallNameFilter) {
        ProjectionController projectionController = new ProjectionController();
        MovieController movieController = new MovieController();
        try {
            List<Projection> projections = projectionController.getAllProjections();
            tableModel.setRowCount(0); // Clear table
            Map<Integer, String> movieTitleMap = new HashMap<>();
            for (Projection projection : projections) {
                if (hallNameFilter == null || projection.getHall().equals(hallNameFilter)) {
                    if (!movieTitleMap.containsKey(projection.getMovieId())) {
                        Movie movie = movieController.getMovieById(projection.getMovieId());
                        if (movie != null) {
                            movieTitleMap.put(projection.getMovieId(), movie.getTitle());
                        }
                    }
                    String projectionDate = projection.getProjectionDate().split(" ")[0]; // Only the date
                    tableModel.addRow(new Object[]{
                        movieTitleMap.get(projection.getMovieId()),
                        projectionDate,
                        projection.getProjectionTime(),
                        projection.getHall()
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading projections: " + e.getMessage(),
             "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
