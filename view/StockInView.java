package view;


import utilities.Navigator;
import utilities.TopBarFactory;
import view.components.SideMenuBar;
import viewModel.StockInViewModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StockInView extends JPanel{
    private StockInViewModel viewModel;
    view.components.SideMenuBar skeleton = new SideMenuBar();
    JPanel StockInPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JLabel StockInLabel = new JLabel("Stock In");
    JPanel HeaderPanel = new JPanel();
    DefaultTableModel StockInModel;
    JTable StockInTable ;
    TopBarFactory topbar;
    String[] sortOptions = {"ID", "Name", "Status"};
    String[] columnNames = {"Transaction ID", "Product name", "Supplier Name", "Quantity", "status","Date"};
    Object[][] data ;
    JScrollPane TableScrollPane;





    public StockInView(StockInViewModel viewModel){
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
        //Set header Panel
        HeaderPanel.setLayout(new BoxLayout(HeaderPanel,BoxLayout.Y_AXIS));

        //Set top label styling
        StockInLabel.setFont(new Font("Arial", Font.BOLD, 28));
        StockInLabel.setForeground(new Color(30, 75, 176));
        StockInLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));
        HeaderPanel.add(StockInLabel);


        //set Top bar menu
        topbar = new TopBarFactory("Search Retailer:", "");
        HeaderPanel.add(topbar);

        //set Table
        data= viewModel.FetchStockIn();
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
        TableScrollPane = new JScrollPane(StockInTable);
        TableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        StockInPanel.setLayout(new BorderLayout());
        StockInPanel.add(HeaderPanel, BorderLayout.NORTH);
        StockInPanel.add(TableScrollPane, BorderLayout.CENTER);

        // Main layout
        scrollPane.setViewportView(StockInPanel);
        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.CENTER);

        Actions();
    }

    public void Actions(){
        topbar.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ValueToSearch= topbar.getTxtSearch().getText();
                if (ValueToSearch.isEmpty()){
                    Object[][] defaultData = viewModel.FetchStockIn();
                    StockInModel.setRowCount(0);
                    StockInModel.setDataVector(defaultData,columnNames);
                }else {
                    Object[][] SearchValues = viewModel.SearchStockIn(ValueToSearch);
                    StockInModel.setRowCount(0);
                    StockInModel.setDataVector(SearchValues,columnNames);
                }
            }
        });
        topbar.getBtnViewEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = StockInTable.getSelectedRow();
                int transactionID = (Integer) data[row][0];
                if(row!=-1){
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
                if(StockInTable.getSelectedRow()==-1){
                    JOptionPane.showMessageDialog(
                            null,
                            "Select a row to Delete !",
                            "Instruction", JOptionPane.INFORMATION_MESSAGE
                    );

                }else{
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure you want to Cancel Order?",
                            "Cancel Order Warning",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if(choice==JOptionPane.YES_OPTION){
                        int selectedRow = StockInTable.getSelectedRow();
                        int TransactionID = (int) StockInTable.getValueAt(selectedRow,0);
                        boolean response=viewModel.CancelOrder(TransactionID);
                        if(!response){
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Order Not be Cancelled !",
                                    "ERROR",JOptionPane.ERROR_MESSAGE
                            );

                        }else{
                            data= viewModel.FetchStockIn();
                            StockInModel.setDataVector(data,columnNames);
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Order Cancelled !",
                                    "Success",JOptionPane.INFORMATION_MESSAGE
                            );

                        }

                    }
                }
            }
        });

    }


}
