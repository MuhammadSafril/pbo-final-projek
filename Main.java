import ui.MainMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Jalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}
