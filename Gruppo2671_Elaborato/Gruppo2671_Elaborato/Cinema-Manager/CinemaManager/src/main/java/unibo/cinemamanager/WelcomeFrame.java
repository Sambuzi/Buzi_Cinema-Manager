package unibo.cinemamanager;

import unibo.cinemamanager.controller.UserController;
import unibo.cinemamanager.Model.User;
import unibo.cinemamanager.view.AdminMainFrame;
import unibo.cinemamanager.view.UserMainFrame;
import unibo.cinemamanager.view.RegisterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class WelcomeFrame extends JFrame {
    private JButton adminButton;
    private JButton userButton;
    private JButton registerButton;

    public WelcomeFrame() {
        setTitle("Welcome Screen");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("path/to/your/background/image.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Test di connessione al database
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connessione al database riuscita!");
            }
        } catch (SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Errore di connessione al database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Termina l'applicazione
        }

        JLabel titleLabel = new JLabel("Cinema Manager", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        adminButton = new JButton("Admin");
        userButton = new JButton("Utente");
        registerButton = new JButton("Registrati");

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        adminButton.setFont(buttonFont);
        userButton.setFont(buttonFont);
        registerButton.setFont(buttonFont.deriveFont(14f)); // Font più piccolo per il pulsante "Registrati"

        adminButton.setBackground(new Color(70, 130, 180));
        adminButton.setForeground(Color.BLACK);
        userButton.setBackground(new Color(70, 130, 180));
        userButton.setForeground(Color.BLACK);
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(adminButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(userButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(registerButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAdminLogin();
            }
        });

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterFrame();
            }
        });
    }

    private void showAdminLogin() {
        String email = JOptionPane.showInputDialog(this, "Enter Admin Email:");
        String password = JOptionPane.showInputDialog(this, "Enter Admin Password:");
        UserController userController = new UserController();
        try {
            User user = userController.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password) && "Admin".equals(user.getUserType())) {
                JOptionPane.showMessageDialog(this, "Admin access successful.");
                dispose();
                new AdminMainFrame(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin credentials.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error accessing user data: " + e.getMessage());
        }
    }

    private void showUserLogin() {
        String email = JOptionPane.showInputDialog(this, "Enter User Email:");
        String password = JOptionPane.showInputDialog(this, "Enter User Password:");
        UserController userController = new UserController();
        try {
            User user = userController.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password) && "User".equals(user.getUserType())) {
                JOptionPane.showMessageDialog(this, "User access successful.");
                dispose();
                new UserMainFrame(user.getId(), user.getFirstName()).setVisible(true); // Pass userId and userName to UserMainFrame
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User credentials.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error accessing user data: " + e.getMessage());
        }
    }

    private void showRegisterFrame() {
        dispose();
        new RegisterFrame(WelcomeFrame.this).setVisible(true);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomeFrame().setVisible(true);
            }
        });
    }
}
