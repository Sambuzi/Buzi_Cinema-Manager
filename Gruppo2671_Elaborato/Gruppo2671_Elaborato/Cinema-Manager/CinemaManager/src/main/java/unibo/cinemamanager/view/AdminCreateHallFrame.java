package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.HallController;
import unibo.cinemamanager.Model.Hall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminCreateHallFrame extends JFrame {
    private JTextField hallNameField;
    private JTextField hallCapacityField;
    private JButton saveButton;
    private JButton backButton; // Pulsante per tornare indietro

    public AdminCreateHallFrame() {
        setTitle("Create Hall");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra il frame sullo schermo

        // Pannello principale
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etichetta e campo per il nome della sala
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Hall Name:"), gbc);
        hallNameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(hallNameField, gbc);

        // Etichetta e campo per la capacità della sala
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Capacity:"), gbc);
        hallCapacityField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(hallCapacityField, gbc);

        // Pulsante per salvare la sala
        saveButton = new JButton("Save Hall");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        // Pulsante per tornare indietro
        backButton = new JButton("Back");
        gbc.gridy = 3;
        panel.add(backButton, gbc);

        // Aggiunta del pannello al frame
        add(panel);

        // Azione per il pulsante "Save Hall"
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveHall();
            }
        });

        // Azione per il pulsante "Back"
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiude il frame corrente
                // Apri il frame principale dell'admin (adminMainFrame)
                // Puoi sostituire questa logica con il tuo codice specifico per mostrare il frame principale dell'admin
                // new AdminMainFrame().setVisible(true);
            }
        });
    }

    // Metodo per salvare la nuova sala
    private void saveHall() {
        String hallName = hallNameField.getText();
        String capacityText = hallCapacityField.getText();

        // Validazione degli input
        if (hallName.isEmpty() || capacityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int capacity = Integer.parseInt(capacityText);

            // Creazione di una nuova sala
            Hall hall = new Hall();
            hall.setName(hallName);
            hall.setCapacity(capacity);

            HallController hallController = new HallController();
            hallController.createHall(hall);

            JOptionPane.showMessageDialog(this, "Hall created successfully!");
            dispose(); // Chiude il frame corrente

            // Puoi mostrare un altro frame o aggiornare la vista corrente se necessario
            // new AdminMainFrame().setVisible(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for capacity.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving hall: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
