package ui;

import db.DatabaseConnection;
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
        setTitle("📄 Daftar Tiket yang Dipesan");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 248, 255));

        // === Tabel ===
        tableModel = new DefaultTableModel(new String[]{
                " Penumpang", " Asal", " Tujuan", " Berangkat", " Harga", " Pesan"
        }, 0);
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // === Tombol kembali ===
        btnBack = new JButton(" Kembali ke Menu");
        btnBack.setBackground(new Color(156, 163, 175));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBack.setPreferredSize(new Dimension(160, 35));

        btnBack.addActionListener(e -> {
            dispose();
            new MainMenu().setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(245, 248, 255));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // === Load data tiket ===
        loadTickets();
    }

    private void loadTickets() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT t.passenger_name, r.origin, r.destination, r.departure_time, r.price, t.order_time " +
                         "FROM tickets t JOIN routes r ON t.route_id = r.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("passenger_name"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getTimestamp("departure_time").toLocalDateTime().format(formatter),
                        String.format("Rp %,d", rs.getInt("price")),
                        rs.getTimestamp("order_time").toLocalDateTime().format(formatter)
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data tiket.\n" + e.getMessage());
        }
    }
}
