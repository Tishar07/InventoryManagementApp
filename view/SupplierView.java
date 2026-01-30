package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import utilities.TopBarFactory;
import view.components.SideMenuBar;

public class SupplierView extends javax.swing.JFrame {
    JPanel MainPanel = new JPanel();
    JPanel RightPanel = new JPanel();
    JLabel LabelLogo = new JLabel("LOGO");
    JLabel LabelTitle = new JLabel("SUPPLIERS");
    JTable SupplierTable;
    JScrollPane TableScrollPane;
    TopBarFactory topbar;


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

        //Side Menu
        SideMenuBar sideMenu = new SideMenuBar();
        sideMenu.SideBarInt();

        MainPanel.add(sideMenu.getSideBarPanel(), BorderLayout.WEST); // Menu
        MainPanel.add(sideMenu.getMainPanel(), BorderLayout.CENTER);  // Content

        JPanel rightPanel = sideMenu.getMainPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);


            // right panel
            RightPanel.setLayout(new BorderLayout());
            RightPanel.setBackground(Color.WHITE);

        String[] sortOptions = {"ID", "Name", "Email", "Status"};
        topbar = new TopBarFactory("Search Supplier:", sortOptions);

        JPanel NorthContainer = new JPanel(new BorderLayout());
        NorthContainer.setBackground(Color.WHITE);

        LabelTitle.setFont(new Font("Arial", Font.BOLD, 28));
        LabelTitle.setForeground(new Color(30, 75, 176));
        LabelTitle.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));

        NorthContainer.add(LabelTitle, BorderLayout.NORTH); // Title at the very top
        NorthContainer.add(topbar, BorderLayout.CENTER);    // The new styled bar below it

        RightPanel.add(NorthContainer, BorderLayout.NORTH);


            // Table - will have to fetch data from db
            String[] columnNames = {"Supplier ID", "Supplier name", "email", "contact number", "status", "Product ID", "Stock Request"};
            Object[][] data = {
                    {"2", "Naga Pushkaar", "nagapushkaar@gmail.com", "+230 51234567", "active", "1", "Pending"},
                    {"3", "Tishar Beerbul", "tisharbeerbul@gmail.com", "+230 52361567", "active", "2", "Approved"},
                    {"8", "Pritisha Jeeha", "pritishajeeha@gmail.com", "+230 51234000", "active", "3", "Rejected"},
                    {"1", "Nawsheen Sham", "nawsheensham@gmail.com", "+230 51256267", "active", "4", "Approved"}
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


            MainPanel.add(RightPanel, BorderLayout.CENTER);
            add(MainPanel);
    }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new SupplierView().setVisible(true));
        }
    }


