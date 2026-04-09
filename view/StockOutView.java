package view;

import utilities.Navigator;
import utilities.TopBarFactory;
import view.components.SideMenuBar;
import viewModel.StockOutViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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


    String[] columnNames = {"Transaction ID", "Retailer", "Product", "Quantity", "Status","Date"};
    Object[][] data;

    private static final Color GREEN     = new Color(34, 139, 34);
    private static final Color GREEN_BG  = new Color(220, 255, 220);
    private static final Color RED       = new Color(180, 30, 30);
    private static final Color RED_BG    = new Color(255, 220, 220);
    private static final Color ORANGE    = new Color(180, 100, 0);
    private static final Color ORANGE_BG = new Color(255, 240, 210);
    private static final Color GREY      = new Color(100, 100, 100);
    private static final Color ROW_ALT   = new Color(248, 250, 255);
    private static final Color WHITE     = Color.WHITE;

    public StockOutView(StockOutViewModel viewModel) {
        this.viewModel = viewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());

        StockOutLabel.setFont(new Font("Arial", Font.BOLD, 28));
        StockOutLabel.setForeground(Color.white);
        StockOutLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));

        topbar = new TopBarFactory("Search Stock Out:", "");

        HeaderPanel.setLayout(new BoxLayout(HeaderPanel, BoxLayout.Y_AXIS));
        HeaderPanel.add(StockOutLabel);
        HeaderPanel.setBackground(new Color(30, 75, 176));
        topbar.setBackground(new Color(235, 241, 255));
        topbar.setForeground(new Color(61, 83, 193));
        HeaderPanel.add(topbar);

        data = viewModel.FetchStockOuts();

        StockOutTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 3) {
                    return Integer.class;
                }
                if (columnIndex == 5) {
                    return LocalDateTime.class;
                }
                return String.class;
            }
        };

        StockOutTable = new JTable(StockOutTableModel);

        StockOutTable.setFont(new Font("Arial", Font.PLAIN, 14));
        StockOutTable.setRowHeight(30);
        StockOutTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        StockOutTable.getTableHeader().setBackground(new Color(230, 230, 250));
        StockOutTable.setGridColor(new Color(220, 220, 220));
        StockOutTable.setDefaultEditor(Object.class, null);

        TableRowSorter<DefaultTableModel> outSorter =
                new TableRowSorter<>(StockOutTableModel);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        outSorter.setComparator(5, (a, b) -> {
            if (a == null && b == null) return 0;
            if (a == null) return -1;
            if (b == null) return 1;

            LocalDateTime d1 = LocalDateTime.parse(a.toString(), formatter);
            LocalDateTime d2 = LocalDateTime.parse(b.toString(), formatter);

            return d1.compareTo(d2);
        });

        StockOutTable.setAutoCreateRowSorter(false);
        StockOutTable.setRowSorter(outSorter);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        for (int i = 0; i < StockOutTable.getColumnCount(); i++) {
            StockOutTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }

        applyRenderers();


        JTableHeader header = StockOutTable.getTableHeader();
        header.setBackground(new Color(30, 75, 176));
        header.setForeground(Color.white);
        header.setFont(new Font("Arial",Font.BOLD,16));

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

    private void applyRenderers() {
        StockOutTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
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

        topbar.getBtnAdd().addActionListener(e -> Navigator.showStockOutForm());


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

        topbar.getBtnSearch().addActionListener(e -> {
            String value = topbar.getTxtSearch().getText();
            if (value.isEmpty()) {
                StockOutTableModel.setDataVector(viewModel.FetchStockOuts(), columnNames);
            } else {
                StockOutTableModel.setDataVector(viewModel.SearchStockOuts(value), columnNames);
            }
            DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
            for (int i = 0; i < StockOutTable.getColumnCount(); i++) {
                StockOutTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
            }
            applyRenderers();
        });
    }
}