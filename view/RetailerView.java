package view;


import DAO.RetailerDAO;
import com.mysql.cj.protocol.Message;
import database.DBConnection;
import utilities.Navigator;
import utilities.TopBarFactory;
import view.components.SideMenuBar;
import viewModel.RetailerViewModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class RetailerView extends JPanel {
    private final RetailerViewModel viewModel;
    view.components.SideMenuBar skeleton = new SideMenuBar();
    JPanel RetailerPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JLabel RetailerLabel = new JLabel("Retailers");
    JPanel HeaderPanel = new JPanel();
    DefaultTableModel RetailerModel;
    JTable RetailerTable ;
    TopBarFactory topbar;
    String[] sortOptions = {"ID", "Name", "Email", "Status"};
    String[] columnNames = {"Retailer ID", "Retailer name", "Email", "contact number", "status"};
    Object[][] data ;
    JScrollPane TableScrollPane;



    public RetailerView(RetailerViewModel viewModel){
        this.viewModel = viewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());
        RetailerPanel.setLayout(new BoxLayout(RetailerPanel, BoxLayout.Y_AXIS));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        RetailerPanel.setLayout(new BorderLayout());
        //Set header Panel
        HeaderPanel.setLayout(new BoxLayout(HeaderPanel,BoxLayout.Y_AXIS));

        //Set top label styling
        RetailerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        RetailerLabel.setForeground(new Color(30, 75, 176));
        RetailerLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 0));
        HeaderPanel.add(RetailerLabel);


        //set Top bar menu
        topbar = new TopBarFactory("Search Retailer:", sortOptions);
        HeaderPanel.add(topbar);

        //set Table
        data= viewModel.FetchRetailers();
        RetailerModel = new DefaultTableModel(data, columnNames);
        RetailerTable = new JTable(RetailerModel);

        RetailerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        RetailerTable.setRowHeight(30);
        RetailerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        RetailerTable.getTableHeader().setBackground(new Color(230, 230, 250));
        RetailerTable.setGridColor(new Color(220, 220, 220));
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(RetailerModel);
        RetailerTable.setRowSorter(sorter);
        RetailerTable.setDefaultEditor(Object.class, null);
        TableScrollPane = new JScrollPane(RetailerTable);
        TableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        RetailerPanel.setLayout(new BorderLayout());
        RetailerPanel.add(HeaderPanel, BorderLayout.NORTH);
        RetailerPanel.add(TableScrollPane, BorderLayout.CENTER);

        // Main layout
        scrollPane.setViewportView(RetailerPanel);
        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.CENTER);

        Actions();
    }

    public void Actions(){
        topbar.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showRetailerForm();
            }
        });

        topbar.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(RetailerTable.getSelectedRow()==-1){
                    JOptionPane.showMessageDialog(
                            null,
                            "Select a row to Delete !",
                            "Instruction", JOptionPane.INFORMATION_MESSAGE
                    );

                }else{
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure you want to delete?",
                            "Delete Warning",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if(choice==JOptionPane.YES_OPTION){
                        int selectedRow = RetailerTable.getSelectedRow();
                        int RetailerID = (int) RetailerTable.getValueAt(selectedRow,0);
                        String message=viewModel.Delete(RetailerID);
                        data= viewModel.FetchRetailers();
                        RetailerModel.setDataVector(data,columnNames);
                        JOptionPane.showMessageDialog(
                                null,
                                message,
                                "Success",JOptionPane.INFORMATION_MESSAGE
                                );
                    }
                }
            }
        });

        topbar.getBtnViewEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = RetailerTable.getSelectedRow();
                int RetailerID = (int) RetailerTable.getValueAt(selectedRow,0);
                if(RetailerID==-1){
                    JOptionPane.showMessageDialog(
                            null,
                            "Select a row to View/Edit !",
                            "Instruction", JOptionPane.INFORMATION_MESSAGE
                    );
                }else {
                    Navigator.showRetailerFormViewEdit(RetailerID);
                }
            }
        });

        topbar.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ValueToSearch= topbar.getTxtSearch().getText();
                if (ValueToSearch.isEmpty()){
                    Object[][] defaultData = viewModel.FetchRetailers();
                    RetailerModel.setRowCount(0);
                    RetailerModel.setDataVector(defaultData,columnNames);
                }else {
                    Object[][] SearchValues = viewModel.SearchRetailers(ValueToSearch);
                    RetailerModel.setRowCount(0);
                    RetailerModel.setDataVector(SearchValues,columnNames);
                }

            }
        });

    }
    public static void main(String[] args) throws SQLException {
        Connection conn= DBConnection.getConnection();
        RetailerDAO dao = new RetailerDAO(conn);
        RetailerViewModel pvm = new RetailerViewModel(dao);
        RetailerView pv =new RetailerView(pvm);
        JFrame test = new JFrame();
        test.add(pv);
        test.setVisible(true);
    }

}
