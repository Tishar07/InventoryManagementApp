package utilities;

import javax.swing.*;
import java.awt.*;

public class ComboBoxFactory {

    private ComboBoxFactory() {}

    public static <T> JComboBox<T> createFormComboBox(T[] items) {

        JComboBox<T> combo = new JComboBox<>(items);

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        combo.setBackground(Color.WHITE);
        combo.setFocusable(false);

        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        return combo;
    }
}
