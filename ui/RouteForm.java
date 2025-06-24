package ui;

import controller.RouteController;
import controller.BusController;
import model.Bus;
import model.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RouteForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbOrigin, cbDestination, cbBus;
    private JTextField tfDeparture;
    private JSpinner spPrice;
    private JButton btnAdd, btnUpdate, btnDelete, btnBack;

    private int selectedRouteId = -1;
    private ArrayList<Bus> busList = new ArrayList<>();

    public RouteForm() {
        setTitle("🗺️ Manajemen Rute Bus");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Tabel ===
        tableModel = new DefaultTableModel(new String[]{"ID", "Asal", "Tujuan", "Waktu", "Harga", "Bus"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        // === Form Input ===
        cbOrigin = new JComboBox<>(new String[]{"Jakarta", "Bandung", "Yogyakarta", "Surabaya", "Semarang"});
        cbDestination = new JComboBox<>(new String[]{"Jakarta", "Bandung", "Yogyakarta", "Surabaya", "Semarang"});
        tfDeparture = new JTextField(); // format: yyyy-MM-dd HH:mm
        spPrice = new JSpinner(new SpinnerNumberModel(100000, 50000, 1000000, 10000));
        cbBus = new JComboBox<>();

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        cbOrigin.setFont(inputFont);
        cbDestination.setFont(inputFont);
        tfDeparture.setFont(inputFont);
        spPrice.setFont(inputFont);
        cbBus.setFont(inputFont);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formPanel.setBackground(new Color(250, 250, 255));

        formPanel.add(new JLabel("Asal:"));
        formPanel.add(cbOrigin);
        formPanel.add(new JLabel("Tujuan:"));
        formPanel.add(cbDestination);
        formPanel.add(new JLabel("Waktu (yyyy-MM-dd HH:mm):"));
        formPanel.add(tfDeparture);
        formPanel.add(new JLabel("Harga:"));
        formPanel.add(spPrice);
        formPanel.add(new JLabel("Bus:"));
        formPanel.add(cbBus);

        // === Tombol ===
        btnAdd = createButton("Tambah", new Color(59, 130, 246));
        btnUpdate = createButton("Ubah", new Color(34, 197, 94));
        btnDelete = createButton("Hapus", new Color(239, 68, 68));
        btnBack = createButton("Kembali", new Color(156, 163, 175));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadBuses();
        loadTable();

        // === Event Klik Tabel ===
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                selectedRouteId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                cbOrigin.setSelectedItem(tableModel.getValueAt(row, 1).toString());
                cbDestination.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                tfDeparture.setText(tableModel.getValueAt(row, 3).toString());
                spPrice.setValue(Double.parseDouble(tableModel.getValueAt(row, 4).toString()));
                cbBus.setSelectedItem(tableModel.getValueAt(row, 5).toString());
            }
        });

        // === Event Tombol ===
        btnAdd.addActionListener(e -> {
            try {
                Route route = getRouteFromForm(false);
                if (RouteController.addRoute(route)) {
                    JOptionPane.showMessageDialog(this, "✅ Rute ditambahkan!");
                    loadTable();
                    clearForm();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Input tidak valid: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                if (selectedRouteId != -1) {
                    Route route = getRouteFromForm(true);
                    route.setId(selectedRouteId);
                    if (RouteController.updateRoute(route)) {
                        JOptionPane.showMessageDialog(this, "✅ Rute diperbarui!");
                        loadTable();
                        clearForm();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Input tidak valid: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            if (selectedRouteId != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus?");
                if (confirm == 0 && RouteController.deleteRoute(selectedRouteId)) {
                    JOptionPane.showMessageDialog(this, "🗑️ Rute dihapus!");
                    loadTable();
                    clearForm();
                }
            }
        });

        btnBack.addActionListener(e -> {
            dispose();
            new MainMenu().setVisible(true);
        });
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(110, 35));
        return btn;
    }

    private Route getRouteFromForm(boolean includeId) {
        String origin = cbOrigin.getSelectedItem().toString();
        String dest = cbDestination.getSelectedItem().toString();
        LocalDateTime time = LocalDateTime.parse(tfDeparture.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        double price = ((Integer) spPrice.getValue()).doubleValue();
        int busIndex = cbBus.getSelectedIndex();
        int busId = busList.get(busIndex).getId();

        if (includeId)
            return new Route(selectedRouteId, origin, dest, time, price, busId);
        else
            return new Route(origin, dest, time, price, busId);
    }

    private void loadBuses() {
        busList = BusController.getAllBuses();
        cbBus.removeAllItems();
        for (Bus b : busList) {
            cbBus.addItem(b.getName());
        }
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for (Route r : RouteController.getAllRoutes()) {
            String busName = busList.stream()
                .filter(b -> b.getId() == r.getBusId())
                .map(Bus::getName)
                .findFirst()
                .orElse("Tidak Diketahui");
            tableModel.addRow(new Object[]{
                r.getId(),
                r.getOrigin(),
                r.getDestination(),
                r.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                r.getPrice(),
                busName
            });
        }
    }

    private void clearForm() {
        cbOrigin.setSelectedIndex(0);
        cbDestination.setSelectedIndex(0);
        tfDeparture.setText("");
        spPrice.setValue(100000);
        cbBus.setSelectedIndex(0);
        selectedRouteId = -1;
    }
}
