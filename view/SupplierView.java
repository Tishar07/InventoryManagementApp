package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;


public class SupplierView extends javax.swing.JFrame {
    JPanel MainPanel = new JPanel();
    JPanel NavPanel = new JPanel();
    JPanel RightPanel = new JPanel();
    JLabel LabelLogo = new JLabel("LOGO");
    JLabel LabelTitle = new JLabel("SUPPLIERS");
    JTable SupplierTable;
    JScrollPane TableScrollPane;
    JButton AddButton = new JButton("Add Retailer");
    JButton UpdateButton = new JButton("Update");
    JButton DeleteButton = new JButton("Delete");


    public SupplierView() {
        initComponents();
    }

    private void initComponents() {
        // set Jframe properties
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SUPPLIERS");
        setLocationRelativeTo(null);
        setResizable(true);

        // set Panel layout manager
        MainPanel.setLayout(new BorderLayout());

        // style nav panel
        NavPanel.setBackground(new Color(30, 75, 176));
        NavPanel.setPreferredSize(new Dimension(250, 400));
        NavPanel.setLayout(new GridLayout(6, 1, 0, 10)); // 6 rows, 1 column, 10px vertical gap

        //style nav buttons
        String[] menuItems = {"Dashboard", "Products", "Suppliers", "Stock", "Sales", "Reports"};
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setFont(new Font("Arial", Font.PLAIN, 16));
            btn.setBackground(new Color(30, 90, 200));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setMaximumSize(new Dimension(200, 45));
            NavPanel.add(btn);
        };

            // right panel
            RightPanel.setLayout(new BorderLayout());
            RightPanel.setBackground(Color.WHITE);

            // title
            JPanel TopPanel = new JPanel(new BorderLayout());
            TopPanel.setBackground(Color.WHITE);
            LabelTitle.setFont(new Font("Arial", Font.BOLD, 28));
            LabelTitle.setForeground(new Color(30, 75, 176));
            LabelTitle.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));
            TopPanel.add(LabelTitle, BorderLayout.WEST);
            RightPanel.add(TopPanel, BorderLayout.NORTH);

            // Table - will have to fetch data from db
            String[] columnNames = {"Retailer ID", "Product ID", "Stock Qty", "Transaction ID"};
            Object[][] data = {
                    {"3", "1", 120, "1"},
                    {"6", "2", 85, "2"},
                    {"7", "3", 300, "3"},
                    {"9", "4", 190, "4"}
            };

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            SupplierTable = new JTable(model);
            SupplierTable.setFont(new Font("Arial", Font.PLAIN, 14));
            SupplierTable.setRowHeight(30);
            SupplierTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
            SupplierTable.getTableHeader().setBackground(new Color(230, 230, 250));
            SupplierTable.setGridColor(new Color(220, 220, 220));

            TableScrollPane = new JScrollPane(SupplierTable);
            TableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
            RightPanel.add(TableScrollPane, BorderLayout.CENTER);

            // style button for add, update, delete supplier
            JPanel ButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            ButtonPanel.setBackground(Color.WHITE);
            ButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 30));
            JButton[] buttons = {AddButton, UpdateButton, DeleteButton};
            for (JButton actionButton : buttons) {
                actionButton.setFont(new Font("Arial", Font.BOLD, 14));
                actionButton.setBackground(new Color(30, 90, 200));
                actionButton.setForeground(Color.WHITE);
                actionButton.setFocusPainted(false);
                actionButton.setBorderPainted(false);
                actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                actionButton.setPreferredSize(new Dimension(150, 40));
                ButtonPanel.add(actionButton);
            }

            RightPanel.add(ButtonPanel, BorderLayout.SOUTH);

            MainPanel.add(NavPanel, BorderLayout.WEST);
            MainPanel.add(RightPanel, BorderLayout.CENTER);

            add(MainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupplierView().setVisible(true));
    }
}
