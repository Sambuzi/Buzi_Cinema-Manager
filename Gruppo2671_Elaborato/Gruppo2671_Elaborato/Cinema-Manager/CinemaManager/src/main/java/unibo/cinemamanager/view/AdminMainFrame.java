package unibo.cinemamanager.view;

import unibo.cinemamanager.WelcomeFrame;
import unibo.cinemamanager.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMainFrame extends JFrame {
    private JButton createMovieButton;
    private JButton createProjectionButton;
    private JButton viewMoviesButton;
    private JButton viewProjectionsButton;
    private JButton manageUsersButton;
    private JButton createHallButton; // Pulsante per creare una nuova sala
    private JButton exitButton; // Pulsante per uscire
    private User admin;

    public AdminMainFrame(User admin) {
        this.admin = admin;

        setTitle("Admin Dashboard");
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
        JLabel mainTitleLabel = new JLabel("Cinema Management System", SwingConstants.CENTER);
        mainTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainTitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        topPanel.add(mainTitleLabel, BorderLayout.NORTH);

        // Etichetta di benvenuto
        JLabel welcomeLabel = new JLabel("Benvenuto, " + admin.getFirstName(), SwingConstants.RIGHT);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        topPanel.add(welcomeLabel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10)); // Aggiornato per 6 righe
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        createMovieButton = new JButton("Create Movie");
        createHallButton = new JButton("Create Hall"); // Pulsante per creare una nuova sala
        createProjectionButton = new JButton("Create Projection");
        viewMoviesButton = new JButton("View Movies");
        viewProjectionsButton = new JButton("View Projections");
        manageUsersButton = new JButton("Manage Users");

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        createMovieButton.setFont(buttonFont);
        createProjectionButton.setFont(buttonFont);
        createHallButton.setFont(buttonFont); // Imposta il font per il pulsante createHallButton
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

        createMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminCreateMovieFrame().setVisible(true);
            }
        });

        createProjectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminCreateProjectionFrame().setVisible(true);
            }
        });

        createHallButton.addActionListener(new ActionListener() { // Aggiunge l'azione per il pulsante createHallButton
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminCreateHallFrame().setVisible(true);
            }
        });

        viewMoviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminViewMoviesFrame(AdminMainFrame.this).setVisible(true);
            }
        });

        viewProjectionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminViewProjectionsFrame(AdminMainFrame.this).setVisible(true);
            }
        });

        manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminManageUsersFrame(AdminMainFrame.this).setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiudi AdminMainFrame
                new WelcomeFrame().setVisible(true); // Apri WelcomeFrame
            }
        });
    }
}
