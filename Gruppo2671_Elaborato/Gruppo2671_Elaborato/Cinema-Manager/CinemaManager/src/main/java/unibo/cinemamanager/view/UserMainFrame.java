package unibo.cinemamanager.view;

import unibo.cinemamanager.WelcomeFrame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The UserMainFrame class provides a GUI for the user dashboard.
 * Users can view movies, projections, book seats, and leave reviews from this frame.
 */
public class UserMainFrame extends JFrame {

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 500;
    private static final int PADDING = 20;
    private static final int TOP_TITLE_FONT_SIZE = 24;
    private static final int WELCOME_LABEL_FONT_SIZE = 18;
    private static final int BUTTON_FONT_SIZE = 18;
    private static final int BUTTON_GRID_ROWS = 5;
    private static final int BUTTON_GRID_COLS = 1;
    private static final int BUTTON_GRID_GAP = 10;
    private static final int TITLE_LABEL_TOP_PADDING = 10;
    private static final int TITLE_LABEL_BOTTOM_PADDING = 20;
    private static final Color EXIT_BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color EXIT_BUTTON_TEXT_COLOR = Color.BLACK;

    private final int userId;
    private final String userName;

    private JButton viewReviewsButton;
    private JButton moviesButton;
    private JButton projectionsButton;
    private JButton bookingsButton;
    private JButton reviewsButton;
    private JButton exitButton;

    /**
     * Constructor for the UserMainFrame class.
     *
     * @param userId   the ID of the user accessing the dashboard
     * @param userName the name of the user
     */
    public UserMainFrame(final int userId, final String userName) {
        this.userId = userId;
        this.userName = userName;

        setTitle("Cinema Management System");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout(PADDING, PADDING));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        mainPanel.setBackground(Color.WHITE);

        // Pannello superiore per la scritta principale e l'etichetta di benvenuto
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Scritta principale
        JLabel mainTitleLabel = new JLabel("Cinema Management System", JLabel.CENTER);
        mainTitleLabel.setFont(new Font("Arial", Font.BOLD, TOP_TITLE_FONT_SIZE));
        mainTitleLabel.setBorder(BorderFactory.createEmptyBorder(TITLE_LABEL_TOP_PADDING, 0, TITLE_LABEL_BOTTOM_PADDING, 0));
        topPanel.add(mainTitleLabel, BorderLayout.NORTH);

        // Etichetta di benvenuto
        JLabel welcomeLabel = new JLabel("Benvenuto, " + userName, SwingConstants.RIGHT);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, WELCOME_LABEL_FONT_SIZE));
        topPanel.add(welcomeLabel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(BUTTON_GRID_ROWS, BUTTON_GRID_COLS, BUTTON_GRID_GAP, BUTTON_GRID_GAP));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        viewReviewsButton = new JButton("View Reviews");
        moviesButton = new JButton("View Movies");
        projectionsButton = new JButton("View Projections");
        bookingsButton = new JButton("Book Seats");
        reviewsButton = new JButton("Leave a Review");

        Font buttonFont = new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE);
        viewReviewsButton.setFont(buttonFont);
        moviesButton.setFont(buttonFont);
        projectionsButton.setFont(buttonFont);
        bookingsButton.setFont(buttonFont);
        reviewsButton.setFont(buttonFont);

        buttonPanel.add(viewReviewsButton);
        buttonPanel.add(moviesButton);
        buttonPanel.add(projectionsButton);
        buttonPanel.add(bookingsButton);
        buttonPanel.add(reviewsButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomPanel.setBackground(Color.WHITE);

        exitButton = new JButton("Exit");
        exitButton.setFont(buttonFont);
        exitButton.setBackground(EXIT_BUTTON_COLOR);
        exitButton.setForeground(EXIT_BUTTON_TEXT_COLOR);

        bottomPanel.add(exitButton, BorderLayout.EAST);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        viewReviewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new ViewReviewsFrame(UserMainFrame.this).setVisible(true);
            }
        });

        moviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new MoviesFrame(UserMainFrame.this).setVisible(true);
            }
        });

        projectionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setVisible(false);
                new ProjectionFrame(UserMainFrame.this).setVisible(true);
            }
        });

        bookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setVisible(false);
                new BookingFrame(UserMainFrame.this, userId).setVisible(true);
            }
        });

        reviewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setVisible(false);
                new ReviewFrame(UserMainFrame.this, userId).setVisible(true); // Passa l'ID utente
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose(); // Chiudi UserMainFrame
                new WelcomeFrame().setVisible(true); // Apri WelcomeFrame
            }
        });
    }
}
