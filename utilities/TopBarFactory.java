package utilities;

import javax.swing.*;
import java.awt.*;

public class TopBarFactory extends JPanel {

    public final JButton btnAdd;
    public final JButton btnClear;
    public final JButton btnUpdate;
    public final JButton btnDelete;
    public final JButton btnSearch;
    public final JComboBox<String> cmbSort;
    public final JTextField txtSearch;

    public TopBarFactory(String searchLabelText, String[] sortOptions) {

        setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        setBackground(new Color(240, 240, 240));

        Font fontBold = new Font("Segoe UI", Font.BOLD, 14);
        Color primaryBlue = new Color(30, 75, 176);

        // Buttons
        btnAdd    = ButtonFactory.createButtonPlain();
        btnClear  = ButtonFactory.createButtonPlain();
        btnUpdate = ButtonFactory.createButtonPlain();
        btnDelete = ButtonFactory.createButtonPlain();
        btnSearch = ButtonFactory.createButtonPlain();

        btnAdd.setText("Add");
        btnClear.setText("Clear");
        btnUpdate.setText("Update");
        btnDelete.setText("Delete");
        btnSearch.setText("Search");

        JButton[] buttons = {
                btnAdd, btnClear, btnUpdate, btnDelete, btnSearch
        };

        for (JButton b : buttons) {
            b.setBackground(primaryBlue);
            b.setForeground(Color.WHITE);
            b.setFont(fontBold);
        }

        // Sort combo
        cmbSort = ComboBoxFactory.createFormComboBox(sortOptions);

        // Search
        JLabel lblSearch = new JLabel(searchLabelText);
        lblSearch.setFont(fontBold);

        txtSearch = new JTextField(40);
        txtSearch.setFont(fontBold);

        // Add components
        add(btnAdd);
        add(btnClear);
        add(btnUpdate);
        add(btnDelete);
        add(cmbSort);
        add(lblSearch);
        add(txtSearch);
        add(btnSearch);
    }
}
