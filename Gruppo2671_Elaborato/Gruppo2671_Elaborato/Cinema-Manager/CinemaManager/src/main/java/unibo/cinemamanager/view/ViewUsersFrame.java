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

public class ViewUsersFrame extends JFrame {
    private JTable usersTable;
    private JButton backButton;
    private RegisterFrame registerFrame; // Declare a reference to the RegisterFrame

    public ViewUsersFrame(RegisterFrame registerFrame) {
        this.registerFrame = registerFrame; // Initialize the reference

        setTitle("Registered Users");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        usersTable = new JTable();
        add(new JScrollPane(usersTable), BorderLayout.CENTER);

        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerFrame.setVisible(true); // Show the RegisterFrame
                dispose(); // Close the current frame
            }
        });

        loadUsers();
    }

    private void loadUsers() {
        UserController userController = new UserController();
        try {
            List<User> users = userController.getAllUsers();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("First Name");
            model.addColumn("Last Name");
            model.addColumn("Email");
            model.addColumn("Genres");
            model.addColumn("Priority Level");

            for (User user : users) {
                model.addRow(new Object[]{
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPreferredGenres(),
                        user.getPriorityLevel()
                });
            }

            usersTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading users.");
        }
    }
}
