package view;

import utilities.Navigator;
import utilities.TopBarFactory;
import view.components.SideMenuBar;
import viewModel.SupplierViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class SupplierView extends JPanel {

    private final SupplierViewModel viewModel;

    SideMenuBar skeleton = new SideMenuBar();
    JPanel SupplierPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane();

    JLabel SupplierLabel = new JLabel("Suppliers");
    JPanel HeaderPanel = new JPanel();

    DefaultTableModel SupplierModel;
    JTable SupplierTable;

    TopBarFactory topbar;

    String[] sortOptions = {"ID", "Name", "Email", "Status"};
    String[] columnNames = {
            "Supplier ID", "Supplier name", "Email",
            "Contact number", "Status", "Product ID", "Stock Request"
    };

    Object[][] data;
    JScrollPane TableScrollPane;

    public SupplierView(SupplierViewModel viewModel) {
        this.viewModel = viewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    public void initComponents() {

        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());

        SupplierPanel.setLayout(new BorderLayout());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        HeaderPanel.setLayout(new BoxLayout(HeaderPanel, BoxLayout.Y_AXIS));

        SupplierLabel.setFont(new Font("Arial", Font.BOLD, 28));
        SupplierLabel.setForeground(new Color(30, 75, 176));
        SupplierLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));
        HeaderPanel.add(SupplierLabel);

        topbar = new TopBarFactory("Search Supplier:", sortOptions);
        HeaderPanel.add(topbar);

        data = viewModel.FetchSuppliers();
        SupplierModel = new DefaultTableModel(data, columnNames);
        SupplierTable = new JTable(SupplierModel);

        SupplierTable.setFont(new Font("Arial", Font.PLAIN, 14));
        SupplierTable.setRowHeight(30);
        SupplierTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        SupplierTable.getTableHeader().setBackground(new Color(230, 230, 250));
        SupplierTable.setGridColor(new Color(220, 220, 220));
        SupplierTable.setDefaultEditor(Object.class, null);

        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(SupplierModel);
        SupplierTable.setRowSorter(sorter);

        TableScrollPane = new JScrollPane(SupplierTable);
        TableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        SupplierPanel.add(HeaderPanel, BorderLayout.NORTH);
        SupplierPanel.add(TableScrollPane, BorderLayout.CENTER);

        scrollPane.setViewportView(SupplierPanel);

        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);

        add(pagePanel, BorderLayout.CENTER);

        Actions();
    }

    public void Actions() {

        topbar.getBtnAdd().addActionListener(e ->
                Navigator.showSupplierForm()
        );

        topbar.getBtnDelete().addActionListener(e -> {

            if (SupplierTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Select a row to Delete!",
                        "Instruction",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete?",
                    "Delete Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                int row = SupplierTable.getSelectedRow();
                int SupplierID = (int) SupplierTable.getValueAt(row, 0);

                String message = viewModel.Delete(SupplierID);
                data = viewModel.FetchSuppliers();
                SupplierModel.setDataVector(data, columnNames);

                JOptionPane.showMessageDialog(
                        null,
                        message,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        topbar.getBtnViewEdit().addActionListener(e -> {

            int row = SupplierTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Select a row to View/Edit!",
                        "Instruction",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                int SupplierID = (int) SupplierTable.getValueAt(row, 0);
                Navigator.showSupplierFormViewEdit(SupplierID);
            }
        });

        topbar.getBtnSearch().addActionListener(e -> {

            String value = topbar.getTxtSearch().getText();

            if (value.isEmpty()) {
                SupplierModel.setDataVector(
                        viewModel.FetchSuppliers(),
                        columnNames
                );
            } else {
                SupplierModel.setDataVector(
                        viewModel.SearchSuppliers(value),
                        columnNames
                );
            }
        });
    }
}