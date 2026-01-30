package utilities;

import javax.swing.*;
import java.awt.*;

public class TextFieldFactory {

    private TextFieldFactory() {} // prevent instantiation

    public static JTextField createFormTextField() {
        JTextField txt = new JTextField();

        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        return txt;
    }
}

