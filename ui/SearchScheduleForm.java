package ui;

import controller.RouteController;
import model.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class SearchScheduleForm extends JFrame {
    private JComboBox<String> cbOrigin, cbDestination;
    private JButton btnSearch, btnBack;
    private JTable table;
    private DefaultTableModel tableModel;

    public SearchScheduleForm() {
        setTitle("Cari Jadwal Bus");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Atas: Filter
        JPanel topPanel = new JPanel();
        cbOrigin = new JComboBox<>();
        cbDestination = new JComboBox<>();
        btnSearch = new JButton("🔍 Cari");

        topPanel.add(new JLabel("Asal:"));
        topPanel.add(cbOrigin);
        topPanel.add(new JLabel("Tujuan:"));
        topPanel.add(cbDestination);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        // Tengah: Tabel hasil
        tableModel = new DefaultTableModel(new String[]{"Asal", "Tujuan", "Waktu", "Harga"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bawah: Tombol kembali
        JPanel bottomPanel = new JPanel();
        btnBack = new JButton("⬅ Kembali ke Menu Utama");
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // Isi combo
        loadComboData();

        // Event tombol
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

        var origins = routes.stream().map(Route::getOrigin).distinct().toArray(String[]::new);
        var destinations = routes.stream().map(Route::getDestination).distinct().toArray(String[]::new);

        cbOrigin.setModel(new DefaultComboBoxModel<>(origins));
        cbDestination.setModel(new DefaultComboBoxModel<>(destinations));
    }

    private void searchSchedule(String asal, String tujuan) {
        List<Route> hasil = RouteController.getAllRoutes().stream()
                .filter(r -> r.getOrigin().equals(asal) && r.getDestination().equals(tujuan))
                .collect(Collectors.toList());

        tableModel.setRowCount(0);
        for (Route r : hasil) {
            tableModel.addRow(new Object[]{
                    r.getOrigin(),
                    r.getDestination(),
                    r.getDepartureTime().toString(),
                    r.getPrice()
            });
        }
    }
}
