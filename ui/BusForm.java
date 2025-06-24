package ui;

import controller.BusController;
import model.Bus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BusForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbName, cbType;
    private JSpinner spSeats;
    private JButton btnAdd, btnUpdate, btnDelete, btnBack;

    private int selectedBusId = -1;

    public BusForm() {
        setTitle("🚌 Manajemen Data Bus");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Tabel ===
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Tipe", "Kursi"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        // === Komponen Form ===
        cbName = new JComboBox<>(new String[]{"Sinar Jaya", "Rosalia Indah", "Harapan Jaya", "ALS", "PO Haryanto"});
        cbType = new JComboBox<>(new String[]{"Ekonomi", "Bisnis", "Eksekutif"});
        spSeats = new JSpinner(new SpinnerNumberModel(30, 10, 100, 1));

        cbName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spSeats.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        formPanel.add(new JLabel("Nama Bus:"));
        formPanel.add(cbName);
        formPanel.add(new JLabel("Tipe Bus:"));
        formPanel.add(cbType);
        formPanel.add(new JLabel("Jumlah Kursi:"));
        formPanel.add(spSeats);

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

        loadTable();

        // === Event Klik Tabel ===
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                selectedBusId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                cbName.setSelectedItem(tableModel.getValueAt(row, 1).toString());
                cbType.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                spSeats.setValue(Integer.parseInt(tableModel.getValueAt(row, 3).toString()));
            }
        });

        // === Event Tombol ===
        btnAdd.addActionListener(e -> {
            Bus bus = new Bus(
                    cbName.getSelectedItem().toString(),
                    cbType.getSelectedItem().toString(),
                    (Integer) spSeats.getValue()
            );
            if (BusController.addBus(bus)) {
                JOptionPane.showMessageDialog(this, "✅ Bus berhasil ditambahkan!");
                loadTable();
                clearForm();
            }
        });

        btnUpdate.addActionListener(e -> {
            if (selectedBusId != -1) {
                Bus bus = new Bus(
                        selectedBusId,
                        cbName.getSelectedItem().toString(),
                        cbType.getSelectedItem().toString(),
                        (Integer) spSeats.getValue()
                );
                if (BusController.updateBus(bus)) {
                    JOptionPane.showMessageDialog(this, "✅ Bus berhasil diperbarui!");
                    loadTable();
                    clearForm();
                }
            }
        });

        btnDelete.addActionListener(e -> {
            if (selectedBusId != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?");
                if (confirm == 0) {
                    if (BusController.deleteBus(selectedBusId)) {
                        JOptionPane.showMessageDialog(this, "🗑️ Bus berhasil dihapus!");
                        loadTable();
                        clearForm();
                    }
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
        btn.setPreferredSize(new Dimension(100, 35));
        return btn;
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        ArrayList<Bus> buses = BusController.getAllBuses();
        for (Bus b : buses) {
            tableModel.addRow(new Object[]{b.getId(), b.getName(), b.getType(), b.getTotalSeats()});
        }
    }

    private void clearForm() {
        cbName.setSelectedIndex(0);
        cbType.setSelectedIndex(0);
        spSeats.setValue(30);
        selectedBusId = -1;
    }
}
