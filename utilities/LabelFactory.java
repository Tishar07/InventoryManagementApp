package utilities;

import javax.swing.*;
import java.awt.*;

public class LabelFactory {
    private LabelFactory(){}
    public static JLabel creatFormLabel(){
        JLabel label = new JLabel();
        label.setFont(new Font("Dialog", Font.BOLD, 18));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }
}
