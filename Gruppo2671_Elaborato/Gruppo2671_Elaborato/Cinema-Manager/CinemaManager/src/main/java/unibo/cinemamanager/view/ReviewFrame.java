package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.MovieController;
import unibo.cinemamanager.controller.ReviewController;
import unibo.cinemamanager.Model.Movie;
import unibo.cinemamanager.Model.Review;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReviewFrame extends JFrame {
    private JTextField userIdField;
    private JComboBox<String> movieComboBox; // Campo per il nome del film
    private JTextField ratingField;
    private JTextArea commentArea;
    private JButton submitButton;
    private JButton deleteButton;
    private JButton backButton;
    private JTable reviewsTable;
    private DefaultTableModel tableModel;
    private UserMainFrame userMainFrame;
    private int userId;

    public ReviewFrame(UserMainFrame userMainFrame, int userId) {
        this.userMainFrame = userMainFrame;
        this.userId = userId;

        setTitle("Leave a Review");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("User ID:"), gbc);
        userIdField = new JTextField();
        userIdField.setText(String.valueOf(userId)); // Imposta l'ID utente
        userIdField.setEditable(false); // Rendi il campo non modificabile
        gbc.gridx = 1;
        panel.add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Movie:"), gbc);
        movieComboBox = new JComboBox<>();
        loadMovies();
        gbc.gridx = 1;
        panel.add(movieComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Rating (1-5):"), gbc);
        ratingField = new JTextField();
        gbc.gridx = 1;
        panel.add(ratingField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Comment:"), gbc);
        commentArea = new JTextArea(3, 20);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(commentArea), gbc);

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        submitButton = new JButton("Submit");
        deleteButton = new JButton("Delete Review");
        backButton = new JButton("Back");
        buttonPanel.add(submitButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel, BorderLayout.NORTH);

        // Tabella per mostrare le recensioni
        String[] columnNames = {"Review ID", "Movie", "Rating", "Comment", "Review Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reviewsTable = new JTable(tableModel);
        reviewsTable.setFillsViewportHeight(true);
        reviewsTable.setRowHeight(30);
        reviewsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        reviewsTable.setSelectionBackground(new Color(184, 207, 229));
        reviewsTable.setSelectionForeground(Color.BLACK);
        reviewsTable.setGridColor(Color.LIGHT_GRAY);
        add(new JScrollPane(reviewsTable), BorderLayout.CENTER);

        loadUserReviews(userId);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Review review = new Review();
                review.setUserId(Integer.parseInt(userIdField.getText()));

                String selectedMovie = (String) movieComboBox.getSelectedItem();
                int movieId = Integer.parseInt(selectedMovie.split(":")[0].trim());
                review.setMovieId(movieId);

                review.setRating(Integer.parseInt(ratingField.getText()));
                review.setComment(commentArea.getText());
                review.setReviewDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                ReviewController reviewController = new ReviewController();
                try {
                    reviewController.createReview(review);
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Review submitted successfully!");
                    loadUserReviews(userId); // Refresh the reviews table
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Error submitting review.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = reviewsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Please select a review to delete.");
                    return;
                }

                int reviewId = (Integer) tableModel.getValueAt(selectedRow, 0);
                ReviewController reviewController = new ReviewController();
                try {
                    reviewController.deleteReview(reviewId);
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Review deleted successfully!");
                    loadUserReviews(userId); // Refresh the reviews table
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Error deleting review.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                userMainFrame.setVisible(true);
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

    private void loadUserReviews(int userId) {
        ReviewController reviewController = new ReviewController();
        try {
            List<Review> reviews = reviewController.getReviewsByUserId(userId);
            tableModel.setRowCount(0); // Clear existing rows
            for (Review review : reviews) {
                Object[] rowData = {
                        review.getId(),
                        review.getMovieTitle(),
                        review.getRating(),
                        review.getComment(),
                        review.getReviewDate()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading reviews: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
