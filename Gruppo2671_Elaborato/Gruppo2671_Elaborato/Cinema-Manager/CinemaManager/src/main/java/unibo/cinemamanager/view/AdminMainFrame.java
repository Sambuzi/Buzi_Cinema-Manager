package unibo.cinemamanager.view;

import unibo.cinemamanager.WelcomeFrame;
import unibo.cinemamanager.Model.User;

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
 * The AdminMainFrame class provides a GUI for the admin dashboard.
 * Admins can manage movies, projections, users, and halls from this frame.
 */
public class AdminMainFrame extends JFrame {

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 500;
    private static final int PADDING = 20;
    private static final int TOP_TITLE_FONT_SIZE = 24;
    private static final int WELCOME_LABEL_FONT_SIZE = 18;
    private static final int BUTTON_FONT_SIZE = 18;
    private static final int BUTTON_GRID_ROWS = 6;
    private static final int BUTTON_GRID_COLS = 1;
    private static final int BUTTON_GRID_GAP = 10;
    private static final int TITLE_TOP_PADDING = 10;
    private static final int TITLE_BOTTOM_PADDING = 20;
    private static final int EXIT_BUTTON_RED = 70;
    private static final int EXIT_BUTTON_GREEN = 130;
    private static final int EXIT_BUTTON_BLUE = 180;

    private JButton createMovieButton;
    private JButton createProjectionButton;
    private JButton viewMoviesButton;
    private JButton viewProjectionsButton;
    private JButton manageUsersButton;
    private JButton createHallButton;
    private JButton exitButton;
    private final User admin;

    /**
     * Constructor for the AdminMainFrame class.
     *
     * @param admin the admin user accessing the dashboard
     */
    public AdminMainFrame(final User admin) {
        this.admin = admin;

        setTitle("Admin Dashboard");
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
        JLabel mainTitleLabel = new JLabel("Cinema Management System", SwingConstants.CENTER);
        mainTitleLabel.setFont(new Font("Arial", Font.BOLD, TOP_TITLE_FONT_SIZE));
        mainTitleLabel.setBorder(BorderFactory.createEmptyBorder(TITLE_TOP_PADDING, 0, TITLE_BOTTOM_PADDING, 0));
        topPanel.add(mainTitleLabel, BorderLayout.NORTH);

        // Etichetta di benvenuto
        JLabel welcomeLabel = new JLabel("Benvenuto, " + admin.getFirstName(), SwingConstants.RIGHT);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, WELCOME_LABEL_FONT_SIZE));
        topPanel.add(welcomeLabel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(BUTTON_GRID_ROWS, BUTTON_GRID_COLS, BUTTON_GRID_GAP, BUTTON_GRID_GAP));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        buttonPanel.setBackground(Color.WHITE);

        createMovieButton = new JButton("Create Movie");
        createHallButton = new JButton("Create Hall");
        createProjectionButton = new JButton("Create Projection");
        viewMoviesButton = new JButton("View Movies");
        viewProjectionsButton = new JButton("View Projections");
        manageUsersButton = new JButton("Manage Users");

        Font buttonFont = new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE);
        createMovieButton.setFont(buttonFont);
        createProjectionButton.setFont(buttonFont);
        createHallButton.setFont(buttonFont);
        viewMoviesButton.setFont(buttonFont);
        viewProjectionsButton.setFont(buttonFont);
        manageUsersButton.setFont(buttonFont);

        buttonPanel.add(createMovieButton);
        buttonPanel.add(createHallButton);
        buttonPanel.add(createProjectionButton);
        buttonPanel.add(viewMoviesButton);
        buttonPanel.add(viewProjectionsButton);
        buttonPanel.add(manageUsersButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(TITLE_TOP_PADDING, 0, 0, 0));
        bottomPanel.setBackground(Color.WHITE);

        exitButton = new JButton("Exit");
        exitButton.setFont(buttonFont);
        exitButton.setBackground(new Color(EXIT_BUTTON_RED, EXIT_BUTTON_GREEN, EXIT_BUTTON_BLUE));
        exitButton.setForeground(Color.BLACK);

        bottomPanel.add(exitButton, BorderLayout.EAST);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        createMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new AdminCreateMovieFrame().setVisible(true);
            }
        });

        createProjectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new AdminCreateProjectionFrame().setVisible(true);
            }
        });

        createHallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new AdminCreateHallFrame().setVisible(true);
            }
        });

        viewMoviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new AdminViewMoviesFrame(AdminMainFrame.this).setVisible(true);
            }
        });

        viewProjectionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new AdminViewProjectionsFrame(AdminMainFrame.this).setVisible(true);
            }
        });

        manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new AdminManageUsersFrame(AdminMainFrame.this).setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
                new WelcomeFrame().setVisible(true);
            }
        });
    }
}
