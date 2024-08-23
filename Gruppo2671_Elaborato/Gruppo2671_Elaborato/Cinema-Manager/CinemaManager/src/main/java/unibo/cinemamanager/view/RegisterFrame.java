package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.UserController;
import unibo.cinemamanager.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
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

    public RegisterFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setTitle("User Registration");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        genresField = new JTextField(20);
        priorityLevelField = new JTextField(20);
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
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Priority Level:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priorityLevelField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("User Type:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(userTypeComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
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
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        viewUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewUsersFrame(RegisterFrame.this).setVisible(true);
                setVisible(false); // Hide the current frame
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.setVisible(true);
                dispose();
            }
        });

        setLocationRelativeTo(null); // Centra la finestra sullo schermo
    }

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

