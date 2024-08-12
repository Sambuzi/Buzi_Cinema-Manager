package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.ReviewController;
import unibo.cinemamanager.Model.Review;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ViewReviewsFrame extends JFrame {
    private JTable reviewsTable;
    private DefaultTableModel tableModel;
    private JTextField searchTextField;
    private JComboBox<String> ratingComboBox;
    private JButton backButton; // Pulsante Back
    private UserMainFrame userMainFrame; // Riferimento a UserMainFrame

    public ViewReviewsFrame(UserMainFrame userMainFrame) {
        this.userMainFrame = userMainFrame; // Inizializzazione del riferimento a UserMainFrame

        setTitle("View Reviews");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pannello di ricerca
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Search:"));
        searchTextField = new JTextField(15);
        searchPanel.add(searchTextField);
        searchPanel.add(new JLabel("Rating:"));
        ratingComboBox = new JComboBox<>(new String[]{"All", "Best", "Worst"});
        searchPanel.add(ratingComboBox);

        JButton searchButton = new JButton("Filter");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Colonne della tabella
        String[] columnNames = {"Username", "Movie Title", "Rating", "Comment", "Review Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reviewsTable = new JTable(tableModel);
        reviewsTable.setFillsViewportHeight(true);
        reviewsTable.setRowHeight(30);
        reviewsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        reviewsTable.setSelectionBackground(new Color(184, 207, 229));
        reviewsTable.setSelectionForeground(Color.BLACK);
        reviewsTable.setGridColor(Color.LIGHT_GRAY);

        // Personalizza l'intestazione della tabella
        JTableHeader header = reviewsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.BLACK);
        header.setOpaque(true);
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Recupera i dati delle recensioni dal database e popolano la tabella
        loadReviews();

        JScrollPane scrollPane = new JScrollPane(reviewsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Pannello dei pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiudi il frame corrente
                userMainFrame.setVisible(true); // Torna a UserMainFrame
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
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                boolean matchesSearch = true;
                boolean matchesRating = true;

                if (!searchText.isEmpty()) {
                    String username = entry.getStringValue(0).toLowerCase();
                    String movieTitle = entry.getStringValue(1).toLowerCase();
                    String comment = entry.getStringValue(3).toLowerCase();
                    matchesSearch = username.contains(searchText) || movieTitle.contains(searchText) || comment.contains(searchText);
                }

                if (!"All".equals(ratingFilter)) {
                    int rating = Integer.parseInt(entry.getStringValue(2));
                    if ("Best".equals(ratingFilter)) {
                        matchesRating = rating >= 4;
                    } else if ("Worst".equals(ratingFilter)) {
                        matchesRating = rating <= 2;
                    }
                }

                return matchesSearch && matchesRating;
            }
        });
    }
}

