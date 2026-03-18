package view;
import view.components.SideMenuBar;
import viewModel.HistoryViewModel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class HistoryView extends JPanel {

    private final HistoryViewModel viewModel;
    SideMenuBar skeleton = new SideMenuBar();
    JPanel HistoryPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JPanel HeaderPanel = new JPanel();
    JPanel SearchPanel = new JPanel();
    JLabel HistoryLabel = new JLabel("History");
    JLabel subtitleLabel = new JLabel("Track all stock movements and transactions");
    DefaultTableModel HistoryModel;
    JTable HistoryTable;
    JScrollPane TableScrollPane;
    JTextField txtSearch = new JTextField(20);
    JButton btnSearch;
    JButton btnReset;
    JComboBox<String> cmbFilter;

    String[] filterOptions = {"ALL", "SUPPLIER_IN", "RETAILER_OUT", "DISPOSED"};
    String[] columnNames = {
            "History ID", "Transaction ID", "Product Name",
            "Supplier", "Retailer", "Quantity",
            "Type", "Status", "Date"
    };

    Object[][] data;

    private static final Color BLUE       = new Color(30, 75, 176);
    private static final Color LIGHT_BLUE = new Color(235, 241, 255);
    private static final Color GREEN      = new Color(34, 139, 34);
    private static final Color GREEN_BG   = new Color(220, 255, 220);
    private static final Color RED        = new Color(180, 30, 30);
    private static final Color RED_BG     = new Color(255, 220, 220);
    private static final Color ORANGE     = new Color(180, 100, 0);
    private static final Color ORANGE_BG  = new Color(255, 240, 210);
    private static final Color GREY       = new Color(100, 100, 100);
    private static final Color ROW_ALT    = new Color(248, 250, 255);
    private static final Color WHITE      = Color.WHITE;

    public HistoryView(HistoryViewModel viewModel) {
        this.viewModel = viewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    public void initComponents() {

        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());

        HistoryPanel.setLayout(new BorderLayout());
        HistoryPanel.setBackground(WHITE);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        // ── Top banner ───────────────────────────────────────────
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBackground(BLUE);
        bannerPanel.setPreferredSize(new Dimension(0, 80));
        bannerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JPanel titleBlock = new JPanel();
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.setOpaque(false);

        HistoryLabel.setFont(new Font("Arial", Font.BOLD, 28));
        HistoryLabel.setForeground(WHITE);

        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(180, 210, 255));

        titleBlock.add(HistoryLabel);
        titleBlock.add(subtitleLabel);
        bannerPanel.add(titleBlock, BorderLayout.WEST);

        JLabel countLabel = new JLabel("Loading...");
        countLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        countLabel.setForeground(new Color(180, 210, 255));
        bannerPanel.add(countLabel, BorderLayout.EAST);

        HeaderPanel.setLayout(new BoxLayout(HeaderPanel, BoxLayout.Y_AXIS));
        HeaderPanel.setBackground(WHITE);
        HeaderPanel.add(bannerPanel);

        SearchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 12));
        SearchPanel.setBackground(LIGHT_BLUE);
        SearchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 215, 245)));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 13));
        searchLabel.setForeground(BLUE);

        txtSearch.setFont(new Font("Arial", Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(220, 32));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 240), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        btnSearch = makeButton("Search", BLUE, WHITE);
        btnReset  = makeButton("Reset", GREY, WHITE);

        JLabel filterLabel = new JLabel("Filter by Type:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 13));
        filterLabel.setForeground(BLUE);

        cmbFilter = new JComboBox<>(filterOptions);
        cmbFilter.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbFilter.setPreferredSize(new Dimension(155, 32));
        cmbFilter.setBackground(WHITE);

        SearchPanel.add(searchLabel);
        SearchPanel.add(txtSearch);
        SearchPanel.add(btnSearch);
        SearchPanel.add(Box.createHorizontalStrut(10));
        SearchPanel.add(filterLabel);
        SearchPanel.add(cmbFilter);
        SearchPanel.add(Box.createHorizontalStrut(10));
        SearchPanel.add(btnReset);

        HeaderPanel.add(SearchPanel);

        data = viewModel.FetchHistory();
        HistoryModel = new DefaultTableModel(data, columnNames) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        HistoryTable = new JTable(HistoryModel);
        HistoryTable.setFont(new Font("Arial", Font.PLAIN, 13));
        HistoryTable.setRowHeight(34);
        HistoryTable.setShowVerticalLines(false);
        HistoryTable.setGridColor(new Color(230, 235, 245));
        HistoryTable.setSelectionBackground(new Color(210, 225, 255));
        HistoryTable.setSelectionForeground(Color.BLACK);
        HistoryTable.setIntercellSpacing(new Dimension(0, 1));
        HistoryTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        HistoryTable.getTableHeader().setBackground(BLUE);
        HistoryTable.getTableHeader().setForeground(WHITE);
        HistoryTable.getTableHeader().setPreferredSize(new Dimension(0, 38));

        HistoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(col == 2 || col == 3 || col == 4 || col == 8
                        ? JLabel.LEFT : JLabel.CENTER);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? WHITE : ROW_ALT);
                    setForeground(Color.DARK_GRAY);
                }
                return this;
            }
        });

        // Type column badge
        HistoryTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Arial", Font.BOLD, 12));
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                if (!isSelected && value != null) {
                    switch (value.toString()) {
                        case "SUPPLIER_IN":  setForeground(GREEN);  setBackground(GREEN_BG);  break;
                        case "RETAILER_OUT": setForeground(RED);    setBackground(RED_BG);    break;
                        case "DISPOSED":     setForeground(ORANGE); setBackground(ORANGE_BG); break;
                        default:             setForeground(GREY);   setBackground(row % 2 == 0 ? WHITE : ROW_ALT);
                    }
                }
                return this;
            }
        });

        HistoryTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
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

        int[] widths = {80, 110, 160, 130, 130, 80, 130, 100, 160};
        for (int i = 0; i < widths.length; i++) {
            HistoryTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(HistoryModel);
        HistoryTable.setRowSorter(sorter);
        TableScrollPane = new JScrollPane(HistoryTable);
        TableScrollPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
        TableScrollPane.getViewport().setBackground(WHITE);
        countLabel.setText("Total Records: " + data.length);
        HistoryPanel.add(HeaderPanel, BorderLayout.NORTH);
        HistoryPanel.add(TableScrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(HistoryPanel);
        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.CENTER);
        Actions(countLabel);
    }

    public void Actions(JLabel countLabel) {

        btnSearch.addActionListener(e -> {
            String value = txtSearch.getText().trim();
            Object[][] result = value.isEmpty()
                    ? viewModel.FetchHistory()
                    : viewModel.SearchHistory(value);
            HistoryModel.setDataVector(result, columnNames);
            countLabel.setText("Total Records: " + result.length);
        });

        cmbFilter.addActionListener(e -> {
            String selected = (String) cmbFilter.getSelectedItem();
            Object[][] result = (selected == null || selected.equals("ALL"))
                    ? viewModel.FetchHistory()
                    : viewModel.FilterByType(selected);
            HistoryModel.setDataVector(result, columnNames);
            countLabel.setText("Total Records: " + result.length);
        });

        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            cmbFilter.setSelectedIndex(0);
            Object[][] result = viewModel.FetchHistory();
            HistoryModel.setDataVector(result, columnNames);
            countLabel.setText("Total Records: " + result.length);
        });
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 32));
        return btn;
    }
}