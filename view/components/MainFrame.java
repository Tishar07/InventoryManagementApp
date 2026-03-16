package view.components;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ImageIcon WindowIcon = new ImageIcon("assets/logo.png");

    public MainFrame() {
        setTitle("Inventory Management System");
        setIconImage(WindowIcon.getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1300,700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void showPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    }
}
