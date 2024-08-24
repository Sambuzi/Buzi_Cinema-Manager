package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.UserController;
import unibo.cinemamanager.Model.User;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * The ViewUsersFrame class displays a table of registered users and allows navigation back to the RegisterFrame.
 */
public class ViewUsersFrame extends JFrame {

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400; 
    private JTable usersTable;
    private JButton backButton;
    private final RegisterFrame registerFrame;

    /**
     * Constructor for the ViewUsersFrame.
     *
     * @param registerFrame the frame to return to when the back button is pressed
     */
    public ViewUsersFrame(final RegisterFrame registerFrame) {
        this.registerFrame = registerFrame;

        setTitle("Registered Users");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
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
            public void actionPerformed(final ActionEvent e) {
                registerFrame.setVisible(true);
                dispose();
            }
        });

        loadUsers();

        setLocationRelativeTo(null);
    }

    /**
     * Loads the list of users from the database and displays them in the table.
     */
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
