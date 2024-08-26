package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.UserController;
import unibo.cinemamanager.Model.User;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * This class provides an interface for administrators to manage users.
 */
public class AdminManageUsersFrame extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int PRIORITY_LEVEL_INDEX = 5;
    private static final int USER_TYPE_INDEX = 6;
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton backButton;
    private JButton blockButton;
    private JButton unblockButton;
    private AdminMainFrame adminMainFrame;

    /**
     * Constructs the frame for managing users.
     *
     * @param adminMainFrame The reference to the main admin frame.
     */
    public AdminManageUsersFrame(final AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;

        setTitle("Manage Users");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Preferred Genres",
                "Priority Level", "User Type", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        usersTable = new JTable(tableModel);
        loadUsers();

        JScrollPane scrollPane = new JScrollPane(usersTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for left-aligned buttons
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        backButton = new JButton("Back");

        leftButtonPanel.add(editButton);
        leftButtonPanel.add(deleteButton);
        leftButtonPanel.add(refreshButton);
        leftButtonPanel.add(backButton);

        // Panel for right-aligned buttons
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        blockButton = new JButton("Block");
        unblockButton = new JButton("Unblock");

        rightButtonPanel.add(blockButton);
        rightButtonPanel.add(unblockButton);

        // Panel to contain both left and right panels
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                editUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                deleteUser();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                loadUsers();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
                adminMainFrame.setVisible(true);
            }
        });

        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                blockUser();
            }
        });

        unblockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                unblockUser();
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
                        user.getUserType(),
                        user.isBlocked() ? "Blocked" : "Active"
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String firstName = (String) tableModel.getValueAt(selectedRow, 1);
        String lastName = (String) tableModel.getValueAt(selectedRow, 2);
        String email = (String) tableModel.getValueAt(selectedRow, 3);
        String preferredGenres = (String) tableModel.getValueAt(selectedRow, 4);
        int priorityLevel = (int) tableModel.getValueAt(selectedRow, PRIORITY_LEVEL_INDEX);
        String userType = (String) tableModel.getValueAt(selectedRow, USER_TYPE_INDEX);

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

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit User",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?",
                "Delete User", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            UserController userController = new UserController();
            try {
                userController.deleteUser(userId);
                loadUsers();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void blockUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to block.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userType = (String) tableModel.getValueAt(selectedRow, USER_TYPE_INDEX);
        if ("Admin".equals(userType)) {
            JOptionPane.showMessageDialog(this, "Cannot block an admin user.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to block this user?",
                "Block User", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            UserController userController = new UserController();
            try {
                userController.blockUser(userId);
                loadUsers();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error blocking user: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void unblockUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to unblock.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String status = (String) tableModel.getValueAt(selectedRow, 7);
        if (!"Blocked".equals(status)) {
            JOptionPane.showMessageDialog(this, "Selected user is not blocked.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to unblock this user?",
                "Unblock User", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            UserController userController = new UserController();
            try {
                userController.unblockUser(userId);
                loadUsers();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error unblocking user: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
