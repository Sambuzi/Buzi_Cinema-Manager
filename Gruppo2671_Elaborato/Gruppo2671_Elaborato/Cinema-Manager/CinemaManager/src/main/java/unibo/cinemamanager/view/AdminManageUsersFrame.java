package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.UserController;
import unibo.cinemamanager.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AdminManageUsersFrame extends JFrame {
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton backButton; // Dichiarazione del pulsante Back
    private AdminMainFrame adminMainFrame; // Riferimento a AdminMainFrame

    public AdminManageUsersFrame(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame; // Inizializzazione di AdminMainFrame

        setTitle("Manage Users");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Colonne della tabella
        String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Preferred Genres", "Priority Level", "User Type"};
        tableModel = new DefaultTableModel(columnNames, 0);
        usersTable = new JTable(tableModel);
        loadUsers();

        JScrollPane scrollPane = new JScrollPane(usersTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        backButton = new JButton("Back"); // Inizializzazione del pulsante Back

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton); // Aggiunta del pulsante Back al pannello

        add(buttonPanel, BorderLayout.SOUTH);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUsers();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiudi il frame corrente
                adminMainFrame.setVisible(true); // Torna a AdminMainFrame
            }
        });
    }

    private void loadUsers() {
        UserController userController = new UserController();
        try {
            List<User> users = userController.getAllUsers();
            tableModel.setRowCount(0); // Clear existing data
            for (User user : users) {
                Object[] rowData = {
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPreferredGenres(),
                        user.getPriorityLevel(),
                        user.getUserType()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String firstName = (String) tableModel.getValueAt(selectedRow, 1);
        String lastName = (String) tableModel.getValueAt(selectedRow, 2);
        String email = (String) tableModel.getValueAt(selectedRow, 3);
        String preferredGenres = (String) tableModel.getValueAt(selectedRow, 4);
        int priorityLevel = (int) tableModel.getValueAt(selectedRow, 5);
        String userType = (String) tableModel.getValueAt(selectedRow, 6);

        JTextField firstNameField = new JTextField(firstName);
        JTextField lastNameField = new JTextField(lastName);
        JTextField emailField = new JTextField(email);
        JTextField preferredGenresField = new JTextField(preferredGenres);
        JTextField priorityLevelField = new JTextField(String.valueOf(priorityLevel));
        JComboBox<String> userTypeComboBox = new JComboBox<>(new String[]{"User", "Admin"});
        userTypeComboBox.setSelectedItem(userType);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Preferred Genres:"));
        panel.add(preferredGenresField);
        panel.add(new JLabel("Priority Level:"));
        panel.add(priorityLevelField);
        panel.add(new JLabel("User Type:"));
        panel.add(userTypeComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            User user = new User();
            user.setId(userId);
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setEmail(emailField.getText());
            user.setPreferredGenres(preferredGenresField.getText());
            user.setPriorityLevel(Integer.parseInt(priorityLevelField.getText()));
            user.setUserType((String) userTypeComboBox.getSelectedItem());

            UserController userController = new UserController();
            try {
                userController.updateUser(user);
                loadUsers();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            UserController userController = new UserController();
            try {
                userController.deleteUser(userId);
                loadUsers();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
