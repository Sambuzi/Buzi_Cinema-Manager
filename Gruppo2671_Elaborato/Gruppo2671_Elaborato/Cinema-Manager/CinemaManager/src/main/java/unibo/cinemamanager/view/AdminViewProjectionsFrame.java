package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.ProjectionController;
import unibo.cinemamanager.Model.Projection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AdminViewProjectionsFrame extends JFrame {
    private JTable projectionsTable;
    private DefaultTableModel tableModel;
    private JButton editButton;
    private JButton backButton; // Dichiarazione del pulsante Back
    private AdminMainFrame adminMainFrame; // Riferimento a AdminMainFrame
    private ProjectionController projectionController;

    public AdminViewProjectionsFrame(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame; // Inizializzazione di AdminMainFrame

        setTitle("View Projections");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        projectionController = new ProjectionController();

        // Colonne della tabella
        String[] columnNames = {"ID", "Movie Title", "Projection Date", "Projection Time", "Hall"};
        tableModel = new DefaultTableModel(columnNames, 0);
        projectionsTable = new JTable(tableModel);

        // Recupera i dati delle proiezioni dal database e popolano la tabella
        loadProjections();

        JScrollPane scrollPane = new JScrollPane(projectionsTable);
        add(scrollPane, BorderLayout.CENTER);

        editButton = new JButton("Edit Selected Projection");
        backButton = new JButton("Back"); // Inizializzazione del pulsante Back

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = projectionsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(AdminViewProjectionsFrame.this, "Please select a projection to edit.");
                    return;
                }

                int projectionId = (Integer) tableModel.getValueAt(selectedRow, 0);
                Projection projection;
                try {
                    projection = projectionController.getProjectionById(projectionId);
                    if (projection != null) {
                        openEditDialog(projection);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(AdminViewProjectionsFrame.this, "Error loading projection: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiudi il frame corrente
                adminMainFrame.setVisible(true); // Torna a AdminMainFrame
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(backButton); // Aggiunta del pulsante Back al pannello
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadProjections() {
        try {
            List<Projection> projections = projectionController.getAllProjections();
            for (Projection projection : projections) {
                // Estrai solo la parte della data dalla stringa di data e ora
                String projectionDate = projection.getProjectionDate().split(" ")[0];
                Object[] rowData = {
                        projection.getId(),
                        projection.getMovieTitle(),
                        projectionDate,
                        projection.getProjectionTime(),
                        projection.getHall()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading projections: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openEditDialog(Projection projection) {
        JDialog editDialog = new JDialog(this, "Edit Projection", true);
        editDialog.setSize(400, 300);
        editDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Movie Title:"), gbc);
        JTextField movieTitleField = new JTextField(projection.getMovieTitle(), 20);
        gbc.gridx = 1;
        panel.add(movieTitleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Projection Date:"), gbc);
        JTextField projectionDateField = new JTextField(projection.getProjectionDate().split(" ")[0], 20);
        gbc.gridx = 1;
        panel.add(projectionDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Projection Time:"), gbc);
        JTextField projectionTimeField = new JTextField(projection.getProjectionTime(), 20);
        gbc.gridx = 1;
        panel.add(projectionTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Hall:"), gbc);
        JTextField hallField = new JTextField(projection.getHall(), 20);
        gbc.gridx = 1;
        panel.add(hallField, gbc);

        JButton saveButton = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        editDialog.add(panel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projection.setMovieTitle(movieTitleField.getText());
                projection.setProjectionDate(projectionDateField.getText());
                projection.setProjectionTime(projectionTimeField.getText());
                projection.setHall(hallField.getText());

                try {
                    projectionController.updateProjection(projection);
                    JOptionPane.showMessageDialog(editDialog, "Projection updated successfully!");
                    editDialog.dispose();
                    refreshProjectionsTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(editDialog, "Error updating projection: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editDialog.setVisible(true);
    }

    private void refreshProjectionsTable() {
        tableModel.setRowCount(0);
        loadProjections();
    }
}
