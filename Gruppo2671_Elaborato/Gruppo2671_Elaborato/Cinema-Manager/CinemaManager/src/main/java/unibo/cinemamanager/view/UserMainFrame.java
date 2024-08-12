package unibo.cinemamanager.view;

import unibo.cinemamanager.WelcomeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMainFrame extends JFrame {
    private int userId;
    private String userName; // Nome dell'utente

    private JButton viewReviewsButton;
    private JButton moviesButton;
    private JButton projectionsButton;
    private JButton bookingsButton;
    private JButton reviewsButton;
    private JButton exitButton; // Pulsante per uscire

    public UserMainFrame(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;

        setTitle("Cinema Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Pannello superiore per la scritta principale e l'etichetta di benvenuto
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Scritta principale
        JLabel mainTitleLabel = new JLabel("Cinema Management System", JLabel.CENTER);
        mainTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        topPanel.add(mainTitleLabel, BorderLayout.NORTH);

        // Etichetta di benvenuto
        JLabel welcomeLabel = new JLabel("Benvenuto, " + userName, SwingConstants.RIGHT);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        topPanel.add(welcomeLabel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        viewReviewsButton = new JButton("View Reviews");
        moviesButton = new JButton("View Movies");
        projectionsButton = new JButton("View Projections");
        bookingsButton = new JButton("Book Seats");
        reviewsButton = new JButton("Leave a Review");

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
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
        exitButton.setBackground(new Color(70, 130, 180));
        exitButton.setForeground(Color.BLACK);

        bottomPanel.add(exitButton, BorderLayout.EAST);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        viewReviewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewReviewsFrame(UserMainFrame.this).setVisible(true);
            }
        });

        moviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MoviesFrame(UserMainFrame.this).setVisible(true);
            }
        });

        projectionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ProjectionFrame(UserMainFrame.this).setVisible(true);
            }
        });

        bookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new BookingFrame(UserMainFrame.this, userId).setVisible(true);
            }
        });

        reviewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ReviewFrame(UserMainFrame.this, userId).setVisible(true); // Passa l'ID utente
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiudi UserMainFrame
                new WelcomeFrame().setVisible(true); // Apri WelcomeFrame
            }
        });
    }
}
