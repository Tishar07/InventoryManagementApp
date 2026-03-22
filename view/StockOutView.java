package view;

import utilities.Navigator;
import utilities.TopBarFactory;
import view.components.SideMenuBar;
import viewModel.StockOutViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class StockOutView extends JPanel {

    private final StockOutViewModel viewModel;
    SideMenuBar skeleton = new SideMenuBar();
    JPanel StockOutPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JLabel StockOutLabel = new JLabel("Stock Out");
    JPanel HeaderPanel = new JPanel();
    DefaultTableModel StockOutTableModel;
    JTable StockOutTable;
    TopBarFactory topbar;
    JScrollPane TableScrollPane;

    // ── First constructor needs a sortOptions array ───────────────
    String[] sortOptions = {"ID", "Retailer", "Product", "Quantity", "Status"};
    String[] columnNames = {"Transaction ID", "Retailer", "Product", "Quantity", "Status", "Notes", "Date"};
    Object[][] data;

    public StockOutView(StockOutViewModel viewModel) {
        this.viewModel = viewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());

        StockOutLabel.setFont(new Font("Arial", Font.BOLD, 28));
        StockOutLabel.setForeground(new Color(30, 75, 176));
        StockOutLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));

        // ── First constructor: all 4 buttons visible ──────────────
        topbar = new TopBarFactory("Search Stock Out:", sortOptions);

        HeaderPanel.setLayout(new BoxLayout(HeaderPanel, BoxLayout.Y_AXIS));
        HeaderPanel.add(StockOutLabel);
        HeaderPanel.add(topbar);

        data = viewModel.FetchStockOuts();
        StockOutTableModel = new DefaultTableModel(data, columnNames);
        StockOutTable = new JTable(StockOutTableModel);

        StockOutTable.setFont(new Font("Arial", Font.PLAIN, 14));
        StockOutTable.setRowHeight(30);
        StockOutTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        StockOutTable.getTableHeader().setBackground(new Color(230, 230, 250));
        StockOutTable.setGridColor(new Color(220, 220, 220));
        StockOutTable.setDefaultEditor(Object.class, null);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(StockOutTableModel);
        StockOutTable.setRowSorter(sorter);

        TableScrollPane = new JScrollPane(StockOutTable);
        TableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        StockOutPanel.setLayout(new BorderLayout());
        StockOutPanel.add(HeaderPanel, BorderLayout.NORTH);
        StockOutPanel.add(TableScrollPane, BorderLayout.CENTER);

        scrollPane.setViewportView(StockOutPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.CENTER);

        Actions();
    }

    public void Actions() {

        // ── Add ───────────────────────────────────────────────────
        topbar.getBtnAdd().addActionListener(e -> Navigator.showStockOutForm());

        // ── Cancel (Delete button relabelled) ─────────────────────
        topbar.getBtnDelete().addActionListener(e -> {
            if (StockOutTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null,
                        "Select a row to Cancel!",
                        "Instruction", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to cancel this stock out?",
                        "Cancel Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (choice == JOptionPane.YES_OPTION) {
                    int selectedRow   = StockOutTable.getSelectedRow();
                    int transactionId = (int) StockOutTable.getValueAt(selectedRow, 0);
                    String message    = viewModel.Cancel(transactionId);

                    if (message.equals("Successfully Cancelled")) {
                        data = viewModel.FetchStockOuts();
                        StockOutTableModel.setDataVector(data, columnNames);
                        JOptionPane.showMessageDialog(null,
                                message, "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                message, "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        // ── View / Edit ───────────────────────────────────────────
        topbar.getBtnViewEdit().addActionListener(e -> {
            if (StockOutTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null,
                        "Select a row to View/Edit!",
                        "Instruction", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int selectedRow   = StockOutTable.getSelectedRow();
                int transactionId = (int) StockOutTable.getValueAt(selectedRow, 0);
                Navigator.showStockOutFormViewEdit(transactionId);
            }
        });

        // ── Search ────────────────────────────────────────────────
        topbar.getBtnSearch().addActionListener(e -> {
            String value = topbar.getTxtSearch().getText();
            if (value.isEmpty()) {
                StockOutTableModel.setDataVector(viewModel.FetchStockOuts(), columnNames);
            } else {
                StockOutTableModel.setDataVector(viewModel.SearchStockOuts(value), columnNames);
            }
        });
    }
}