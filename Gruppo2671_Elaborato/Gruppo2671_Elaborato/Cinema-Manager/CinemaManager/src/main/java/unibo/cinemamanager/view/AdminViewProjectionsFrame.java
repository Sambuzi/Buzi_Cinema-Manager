package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.ProjectionController;
import unibo.cinemamanager.Model.Projection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * This class represents the frame where admins can view and manage projections.
 */
public class AdminViewProjectionsFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int EDIT_DIALOG_WIDTH = 400;
    private static final int EDIT_DIALOG_HEIGHT = 300;
    private static final int INSET_SIZE = 10;
    private static final int TEXT_FIELD_SIZE = 20;
    private static final int BUTTON_INSET = 5;

    private JTable projectionsTable;
    private DefaultTableModel tableModel;
    private JButton editButton;
    private JButton backButton;
    private final AdminMainFrame adminMainFrame;
    private final ProjectionController projectionController;

    /**
     * Constructs the AdminViewProjectionsFrame.
     *
     * @param adminMainFrame the main frame of the admin, must be final.
     */
    public AdminViewProjectionsFrame(final AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;

        setTitle("View Projections");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
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
        backButton = new JButton("Back");

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                editSelectedProjection();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
                adminMainFrame.setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadProjections() {
        try {
            List<Projection> projections = projectionController.getAllProjections();
            for (Projection projection : projections) {
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
            JOptionPane.showMessageDialog(
                this, "Error loading projections: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void editSelectedProjection() {
        int selectedRow = projectionsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a projection to edit.");
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
            JOptionPane.showMessageDialog(
                this, "Error loading projection: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openEditDialog(final Projection projection) {
        final JDialog editDialog = new JDialog(this, "Edit Projection", true);
        editDialog.setSize(EDIT_DIALOG_WIDTH, EDIT_DIALOG_HEIGHT);
        editDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET_SIZE, INSET_SIZE, INSET_SIZE, INSET_SIZE);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Movie Title:"), gbc);
        final JTextField movieTitleField = new JTextField(projection.getMovieTitle(), TEXT_FIELD_SIZE);
        gbc.gridx = 1;
        panel.add(movieTitleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Projection Date:"), gbc);
        final JTextField projectionDateField = new JTextField(projection.getProjectionDate().split(" ")[0], TEXT_FIELD_SIZE);
        gbc.gridx = 1;
        panel.add(projectionDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Projection Time:"), gbc);
        final JTextField projectionTimeField = new JTextField(projection.getProjectionTime(), TEXT_FIELD_SIZE);
        gbc.gridx = 1;
        panel.add(projectionTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Hall:"), gbc);
        final JTextField hallField = new JTextField(projection.getHall(), TEXT_FIELD_SIZE);
        gbc.gridx = 1;
        panel.add(hallField, gbc);

        JButton saveButton = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(BUTTON_INSET, BUTTON_INSET, BUTTON_INSET, BUTTON_INSET);
        panel.add(saveButton, gbc);

        editDialog.add(panel);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                updateProjection(projection, movieTitleField, projectionDateField, projectionTimeField, hallField, editDialog);
            }
        });

        editDialog.setVisible(true);
    }

    private void updateProjection(final Projection projection, final JTextField movieTitleField,
                                  final JTextField projectionDateField, final JTextField projectionTimeField,
                                  final JTextField hallField, final JDialog editDialog) {
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
            JOptionPane.showMessageDialog(
                editDialog, "Error updating projection: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void refreshProjectionsTable() {
        tableModel.setRowCount(0);
        loadProjections();
    }
}
