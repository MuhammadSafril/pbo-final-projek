package ui;

import db.DatabaseConnection; // atau db.DatabaseConnection
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class TicketListForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnBack;

    public TicketListForm() {
        setTitle("Daftar Tiket yang Dipesan");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Tabel ===
        tableModel = new DefaultTableModel(new String[]{
                "Nama Penumpang", "Asal", "Tujuan", "Waktu Berangkat", "Harga", "Waktu Pesan"
        }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // === Tombol kembali ===
        btnBack = new JButton("Kembali ke Menu");
        btnBack.addActionListener(e -> {
            dispose();
            new MainMenu().setVisible(true);
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // === Load data ===
        loadTickets();
    }

    private void loadTickets() {
        tableModel.setRowCount(0);
        try {
            Connection conn = db.DatabaseConnection.getConnection();
            String sql = "SELECT t.passenger_name, r.origin, r.destination, r.departure_time, r.price, t.order_time " +
                         "FROM tickets t " +
                         "JOIN routes r ON t.route_id = r.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("passenger_name"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getTimestamp("departure_time").toLocalDateTime().format(formatter),
                        rs.getDouble("price"),
                        rs.getTimestamp("order_time").toLocalDateTime().format(formatter)
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data tiket.\n" + e.getMessage());
        }
    }
}
