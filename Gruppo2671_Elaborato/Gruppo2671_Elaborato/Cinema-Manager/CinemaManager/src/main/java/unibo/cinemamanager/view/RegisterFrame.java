package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.UserController;
import unibo.cinemamanager.Model.User;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * This class represents the registration frame for new users.
 */
public class RegisterFrame extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 500;
    private static final int INSET_VALUE = 5;
    private static final int FIELD_COLUMNS = 20;
    private static final int BUTTON_GAP = 10;
    private static final int PRIORITY_LEVEL_INDEX = 5;
    private static final int USER_TYPE_INDEX = 6;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField genresField;
    private JTextField priorityLevelField;
    private JComboBox<String> userTypeComboBox;
    private JButton registerButton;
    private JButton viewUsersButton;
    private JButton backButton;
    private JFrame parentFrame;

    /**
     * Constructs the RegisterFrame with a reference to the parent frame.
     *
     * @param parentFrame the parent frame, must be final.
     */
    public RegisterFrame(final JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setTitle("User Registration");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET_VALUE, INSET_VALUE, INSET_VALUE, INSET_VALUE);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        firstNameField = new JTextField(FIELD_COLUMNS);
        lastNameField = new JTextField(FIELD_COLUMNS);
        emailField = new JTextField(FIELD_COLUMNS);
        passwordField = new JPasswordField(FIELD_COLUMNS);
        genresField = new JTextField(FIELD_COLUMNS);
        priorityLevelField = new JTextField(FIELD_COLUMNS);
        userTypeComboBox = new JComboBox<>(new String[]{"User", "Admin"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Genres:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(genresField, gbc);

        gbc.gridx = 0;
        gbc.gridy = PRIORITY_LEVEL_INDEX;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Priority Level:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priorityLevelField, gbc);

        gbc.gridx = 0;
        gbc.gridy = USER_TYPE_INDEX;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("User Type:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(userTypeComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, BUTTON_GAP, BUTTON_GAP));
        registerButton = new JButton("Register");
        viewUsersButton = new JButton("View Users");
        backButton = new JButton("Back");

        buttonPanel.add(registerButton);
        buttonPanel.add(viewUsersButton);
        buttonPanel.add(backButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                registerUser();
            }
        });

        viewUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new ViewUsersFrame(RegisterFrame.this).setVisible(true);
                setVisible(false); // Hide the current frame
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                parentFrame.setVisible(true);
                dispose();
            }
        });

        setLocationRelativeTo(null); // Center the window on the screen
    }

    /**
     * Registers a new user based on the input fields.
     */
    private void registerUser() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String genres = genresField.getText();
        int priorityLevel = Integer.parseInt(priorityLevelField.getText());
        String userType = (String) userTypeComboBox.getSelectedItem();

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPreferredGenres(genres);
        user.setPriorityLevel(priorityLevel);
        user.setUserType(userType);

        UserController userController = new UserController();
        try {
            userController.registerUser(user);
            JOptionPane.showMessageDialog(this, "User registered successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error registering user: " + ex.getMessage());
        }
    }
}
