package unibo.cinemamanager.view;

import unibo.cinemamanager.controller.BookingController;
import unibo.cinemamanager.controller.ProjectionController;
import unibo.cinemamanager.Model.Booking;
import unibo.cinemamanager.Model.Projection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class BookingFrame extends JFrame {
    private JTextField userIdField;
    private JComboBox<String> projectionComboBox;
    private JComboBox<Integer> ticketsComboBox;
    private JTextField availableSeatsField;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private JButton bookButton;
    private JButton deleteButton;
    private JButton backButton;
    private UserMainFrame userMainFrame;

    public BookingFrame(UserMainFrame userMainFrame, int userId) {
        this.userMainFrame = userMainFrame;

        setTitle("Book Seats");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userIdLabel, gbc);

        userIdField = new JTextField(15);
        userIdField.setText(String.valueOf(userId));
        userIdField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(userIdField, gbc);

        JLabel projectionLabel = new JLabel("Projection:");
        projectionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(projectionLabel, gbc);

        projectionComboBox = new JComboBox<>();
        loadProjections();
        projectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAvailableSeats();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(projectionComboBox, gbc);

        JLabel availableSeatsLabel = new JLabel("Available Seats:");
        availableSeatsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(availableSeatsLabel, gbc);

        availableSeatsField = new JTextField(15);
        availableSeatsField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(availableSeatsField, gbc);

        JLabel ticketsLabel = new JLabel("Tickets:");
        ticketsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(ticketsLabel, gbc);

        ticketsComboBox = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            ticketsComboBox.addItem(i);
        }
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(ticketsComboBox, gbc);

        bookButton = new JButton("Book");
        bookButton.setFont(new Font("Arial", Font.BOLD, 14));
        bookButton.setBackground(new Color(70, 130, 180));
        bookButton.setForeground(Color.BLACK);

        deleteButton = new JButton("Delete Booking");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(255, 69, 0));
        deleteButton.setForeground(Color.BLACK);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.BLACK);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.add(bookButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonsPanel, gbc);

        // Pannello per la tabella delle prenotazioni
        JPanel bookingsPanel = new JPanel(new BorderLayout(10, 10));
        bookingsPanel.setBorder(BorderFactory.createTitledBorder("Your Bookings"));
        bookingsPanel.setBackground(Color.WHITE);

        String[] columnNames = {"Booking ID", "Projection", "Tickets"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingsTable = new JTable(tableModel);
        bookingsTable.setFillsViewportHeight(true);
        bookingsTable.setRowHeight(30);
        bookingsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingsTable.setSelectionBackground(new Color(184, 207, 229));
        bookingsTable.setSelectionForeground(Color.BLACK);
        bookingsTable.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        bookingsPanel.add(scrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, bookingsPanel);
        splitPane.setResizeWeight(0.3); // Proporzione di spazio per i pannelli
        splitPane.setDividerLocation(300); // Posizione del divisore

        add(splitPane);

        loadUserBookings(userId);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seatsToBook = (Integer) ticketsComboBox.getSelectedItem();
                int availableSeats = Integer.parseInt(availableSeatsField.getText());
                if (seatsToBook > availableSeats) {
                    JOptionPane.showMessageDialog(BookingFrame.this, "Not enough available seats.");
                    return;
                }

                Booking booking = new Booking();
                booking.setUserId(Integer.parseInt(userIdField.getText()));
                String selectedProjection = (String) projectionComboBox.getSelectedItem();
                int projectionId = Integer.parseInt(selectedProjection.split(":")[0].trim());
                booking.setProjectionId(projectionId);
                booking.setSeats(seatsToBook);

                BookingController bookingController = new BookingController();
                try {
                    bookingController.createBooking(booking);
                    ProjectionController projectionController = new ProjectionController();
                    projectionController.updateAvailableSeats(projectionId, seatsToBook);
                    JOptionPane.showMessageDialog(BookingFrame.this, "Booking created successfully!");
                    updateAvailableSeats();  // Aggiorna i posti disponibili
                    loadUserBookings(userId);  // Ricarica le prenotazioni dell'utente
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(BookingFrame.this, "Error creating booking.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookingsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(BookingFrame.this, "Please select a booking to delete.");
                    return;
                }

                int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
                BookingController bookingController = new BookingController();
                try {
                    bookingController.deleteBooking(bookingId);
                    JOptionPane.showMessageDialog(BookingFrame.this, "Booking deleted successfully!");
                    loadUserBookings(userId);  // Ricarica le prenotazioni dell'utente
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(BookingFrame.this, "Error deleting booking.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                userMainFrame.setVisible(true);
            }
        });
    }

    private void loadProjections() {
        ProjectionController projectionController = new ProjectionController();
        try {
            List<Projection> projections = projectionController.getAllProjections();
            for (Projection projection : projections) {
                String projectionInfo = projection.getId() + ": " + projection.getMovieTitle() + " at " + projection.getProjectionTime();
                projectionComboBox.addItem(projectionInfo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading projections: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAvailableSeats() {
        String selectedProjection = (String) projectionComboBox.getSelectedItem();
        if (selectedProjection != null) {
            int projectionId = Integer.parseInt(selectedProjection.split(":")[0].trim());
            ProjectionController projectionController = new ProjectionController();
            try {
                Projection projection = projectionController.getProjectionById(projectionId);
                if (projection != null) {
                    int availableSeats = projection.getAvailableSeats();
                    availableSeatsField.setText(String.valueOf(availableSeats));

                    ticketsComboBox.removeAllItems();
                    for (int i = 1; i <= Math.min(10, availableSeats); i++) {
                        ticketsComboBox.addItem(i);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error fetching available seats: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadUserBookings(int userId) {
        BookingController bookingController = new BookingController();
        try {
            List<Booking> bookings = bookingController.getBookingsByUserId(userId);
            tableModel.setRowCount(0);
            for (Booking booking : bookings) {
                String projectionInfo = booking.getProjectionId() + ": " + booking.getProjection().getMovieTitle() + " at " + booking.getProjection().getProjectionTime();
                Object[] rowData = {
                        booking.getId(),
                        projectionInfo,
                        booking.getSeats()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading bookings: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
