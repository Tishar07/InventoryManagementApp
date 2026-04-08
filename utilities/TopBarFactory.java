package utilities;

import javax.swing.*;
import java.awt.*;

public class TopBarFactory extends JPanel {

    public final JButton btnAdd;
    public final JButton btnViewEdit;
    public final JButton btnDelete;
    public final JButton btnSearch;
    public final JComboBox<String> cmbSort;
    public final JTextField txtSearch;

    public TopBarFactory(String searchLabelText, String[] sortOptions) {

        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        setBackground(new Color(240, 240, 240));

        Font fontBold = new Font("Segoe UI", Font.BOLD, 14);
        Color primaryBlue = new Color(30, 75, 176);

        // Buttons
        btnAdd    = ButtonFactory.createButtonPlain();
        btnViewEdit = ButtonFactory.createButtonPlain();
        btnDelete = ButtonFactory.createButtonPlain();
        btnSearch = ButtonFactory.createButtonPlain();

        btnAdd.setText("Add");
        btnViewEdit.setText("View/Edit");
        btnDelete.setText("Delete");
        btnSearch.setText("Search");

        JButton[] buttons = {
                btnAdd, btnViewEdit, btnDelete, btnSearch
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

        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                txtSearch.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Top, Left, Bottom, Right padding
        ));

        // Add components
        add(btnAdd);
        add(btnViewEdit);
        add(btnDelete);
        add(cmbSort);
        add(lblSearch);
        add(txtSearch);
        add(btnSearch);
    }

    //Product view
    public TopBarFactory(String searchLabelText, String[] sortOptions, String productViewName) {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Font fontBold = new Font("Segoe UI", Font.BOLD, 14);
        Color primaryBlue = new Color(30, 75, 176);

        // ===== LEFT PANEL (Title + Add Button) =====
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        leftPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(productViewName);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));

        btnAdd = ButtonFactory.createButtonPlain();
        btnAdd.setText("Add");
        btnAdd.setBackground(primaryBlue);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(fontBold);

        leftPanel.add(titleLabel);
        leftPanel.add(btnAdd);

        // ===== RIGHT PANEL (Sort + Search) =====
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        rightPanel.setOpaque(false);

        cmbSort = ComboBoxFactory.createFormComboBox(sortOptions);

        JLabel lblSearch = new JLabel(searchLabelText);
        lblSearch.setFont(fontBold);

        txtSearch = new JTextField(20);
        txtSearch.setFont(fontBold);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        btnSearch = ButtonFactory.createButtonPlain();
        btnSearch.setText("Search");
        btnSearch.setBackground(primaryBlue);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(fontBold);

        // Not used in this constructor
        btnViewEdit = new JButton();
        btnDelete = new JButton();

        rightPanel.add(cmbSort);
        rightPanel.add(lblSearch);
        rightPanel.add(txtSearch);
        rightPanel.add(btnSearch);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    //Stock In and Out
    public TopBarFactory(String searchLabelText, String productViewName) {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Font fontBold = new Font("Segoe UI", Font.BOLD, 14);
        Color primaryBlue = new Color(30, 75, 176);

        // ===== LEFT PANEL (Title + Add Button) =====
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        leftPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(productViewName);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));

        btnAdd = ButtonFactory.createButtonPlain();
        btnAdd.setText("Add");
        btnAdd.setBackground(primaryBlue);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(fontBold);

        leftPanel.add(titleLabel);
        leftPanel.add(btnAdd);


        btnDelete = ButtonFactory.createButtonPlain();
        btnDelete.setText("Cancel");
        btnDelete.setBackground(primaryBlue);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(fontBold);
        //leftPanel.add(btnDelete);

        btnViewEdit = ButtonFactory.createButtonPlain();
        btnViewEdit.setText("View/Edit");
        btnViewEdit.setBackground(primaryBlue);
        btnViewEdit.setForeground(Color.WHITE);
        btnViewEdit.setFont(fontBold);
        leftPanel.add(btnViewEdit);

        // ===== RIGHT PANEL (Sort + Search) =====
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        rightPanel.setOpaque(false);
        String[] sortOptions={""};
        cmbSort = ComboBoxFactory.createFormComboBox(sortOptions);

        JLabel lblSearch = new JLabel(searchLabelText);
        lblSearch.setFont(fontBold);

        txtSearch = new JTextField(20);
        txtSearch.setFont(fontBold);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        btnSearch = ButtonFactory.createButtonPlain();
        btnSearch.setText("Search");
        btnSearch.setBackground(primaryBlue);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(fontBold);





        //rightPanel.add(cmbSort);
        rightPanel.add(lblSearch);
        rightPanel.add(txtSearch);
        rightPanel.add(btnSearch);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    public TopBarFactory(String searchLabelText, int SupplierRetailer) {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Font fontBold = new Font("Segoe UI", Font.BOLD, 14);
        Color primaryBlue = new Color(30, 75, 176);

        // ===== LEFT PANEL (Title + Add Button) =====
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        leftPanel.setOpaque(false);

        String p = "";
        JLabel titleLabel = new JLabel(p);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));

        btnAdd = ButtonFactory.createButtonPlain();
        btnAdd.setText("Add");
        btnAdd.setBackground(primaryBlue);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(fontBold);

        leftPanel.add(titleLabel);
        leftPanel.add(btnAdd);


        btnDelete = ButtonFactory.createButtonPlain();
        btnDelete.setText("Delete");
        btnDelete.setBackground(primaryBlue);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(fontBold);
        leftPanel.add(btnDelete);

        btnViewEdit = ButtonFactory.createButtonPlain();
        btnViewEdit.setText("View/Edit");
        btnViewEdit.setBackground(primaryBlue);
        btnViewEdit.setForeground(Color.WHITE);
        btnViewEdit.setFont(fontBold);
        leftPanel.add(btnViewEdit);

        // ===== RIGHT PANEL (Sort + Search) =====
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        rightPanel.setOpaque(false);
        String[] sortOptions={""};
        cmbSort = ComboBoxFactory.createFormComboBox(sortOptions);

        JLabel lblSearch = new JLabel(searchLabelText);
        lblSearch.setFont(fontBold);

        txtSearch = new JTextField(20);
        txtSearch.setFont(fontBold);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        btnSearch = ButtonFactory.createButtonPlain();
        btnSearch.setText("Search");
        btnSearch.setBackground(primaryBlue);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(fontBold);





        //rightPanel.add(cmbSort);
        rightPanel.add(lblSearch);
        rightPanel.add(txtSearch);
        rightPanel.add(btnSearch);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }


    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnViewEdit() {
        return btnViewEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public JComboBox<String> getCmbSort() {
        return cmbSort;
    }



}
