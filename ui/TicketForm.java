package ui;

import controller.RouteController;
import controller.TicketController;
import model.Route;
import model.Ticket;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TicketForm extends JFrame {
    private JTextField tfPassenger;
    private JComboBox<String> cbRoutes;
    private JButton btnOrder, btnBack;
    private ArrayList<Route> routeList;

    public TicketForm() {
        setTitle("🎫 Pemesanan Tiket Bus");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 248, 255));

        // === Panel Form ===
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        formPanel.setBackground(new Color(245, 248, 255));

        tfPassenger = new JTextField();
        cbRoutes = new JComboBox<>();

        tfPassenger.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbRoutes.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(new JLabel("👤 Nama Penumpang:"));
        formPanel.add(tfPassenger);
        formPanel.add(new JLabel("🚌 Pilih Rute:"));
        formPanel.add(cbRoutes);

        // === Tombol ===
        btnOrder = createButton("Pesan Tiket", new Color(34, 197, 94));
        btnBack = createButton("Kembali", new Color(156, 163, 175));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 248, 255));
        buttonPanel.add(btnOrder);
        buttonPanel.add(btnBack);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadRoutes();

        // === Event tombol ===
        btnOrder.addActionListener(e -> {
            String name = tfPassenger.getText().trim();
            int selected = cbRoutes.getSelectedIndex();

            if (name.isEmpty() || selected < 0) {
                JOptionPane.showMessageDialog(this, "⚠️ Isi nama dan pilih rute terlebih dahulu.");
                return;
            }

            int routeId = routeList.get(selected).getId();
            Ticket ticket = new Ticket(name, routeId, LocalDateTime.now());

            if (TicketController.addTicket(ticket)) {
                JOptionPane.showMessageDialog(this, "✅ Tiket berhasil dipesan!");
                tfPassenger.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Gagal memesan tiket.");
            }
        });

        btnBack.addActionListener(e -> {
            dispose();
            new MainMenu().setVisible(true);
        });
    }

    private void loadRoutes() {
        routeList = RouteController.getAllRoutes();
        cbRoutes.removeAllItems();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Route r : routeList) {
            cbRoutes.addItem(
                r.getOrigin() + " -> " + r.getDestination() + " | " +
                r.getDepartureTime().format(formatter)
            );
        }
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 35));
        return btn;
    }
}
