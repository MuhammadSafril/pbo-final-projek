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
        setTitle("Pesan Tiket Bus");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        tfPassenger = new JTextField();
        cbRoutes = new JComboBox<>();

        formPanel.add(new JLabel("Nama Penumpang:"));
        formPanel.add(tfPassenger);
        formPanel.add(new JLabel("Pilih Rute:"));
        formPanel.add(cbRoutes);

        // Tombol
        btnOrder = new JButton("Pesan Tiket");
        btnBack = new JButton("Kembali");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnOrder);
        buttonPanel.add(btnBack);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadRoutes();

        // Event tombol
        btnOrder.addActionListener(e -> {
            String name = tfPassenger.getText().trim();
            int selected = cbRoutes.getSelectedIndex();

            if (name.isEmpty() || selected < 0) {
                JOptionPane.showMessageDialog(this, "Isi nama dan pilih rute.");
                return;
            }

            int routeId = routeList.get(selected).getId();
            Ticket ticket = new Ticket(name, routeId, LocalDateTime.now());

            if (TicketController.addTicket(ticket)) {
                JOptionPane.showMessageDialog(this, "Tiket berhasil dipesan!");
                tfPassenger.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memesan tiket.");
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
}
