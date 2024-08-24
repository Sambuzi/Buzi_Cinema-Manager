package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.ReviewController;
import unibo.cinemamanager.Model.Review;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * ViewReviewsFrame provides a UI for viewing and filtering movie reviews.
 */
public class ViewReviewsFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int BORDER_PADDING = 20;
    private static final int ROW_HEIGHT = 30;
    private static final int TEXT_FIELD_COLUMNS = 15;
    private static final int HEADER_FONT_SIZE = 16;
    private static final int BUTTON_FONT_SIZE = 14;
    private static final int SEARCH_PANEL_PADDING = 10;
    private static final int RATING_BEST_THRESHOLD = 4;
    private static final int RATING_WORST_THRESHOLD = 2;

    private static final Color SELECTION_BACKGROUND_COLOR = new Color(184, 207, 229);
    private static final Color HEADER_BACKGROUND_COLOR = new Color(70, 130, 180);
    private static final Color HEADER_FOREGROUND_COLOR = Color.BLACK;

    private JTable reviewsTable;
    private DefaultTableModel tableModel;
    private JTextField searchTextField;
    private JComboBox<String> ratingComboBox;
    private JButton backButton;
    private final UserMainFrame userMainFrame;

    /**
     * Constructs a ViewReviewsFrame with a reference to the UserMainFrame.
     *
     * @param userMainFrame the UserMainFrame reference
     */
    public ViewReviewsFrame(final UserMainFrame userMainFrame) {
        this.userMainFrame = userMainFrame;

        setTitle("View Reviews");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(SEARCH_PANEL_PADDING, SEARCH_PANEL_PADDING));
        Border padding = BorderFactory.createEmptyBorder(BORDER_PADDING, BORDER_PADDING, BORDER_PADDING, BORDER_PADDING);
        panel.setBorder(padding);
        panel.setBackground(Color.WHITE);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, SEARCH_PANEL_PADDING, SEARCH_PANEL_PADDING));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Search:"));
        searchTextField = new JTextField(TEXT_FIELD_COLUMNS);
        searchPanel.add(searchTextField);
        searchPanel.add(new JLabel("Rating:"));
        ratingComboBox = new JComboBox<>(new String[]{"All", "Best", "Worst"});
        searchPanel.add(ratingComboBox);

        JButton searchButton = new JButton("Filter");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                applyFilters();
            }
        });
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Table columns
        String[] columnNames = {"Username", "Movie Title", "Rating", "Comment", "Review Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reviewsTable = new JTable(tableModel);
        reviewsTable.setFillsViewportHeight(true);
        reviewsTable.setRowHeight(ROW_HEIGHT);
        reviewsTable.setFont(new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE));
        reviewsTable.setSelectionBackground(SELECTION_BACKGROUND_COLOR);
        reviewsTable.setSelectionForeground(Color.BLACK);
        reviewsTable.setGridColor(Color.LIGHT_GRAY);

        // Customize table header
        JTableHeader header = reviewsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, HEADER_FONT_SIZE));
        header.setBackground(HEADER_BACKGROUND_COLOR);
        header.setForeground(HEADER_FOREGROUND_COLOR);
        header.setOpaque(true);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Load reviews from the database into the table
        loadReviews();

        JScrollPane scrollPane = new JScrollPane(reviewsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, SEARCH_PANEL_PADDING, SEARCH_PANEL_PADDING));
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
                userMainFrame.setVisible(true);
            }
        });
    }

    private void loadReviews() {
        ReviewController reviewController = new ReviewController();
        try {
            List<Review> reviews = reviewController.getAllReviews();
            for (Review review : reviews) {
                Object[] rowData = {
                    review.getUsername(),
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

    private void applyFilters() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        reviewsTable.setRowSorter(sorter);

        String searchText = searchTextField.getText().toLowerCase();
        String ratingFilter = (String) ratingComboBox.getSelectedItem();

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(final Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                boolean matchesSearch = true;
                boolean matchesRating = true;

                if (!searchText.isEmpty()) {
                    String username = entry.getStringValue(0).toLowerCase();
                    String movieTitle = entry.getStringValue(1).toLowerCase();
                    String comment = entry.getStringValue(3).toLowerCase();
                    matchesSearch = username.contains(searchText) 
                    || movieTitle.contains(searchText) || comment.contains(searchText);
                }

                if (!"All".equals(ratingFilter)) {
                    int rating = Integer.parseInt(entry.getStringValue(2));
                    if ("Best".equals(ratingFilter)) {
                        matchesRating = rating >= RATING_BEST_THRESHOLD;
                    } else if ("Worst".equals(ratingFilter)) {
                        matchesRating = rating <= RATING_WORST_THRESHOLD;
                    }
                }

                return matchesSearch && matchesRating;
            }
        });
    }
}
