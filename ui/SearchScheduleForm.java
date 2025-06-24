package ui;

import controller.RouteController;
import model.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SearchScheduleForm extends JFrame {
    private JComboBox<String> cbOrigin, cbDestination;
    private JButton btnSearch, btnBack;
    private JTable table;
    private DefaultTableModel tableModel;

    public SearchScheduleForm() {
        setTitle("🔍 Cari Jadwal Bus");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(250, 252, 255));

        // === Panel Atas (Filter) ===
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(250, 252, 255));
        cbOrigin = new JComboBox<>();
        cbDestination = new JComboBox<>();
        btnSearch = new JButton("🔍 Cari");

        btnSearch.setBackground(new Color(34, 139, 230));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));

        topPanel.add(new JLabel("Asal:"));
        topPanel.add(cbOrigin);
        topPanel.add(new JLabel("Tujuan:"));
        topPanel.add(cbDestination);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        // === Tabel ===
        tableModel = new DefaultTableModel(new String[]{" Asal", " Tujuan", " Waktu", " Harga"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // === Tombol kembali ===
        btnBack = new JButton(" Kembali ke Menu Utama");
        btnBack.setBackground(new Color(108, 117, 125));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(250, 252, 255));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // === Data awal ===
        loadComboData();

        // === Event Button ===
        btnSearch.addActionListener(e -> {
            String asal = cbOrigin.getSelectedItem().toString();
            String tujuan = cbDestination.getSelectedItem().toString();
            searchSchedule(asal, tujuan);
        });

        btnBack.addActionListener(e -> {
            dispose();
            new MainMenu().setVisible(true);
        });
    }

    private void loadComboData() {
        List<Route> routes = RouteController.getAllRoutes();

        String[] origins = routes.stream().map(Route::getOrigin).distinct().toArray(String[]::new);
        String[] destinations = routes.stream().map(Route::getDestination).distinct().toArray(String[]::new);

        cbOrigin.setModel(new DefaultComboBoxModel<>(origins));
        cbDestination.setModel(new DefaultComboBoxModel<>(destinations));
    }

    private void searchSchedule(String asal, String tujuan) {
        List<Route> hasil = RouteController.getAllRoutes().stream()
                .filter(r -> r.getOrigin().equals(asal) && r.getDestination().equals(tujuan))
                .collect(Collectors.toList());

        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Route r : hasil) {
            tableModel.addRow(new Object[]{
                    r.getOrigin(),
                    r.getDestination(),
                    r.getDepartureTime().format(formatter),
                    String.format("Rp %,d", (int) r.getPrice())
            });
        }
    }
}
