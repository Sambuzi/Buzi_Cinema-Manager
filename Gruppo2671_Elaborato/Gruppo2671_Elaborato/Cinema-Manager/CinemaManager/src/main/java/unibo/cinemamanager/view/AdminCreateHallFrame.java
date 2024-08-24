package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.HallController;
import unibo.cinemamanager.Model.Hall;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * This class provides a frame for the admin to create a new hall.
 */
public class AdminCreateHallFrame extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;
    private static final int INSETS_VALUE = 5;
    private static final int TEXT_FIELD_SIZE = 20;

    private JTextField hallNameField;
    private JTextField hallCapacityField;
    private JButton saveButton;
    private JButton backButton;

    /**
     * Constructs a frame for creating a new hall.
     */
    public AdminCreateHallFrame() {
        setTitle("Create Hall");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSETS_VALUE, INSETS_VALUE, INSETS_VALUE, INSETS_VALUE);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Hall Name:"), gbc);
        hallNameField = new JTextField(TEXT_FIELD_SIZE);
        gbc.gridx = 1;
        panel.add(hallNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Capacity:"), gbc);
        hallCapacityField = new JTextField(TEXT_FIELD_SIZE);
        gbc.gridx = 1;
        panel.add(hallCapacityField, gbc);

        saveButton = new JButton("Save Hall");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        backButton = new JButton("Back");
        gbc.gridy = 3;
        panel.add(backButton, gbc);

        add(panel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                saveHall();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
                // Replace with logic to show the admin main frame
                // new AdminMainFrame().setVisible(true);
            }
        });
    }

    /**
     * Saves the new hall to the database.
     */
    private void saveHall() {
        String hallName = hallNameField.getText();
        String capacityText = hallCapacityField.getText();

        if (hallName.isEmpty() || capacityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int capacity = Integer.parseInt(capacityText);
            Hall hall = new Hall();
            hall.setName(hallName);
            hall.setCapacity(capacity);

            HallController hallController = new HallController();
            hallController.createHall(hall);

            JOptionPane.showMessageDialog(this, "Hall created successfully!");
            dispose();

            // Logic to refresh or update view goes here

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for capacity.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving hall: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
