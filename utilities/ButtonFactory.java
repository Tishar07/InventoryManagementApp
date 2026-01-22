package utilities;

import javax.swing.*;
import java.awt.*;

public class ButtonFactory extends JButton {
    public ButtonFactory(){}

    public static JButton createButtonPlain(){
        JButton Btn = new JButton();
        Btn.setFocusPainted(false);
        Btn.setBorderPainted(false);
        Btn.setOpaque(true);
        Btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        Btn.setPreferredSize(new Dimension(100, 36));
        return Btn;
    }
}
