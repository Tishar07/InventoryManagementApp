package view;
import utilities.Navigator;
import utilities.TopBarFactory;
import view.components.SideMenuBar;
import viewModel.StockInViewModel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockInView extends JPanel {
    private StockInViewModel viewModel;
    view.components.SideMenuBar skeleton = new SideMenuBar();
    JPanel StockInPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JLabel StockInLabel = new JLabel("Stock In");
    JPanel HeaderPanel = new JPanel();
    DefaultTableModel StockInModel;
    JTable StockInTable;
    TopBarFactory topbar;
    String[] sortOptions = {"ID", "Name", "Status"};
    String[] columnNames = {"Transaction ID", "Product name", "Supplier Name", "Quantity", "status", "Date"};
    Object[][] data;
    JScrollPane TableScrollPane;

    private static final Color GREEN     = new Color(34, 139, 34);
    private static final Color GREEN_BG  = new Color(220, 255, 220);
    private static final Color RED       = new Color(180, 30, 30);
    private static final Color RED_BG    = new Color(255, 220, 220);
    private static final Color ORANGE    = new Color(180, 100, 0);
    private static final Color ORANGE_BG = new Color(255, 240, 210);
    private static final Color GREY      = new Color(100, 100, 100);
    private static final Color ROW_ALT   = new Color(248, 250, 255);
    private static final Color WHITE     = Color.WHITE;

    public StockInView(StockInViewModel viewModel) {
        this.viewModel = viewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());
        StockInPanel.setLayout(new BoxLayout(StockInPanel, BoxLayout.Y_AXIS));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        StockInPanel.setLayout(new BorderLayout());
        HeaderPanel.setLayout(new BoxLayout(HeaderPanel, BoxLayout.Y_AXIS));

        StockInLabel.setFont(new Font("Arial", Font.BOLD, 28));
        StockInLabel.setForeground(new Color(30, 75, 176));
        StockInLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));
        HeaderPanel.add(StockInLabel);

        topbar = new TopBarFactory("Search Retailer:", "");
        HeaderPanel.add(topbar);

        data = viewModel.FetchStockIn();
        StockInModel = new DefaultTableModel(data, columnNames);
        StockInTable = new JTable(StockInModel);

        StockInTable.setFont(new Font("Arial", Font.PLAIN, 14));
        StockInTable.setRowHeight(30);
        StockInTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        StockInTable.getTableHeader().setBackground(new Color(230, 230, 250));
        StockInTable.setGridColor(new Color(220, 220, 220));
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(StockInModel);
        StockInTable.setRowSorter(sorter);
        StockInTable.setDefaultEditor(Object.class, null);

        applyRenderers();

        TableScrollPane = new JScrollPane(StockInTable);
        TableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        StockInPanel.setLayout(new BorderLayout());
        StockInPanel.add(HeaderPanel, BorderLayout.NORTH);
        StockInPanel.add(TableScrollPane, BorderLayout.CENTER);

        scrollPane.setViewportView(StockInPanel);
        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.CENTER);
        Actions();
    }

    private void applyRenderers() {
        StockInTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Arial", Font.BOLD, 12));
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                if (!isSelected && value != null) {
                    switch (value.toString()) {
                        case "Completed": setForeground(GREEN);  setBackground(GREEN_BG);  break;
                        case "Waiting":   setForeground(ORANGE); setBackground(ORANGE_BG); break;
                        case "Cancelled": setForeground(RED);    setBackground(RED_BG);    break;
                        default:          setForeground(GREY);   setBackground(row % 2 == 0 ? WHITE : ROW_ALT);
                    }
                }
                return this;
            }
        });
    }

    public void Actions() {
        topbar.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ValueToSearch = topbar.getTxtSearch().getText();
                if (ValueToSearch.isEmpty()) {
                    Object[][] defaultData = viewModel.FetchStockIn();
                    StockInModel.setRowCount(0);
                    StockInModel.setDataVector(defaultData, columnNames);
                } else {
                    Object[][] SearchValues = viewModel.SearchStockIn(ValueToSearch);
                    StockInModel.setRowCount(0);
                    StockInModel.setDataVector(SearchValues, columnNames);
                }
                applyRenderers();
            }
        });

        topbar.getBtnViewEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = StockInTable.getSelectedRow();
                int transactionID = (Integer) data[row][0];
                if (row != -1) {
                    Navigator.showStockInFormEdit(transactionID);
                }
            }
        });

        topbar.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showStockInForm();
            }
        });

        topbar.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StockInTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Select a row to Delete !",
                            "Instruction", JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure you want to Cancel Order?",
                            "Cancel Order Warning",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (choice == JOptionPane.YES_OPTION) {
                        int selectedRow = StockInTable.getSelectedRow();
                        int TransactionID = (int) StockInTable.getValueAt(selectedRow, 0);
                        boolean response = viewModel.CancelOrder(TransactionID);
                        if (!response) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Order Not be Cancelled !",
                                    "ERROR", JOptionPane.ERROR_MESSAGE
                            );
                        } else {
                            data = viewModel.FetchStockIn();
                            StockInModel.setDataVector(data, columnNames);
                            applyRenderers();
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Order Cancelled !",
                                    "Success", JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    }
                }
            }
        });
    }
}