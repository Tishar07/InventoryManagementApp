package view;

import utilities.TopBarFactory;
import view.components.SideMenuBar;
import viewModel.StockStatusViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class StockStatusView extends JPanel {
    private final StockStatusViewModel viewModel;
    SideMenuBar skeleton = new SideMenuBar();

    JPanel StockStatusPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JLabel StockStatusLabel = new JLabel("Stock Status");
    JPanel HeaderPanel = new JPanel();
    DefaultTableModel StockStatusTableModel;
    JTable StockStatusTable;
    TopBarFactory topbar;
    JScrollPane TableScrollPane;

    String[] columnNames = {"Product ID", "Product Name", "Current Stock"};
    String[] filterOptions = {"Product ID", "Product Name"};
    Object[][] data;

    public StockStatusView(StockStatusViewModel viewModel) {
        this.viewModel = viewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());

        // ── Header label ─────────────────────────────────────────
        StockStatusLabel.setFont(new Font("Arial", Font.BOLD, 28));
        StockStatusLabel.setForeground(new Color(30, 75, 176));
        StockStatusLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));

        // ── Top bar — use first constructor so cmbSort is visible ─
        topbar = new TopBarFactory("Search:", filterOptions);
        topbar.getBtnAdd().setVisible(false);
        topbar.getBtnViewEdit().setVisible(false);
        topbar.getBtnDelete().setVisible(false);

        HeaderPanel.setLayout(new BoxLayout(HeaderPanel, BoxLayout.Y_AXIS));
        HeaderPanel.add(StockStatusLabel);
        HeaderPanel.add(topbar);

        // TABLE
        data = viewModel.FetchStockStatus();
        StockStatusTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        StockStatusTable = new JTable(StockStatusTableModel);
        StockStatusTable.setFont(new Font("Arial", Font.PLAIN, 14));
        StockStatusTable.setRowHeight(30);
        StockStatusTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        StockStatusTable.getTableHeader().setBackground(new Color(230, 230, 250));
        StockStatusTable.setGridColor(new Color(220, 220, 220));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(StockStatusTableModel);
        StockStatusTable.setRowSorter(sorter);

        TableScrollPane = new JScrollPane(StockStatusTable);
        TableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        StockStatusPanel.setLayout(new BorderLayout());
        StockStatusPanel.add(HeaderPanel, BorderLayout.NORTH);
        StockStatusPanel.add(TableScrollPane, BorderLayout.CENTER);

        scrollPane.setViewportView(StockStatusPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.CENTER);

        Actions();
    }

    public void Actions() {
        topbar.getBtnSearch().addActionListener(e -> {
            String value = topbar.getTxtSearch().getText().trim();
            String filter = (String) topbar.getCmbSort().getSelectedItem();

            if (value.isEmpty()) {
                StockStatusTableModel.setDataVector(viewModel.FetchStockStatus(), columnNames);
                return;
            }

            // Filterby
            TableRowSorter<DefaultTableModel> sorter =
                    (TableRowSorter<DefaultTableModel>) StockStatusTable.getRowSorter();

            if ("Product ID".equals(filter)) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + value, 0));
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + value, 1));
            }
        });
    }
}