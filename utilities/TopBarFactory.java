package utilities;

import javax.swing.*;
import java.awt.*;

public class TopBarFactory {

    private TopBarFactory() {}

    public static JPanel createTopBar(JComponent... components) {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        topBar.setBackground(new Color(240, 240, 240));

        for (JComponent c : components) {
            topBar.add(c);
        }

        return topBar;
    }
}
