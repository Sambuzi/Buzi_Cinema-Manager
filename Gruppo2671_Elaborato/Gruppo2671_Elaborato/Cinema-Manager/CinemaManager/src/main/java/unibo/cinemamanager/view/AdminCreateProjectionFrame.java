package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.MovieController;
import unibo.cinemamanager.controller.ProjectionController;
import unibo.cinemamanager.Model.Movie;
import unibo.cinemamanager.Model.Projection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AdminCreateProjectionFrame extends JFrame {
    private JComboBox<String> movieComboBox;
    private JTextField projectionDateField;
    private JTextField projectionTimeField;
    private JTextField hallField;
    private JTextField availableSeatsField; // Nuovo campo
    private JButton saveButton;

    public AdminCreateProjectionFrame() {
        setTitle("Create Projection");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Movie:"), gbc);

        movieComboBox = new JComboBox<>();
        loadMovies();
        gbc.gridx = 1;
        panel.add(movieComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Projection Date (YYYY-MM-DD):"), gbc);
        projectionDateField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(projectionDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Projection Time (HH:MM:SS):"), gbc);
        projectionTimeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(projectionTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Hall:"), gbc);
        hallField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(hallField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Available Seats:"), gbc); // Nuova etichetta
        availableSeatsField = new JTextField(20); // Nuovo campo
        gbc.gridx = 1;
        panel.add(availableSeatsField, gbc);

        saveButton = new JButton("Save Projection");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        add(panel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            JOptionPane.showMessageDialog(this, "Error loading movies: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveProjection() {
        String selectedMovie = (String) movieComboBox.getSelectedItem();
        int movieId = Integer.parseInt(selectedMovie.split(":")[0].trim());
        String projectionDate = projectionDateField.getText();
        String projectionTime = projectionTimeField.getText();
        String hall = hallField.getText();
        int availableSeats = Integer.parseInt(availableSeatsField.getText()); // Nuovo campo

        Projection projection = new Projection();
        projection.setMovieId(movieId);
        projection.setProjectionDate(projectionDate);
        projection.setProjectionTime(projectionTime);
        projection.setHall(hall);
        projection.setAvailableSeats(availableSeats); // Nuovo campo

        ProjectionController projectionController = new ProjectionController();
        try {
            projectionController.createProjection(projection);
            JOptionPane.showMessageDialog(this, "Projection created successfully!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating projection: " + ex.getMessage());
        }
    }
}
