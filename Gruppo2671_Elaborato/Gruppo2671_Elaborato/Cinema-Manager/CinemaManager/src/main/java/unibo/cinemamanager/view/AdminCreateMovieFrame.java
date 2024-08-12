package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.MovieController;
import unibo.cinemamanager.Model.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminCreateMovieFrame extends JFrame {
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField releaseDateField;
    private JTextField genreField;
    private JTextField durationField;
    private JButton saveButton;

    public AdminCreateMovieFrame() {
        setTitle("Create Movie");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Title:"), gbc);
        titleField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Description:"), gbc);
        descriptionField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Release Date (YYYY-MM-DD):"), gbc);
        releaseDateField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(releaseDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Genre:"), gbc);
        genreField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(genreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Duration (minutes):"), gbc);
        durationField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(durationField, gbc);

        saveButton = new JButton("Save Movie");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        add(panel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMovie();
            }
        });
    }

    private void saveMovie() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String releaseDate = releaseDateField.getText();
        String genre = genreField.getText();
        int duration = Integer.parseInt(durationField.getText());

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setReleaseDate(releaseDate);
        movie.setGenre(genre);
        movie.setDuration(duration);

        MovieController movieController = new MovieController();
        try {
            movieController.createMovie(movie);
            JOptionPane.showMessageDialog(this, "Movie created successfully!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating movie: " + ex.getMessage());
        }
    }
}
