package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("TiketinBus - Menu Utama");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // === Panel utama dengan GridLayout ===
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === Tombol menu utama ===
        JButton btnBus = new JButton("🚌 Manajemen Bus");
        JButton btnRoute = new JButton("🗺️ Manajemen Rute");
        JButton btnTicket = new JButton("🎫 Pesan Tiket");
        JButton btnTicketList = new JButton("📄 Lihat Tiket");
        JButton btnSearchSchedule = new JButton("🔍 Cari Jadwal Bus");
        JButton btnExit = new JButton("❌ Keluar");

        // === Event tombol ===
        btnBus.addActionListener(e -> {
            dispose();
            new BusForm().setVisible(true);
        });

        btnRoute.addActionListener(e -> {
            dispose();
            new RouteForm().setVisible(true);
        });

        btnTicket.addActionListener(e -> {
            dispose();
            new TicketForm().setVisible(true);
        });

        btnTicketList.addActionListener(e -> {
            dispose();
            new TicketListForm().setVisible(true);
        });

        btnSearchSchedule.addActionListener(e -> {
            dispose();
            new SearchScheduleForm().setVisible(true);
        });

        btnExit.addActionListener(e -> System.exit(0));

        // === Tambahkan tombol ke panel ===
        panel.add(btnBus);
        panel.add(btnRoute);
        panel.add(btnTicket);
        panel.add(btnTicketList);
        panel.add(btnSearchSchedule);
        panel.add(btnExit);

        add(panel);
    }
}
