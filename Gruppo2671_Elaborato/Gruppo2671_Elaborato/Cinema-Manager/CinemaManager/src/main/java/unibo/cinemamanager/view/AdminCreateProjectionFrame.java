package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.MovieController;
import unibo.cinemamanager.controller.ProjectionController;
import unibo.cinemamanager.controller.HallController;
import unibo.cinemamanager.Model.Movie;
import unibo.cinemamanager.Model.Projection;
import unibo.cinemamanager.Model.Hall;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * This class represents a frame for creating a new movie projection.
 */
public class AdminCreateProjectionFrame extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;
    private static final int INSET_SIZE = 5;
    private static final int TEXT_FIELD_SIZE = 20;
    private static final int SAVE_BUTTON_GRID_WIDTH = 2;
    private static final int PROJECTION_DATE_GRID_Y = 1;
    private static final int PROJECTION_TIME_GRID_Y = 2;
    private static final int SELECT_HALL_GRID_Y = 3;
    private static final int AVAILABLE_SEATS_GRID_Y = 4;
    private static final int SAVE_BUTTON_GRID_Y = 5;

    private JComboBox<String> movieComboBox;
    private JComboBox<Hall> hallComboBox;
    private JTextField projectionDateField;
    private JTextField projectionTimeField;
    private JTextField availableSeatsField;
    private JButton saveButton;

    /**
     * Initializes the frame for creating a new projection.
     */
    public AdminCreateProjectionFrame() {
        setTitle("Create Projection");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET_SIZE, INSET_SIZE, INSET_SIZE, INSET_SIZE);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Movie:"), gbc);

        movieComboBox = new JComboBox<>();
        loadMovies();
        gbc.gridx = 1;
        panel.add(movieComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = PROJECTION_DATE_GRID_Y;
        panel.add(new JLabel("Projection Date (YYYY-MM-DD):"), gbc);
        projectionDateField = new JTextField(TEXT_FIELD_SIZE);
        gbc.gridx = 1;
        panel.add(projectionDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = PROJECTION_TIME_GRID_Y;
        panel.add(new JLabel("Projection Time (HH:MM:SS):"), gbc);
        projectionTimeField = new JTextField(TEXT_FIELD_SIZE);
        gbc.gridx = 1;
        panel.add(projectionTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = SELECT_HALL_GRID_Y;
        panel.add(new JLabel("Select Hall:"), gbc);
        hallComboBox = new JComboBox<>();
        loadHalls();
        gbc.gridx = 1;
        panel.add(hallComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = AVAILABLE_SEATS_GRID_Y;
        panel.add(new JLabel("Available Seats:"), gbc);
        availableSeatsField = new JTextField(TEXT_FIELD_SIZE);
        availableSeatsField.setEditable(false);
        gbc.gridx = 1;
        panel.add(availableSeatsField, gbc);

        saveButton = new JButton("Save Projection");
        gbc.gridx = 0;
        gbc.gridy = SAVE_BUTTON_GRID_Y;
        gbc.gridwidth = SAVE_BUTTON_GRID_WIDTH;
        panel.add(saveButton, gbc);

        add(panel, BorderLayout.CENTER);

        hallComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Hall selectedHall = (Hall) hallComboBox.getSelectedItem();
                if (selectedHall != null) {
                    availableSeatsField.setText(String.valueOf(selectedHall.getCapacity()));
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                saveProjection();
            }
        });
    }

    private void loadMovies() {
        MovieController movieController = new MovieController();
        try {
            List<Movie> movies = movieController.getAllMovies();
            for (Movie movie : movies) {
                movieComboBox.addItem(movie.getId() + ": " + movie.getTitle());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading movies: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadHalls() {
        HallController hallController = new HallController();
        try {
            List<Hall> halls = hallController.getAllHalls();
            for (Hall hall : halls) {
                hallComboBox.addItem(hall);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading halls: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveProjection() {
        String selectedMovie = (String) movieComboBox.getSelectedItem();
        int movieId = Integer.parseInt(selectedMovie.split(":")[0].trim());
        String projectionDate = projectionDateField.getText();
        String projectionTime = projectionTimeField.getText();
        Hall selectedHall = (Hall) hallComboBox.getSelectedItem();
        int availableSeats = Integer.parseInt(availableSeatsField.getText());

        Projection projection = new Projection();
        projection.setMovieId(movieId);
        projection.setProjectionDate(projectionDate);
        projection.setProjectionTime(projectionTime);
        projection.setHall(selectedHall.getName());
        projection.setAvailableSeats(availableSeats);

        ProjectionController projectionController = new ProjectionController();
        try {
            projectionController.createProjection(projection);
            JOptionPane.showMessageDialog(this, "Projection created successfully!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating projection: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
