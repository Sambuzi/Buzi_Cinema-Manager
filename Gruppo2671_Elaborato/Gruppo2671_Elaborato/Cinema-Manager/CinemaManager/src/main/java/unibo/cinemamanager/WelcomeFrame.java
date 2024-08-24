package unibo.cinemamanager;

import unibo.cinemamanager.controller.UserController;
import unibo.cinemamanager.Model.User;
import unibo.cinemamanager.view.AdminMainFrame;
import unibo.cinemamanager.view.UserMainFrame;
import unibo.cinemamanager.view.RegisterFrame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The WelcomeFrame class represents the main entry point of the Cinema Manager application.
 * It displays options for admin login, user login, and new user registration.
 */
public class WelcomeFrame extends JFrame {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 400;
    private static final int PADDING = 20;
    private static final int INSET = 10;
    private static final int BUTTON_FONT_SIZE = 18;
    private static final int TITLE_FONT_SIZE = 32;
    private static final int TITLE_TOP_PADDING = 10;
    private static final int TITLE_BOTTOM_PADDING = 20;
    private static final float REGISTER_BUTTON_FONT_SIZE = 14f;
    private static final Color BUTTON_BACKGROUND_COLOR = new Color(70, 130, 180);
    private static final String BACKGROUND_IMAGE_PATH = "path/to/your/background/image.jpg";

    private JButton adminButton;
    private JButton userButton;
    private JButton registerButton;

    /**
     * Constructor for the WelcomeFrame class.
     * Initializes the GUI components and sets up event handling.
     */
    public WelcomeFrame() {
        setTitle("Welcome Screen");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(INSET, INSET));
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(BACKGROUND_IMAGE_PATH);
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Test di connessione al database
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connessione al database riuscita!");
            }
        } catch (SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Errore di connessione al database: " + e.getMessage(), 
                "Errore", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Termina l'applicazione
        }

        JLabel titleLabel = new JLabel("Cinema Manager", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(TITLE_TOP_PADDING, 0, TITLE_BOTTOM_PADDING, 0));

        adminButton = new JButton("Admin");
        userButton = new JButton("Utente");
        registerButton = new JButton("Registrati");

        Font buttonFont = new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE);
        adminButton.setFont(buttonFont);
        userButton.setFont(buttonFont);
        registerButton.setFont(buttonFont.deriveFont(REGISTER_BUTTON_FONT_SIZE)); 

        adminButton.setBackground(BUTTON_BACKGROUND_COLOR);
        adminButton.setForeground(Color.BLACK);
        userButton.setBackground(BUTTON_BACKGROUND_COLOR);
        userButton.setForeground(Color.BLACK);
        registerButton.setBackground(BUTTON_BACKGROUND_COLOR);
        registerButton.setForeground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET, INSET, INSET, INSET);

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
            public void actionPerformed(final ActionEvent e) {
                showAdminLogin();
            }
        });

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                showUserLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                showRegisterFrame();
            }
        });
    }

    /**
     * Displays the admin login dialog.
     */
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

    /**
     * Displays the user login dialog.
     */
    private void showUserLogin() {
        String email = JOptionPane.showInputDialog(this, "Enter User Email:");
        String password = JOptionPane.showInputDialog(this, "Enter User Password:");
        UserController userController = new UserController();
        try {
            User user = userController.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password) && "User".equals(user.getUserType())) {
                JOptionPane.showMessageDialog(this, "User access successful.");
                dispose();
                new UserMainFrame(user.getId(), user.getFirstName()).setVisible(true); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User credentials.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error accessing user data: " + e.getMessage());
        }
    }

    /**
     * Displays the registration frame for new users.
     */
    private void showRegisterFrame() {
        dispose();
        new RegisterFrame(WelcomeFrame.this).setVisible(true);
    }

    /**
     * Main method to launch the WelcomeFrame.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomeFrame().setVisible(true);
            }
        });
    }
}
