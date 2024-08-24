package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.MovieController;
import unibo.cinemamanager.controller.ReviewController;
import unibo.cinemamanager.Model.Movie;
import unibo.cinemamanager.Model.Review;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class represents the frame where users can leave and manage their reviews.
 */
public class ReviewFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int INSET_SIZE = 5;
    private static final int COMMENT_AREA_ROWS = 3;
    private static final int COMMENT_AREA_COLUMNS = 20;
    private static final int ROW_HEIGHT = 30;
    private static final int FONT_SIZE = 14;
    private static final Color SELECTION_BG_COLOR = new Color(184, 207, 229);
    private static final Color HEADER_BG_COLOR = new Color(70, 130, 180);

    private JTextField userIdField;
    private JComboBox<String> movieComboBox;
    private JTextField ratingField;
    private JTextArea commentArea;
    private JButton submitButton;
    private JButton deleteButton;
    private JButton backButton;
    private JTable reviewsTable;
    private DefaultTableModel tableModel;
    private UserMainFrame userMainFrame;
    private int userId;

    /**
     * Constructs the ReviewFrame for a specific user.
     *
     * @param userMainFrame the main frame of the user, must be final.
     * @param userId the ID of the user, must be final.
     */
    public ReviewFrame(final UserMainFrame userMainFrame, final int userId) {
        this.userMainFrame = userMainFrame;
        this.userId = userId;

        setTitle("Leave a Review");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET_SIZE, INSET_SIZE, INSET_SIZE, INSET_SIZE);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("User ID:"), gbc);
        userIdField = new JTextField();
        userIdField.setText(String.valueOf(userId));
        userIdField.setEditable(false);
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
        commentArea = new JTextArea(COMMENT_AREA_ROWS, COMMENT_AREA_COLUMNS);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(commentArea), gbc);

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

        String[] columnNames = {"Review ID", "Movie", "Rating", "Comment", "Review Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reviewsTable = new JTable(tableModel);
        reviewsTable.setFillsViewportHeight(true);
        reviewsTable.setRowHeight(ROW_HEIGHT);
        reviewsTable.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        reviewsTable.setSelectionBackground(SELECTION_BG_COLOR);
        reviewsTable.setSelectionForeground(Color.BLACK);
        reviewsTable.setGridColor(Color.LIGHT_GRAY);
        add(new JScrollPane(reviewsTable), BorderLayout.CENTER);

        loadUserReviews(userId);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                submitReview();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                deleteReview();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
                userMainFrame.setVisible(true);
            }
        });
    }

    /**
     * Loads the movies into the movieComboBox from the database.
     */
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

    /**
     * Loads the user's reviews into the reviews table.
     *
     * @param userId the ID of the user whose reviews are to be loaded, must be final.
     */
    private void loadUserReviews(final int userId) {
        ReviewController reviewController = new ReviewController();
        try {
            List<Review> reviews = reviewController.getReviewsByUserId(userId);
            tableModel.setRowCount(0);
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

    /**
     * Submits a new review based on the user's input.
     */
    private void submitReview() {
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
            JOptionPane.showMessageDialog(this, "Review submitted successfully!");
            loadUserReviews(userId);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting review.");
        }
    }

    /**
     * Deletes the selected review from the table.
     */
    private void deleteReview() {
        int selectedRow = reviewsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a review to delete.");
            return;
        }

        int reviewId = (Integer) tableModel.getValueAt(selectedRow, 0);
        ReviewController reviewController = new ReviewController();
        try {
            reviewController.deleteReview(reviewId);
            JOptionPane.showMessageDialog(this, "Review deleted successfully!");
            loadUserReviews(userId);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting review.");
        }
    }
}
