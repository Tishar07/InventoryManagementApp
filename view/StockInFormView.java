package view;

import DAO.StockInDAO;
import database.DBConnection;
import model.Product;
import model.StockIn;
import model.Supplier;
import utilities.*;
import view.components.SideMenuBar;
import viewModel.StockInFormViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class StockInFormView extends JPanel {
    private StockInFormViewModel viewModel;
    view.components.SideMenuBar skeleton = new SideMenuBar();
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel Form = new JPanel();

    ArrayList<Supplier> supplierArrayList;
    ArrayList<Product> productArrayList;

    JPanel BackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    JButton BackBtn = ButtonFactory.createButtonPlain();

    JPanel NotePanel = new JPanel();
    JLabel NoteLabel = LabelFactory.creatFormLabel();
    JTextArea NoteField = TextFieldFactory.createNotesTextArea();

    JPanel QuantityPanel = new JPanel();
    JLabel QuantityLabel = LabelFactory.creatFormLabel();
    JTextField QuantityField = TextFieldFactory.createFormTextField();

    JPanel StatusPanel = new JPanel();
    JLabel StatusLabel = LabelFactory.creatFormLabel();
    String[] Status ={"Waiting", "Completed", "Cancelled"};
    JComboBox StatusComboBox = ComboBoxFactory.createFormComboBox(Status);

    JPanel ProductPanel = new JPanel();
    JLabel ProductLabel = LabelFactory.creatFormLabel();
    JComboBox ProductComboBox ; ;

    JPanel SupplierPanel = new JPanel();
    JLabel SupplierLabel = LabelFactory.creatFormLabel();
    JComboBox SupplierComboBox ; ;

    JPanel ActionPanel = new  JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
    JButton CancelBtn = ButtonFactory.createButtonPlain();
    JButton SaveBtn = ButtonFactory.createButtonPlain();

    int transactionID;
    String prevStatus;
    Boolean EditMode=false;


    //Create Mode
    public StockInFormView(StockInFormViewModel ViewModel){
        this.viewModel=ViewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    //View/Edit Mode
    public StockInFormView(StockInFormViewModel ViewModel,int TransactionID){
        EditMode=true;
        transactionID = TransactionID;
        this.viewModel=ViewModel;
        skeleton.SideBarInt();
        initComponents();
        SaveBtn.setText("Update");
        FetchDetails(transactionID);
        prevStatus=(String)StatusComboBox.getSelectedItem();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());
        // Main Form Panel
        Form = skeleton.getMainPanel();
        Form.setLayout(new GridBagLayout());
        Form.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 15, 10, 15);

        // Back Button
        BackPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        BackPanel.setBackground(Color.WHITE);
        BackBtn.setText("← Back");
        BackBtn.setBackground(new Color(220, 53, 69));
        BackBtn.setForeground(Color.WHITE);
        BackPanel.add(BackBtn);
        Form.add(BackPanel, gbc);

        //Supplier
        gbc.gridy++;
        SupplierComboBox = ComboBoxFactory.createFormComboBox(getSupplierList());
        SupplierLabel.setText("Supplier");
        SupplierLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        SupplierPanel.setLayout(new BoxLayout(SupplierPanel, BoxLayout.Y_AXIS));
        SupplierPanel.setBackground(Color.WHITE);
        SupplierPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        SupplierComboBox.setMaximumSize(new Dimension(300, 30));
        SupplierComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        SupplierPanel.add(SupplierLabel);
        SupplierPanel.add(Box.createVerticalStrut(5));
        SupplierPanel.add(SupplierComboBox);
        Form.add(SupplierPanel, gbc);

        //Product
        String[] temp ={""};
        gbc.gridy++;
        ProductComboBox = ComboBoxFactory.createFormComboBox(temp);
        ProductLabel.setText("Product");
        ProductLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ProductPanel.setLayout(new BoxLayout(ProductPanel, BoxLayout.Y_AXIS));
        ProductPanel.setBackground(Color.WHITE);
        ProductPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        ProductComboBox.setMaximumSize(new Dimension(300, 30));
        ProductComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        ProductPanel.add(ProductLabel);
        ProductPanel.add(Box.createVerticalStrut(5));
        ProductPanel.add(ProductComboBox);
        Form.add(ProductPanel, gbc);

        //Quantity
        gbc.gridy++;
        QuantityLabel.setText("Quantity");
        QuantityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        QuantityPanel.setLayout(new BoxLayout(QuantityPanel, BoxLayout.Y_AXIS));
        QuantityPanel.setBackground(Color.WHITE);
        QuantityPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        QuantityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        QuantityField.setAlignmentX(Component.LEFT_ALIGNMENT);
        QuantityPanel.add(QuantityLabel);
        QuantityPanel.add(Box.createVerticalStrut(5));
        QuantityPanel.add(QuantityField);
        Form.add(QuantityPanel, gbc);

        //Status
        gbc.gridy++;
        StatusLabel.setText("Status");
        StatusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        StatusPanel.setLayout(new BoxLayout(StatusPanel, BoxLayout.Y_AXIS));
        StatusPanel.setBackground(Color.WHITE);
        StatusPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        StatusComboBox.setMaximumSize(new Dimension(300, 30));
        StatusComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        StatusPanel.add(StatusLabel);
        StatusPanel.add(Box.createVerticalStrut(5));
        StatusPanel.add(StatusComboBox);
        Form.add(StatusPanel, gbc);

        //Note
        gbc.gridy++;
        NoteLabel.setText("Note");
        NoteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        NotePanel.setLayout(new BoxLayout(NotePanel, BoxLayout.Y_AXIS));
        NotePanel.setBackground(Color.WHITE);
        NotePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        JScrollPane noteScroll = new JScrollPane(NoteField);
        noteScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        noteScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        noteScroll.setBorder(null); // optional: cleaner UI
        NotePanel.add(NoteLabel);
        NotePanel.add(Box.createVerticalStrut(5));
        NotePanel.add(noteScroll);
        Form.add(NotePanel, gbc);

        //Form Final Action
        gbc.gridy++;
        ActionPanel.setBackground(Color.WHITE);
        CancelBtn.setText("Cancel");
        CancelBtn.setForeground(Color.WHITE);
        CancelBtn.setBackground(Color.GRAY);
        CancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        SaveBtn.setText("Save");
        SaveBtn.setForeground(Color.WHITE);
        SaveBtn.setBackground(Color.GREEN);
        SaveBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        ActionPanel.add(CancelBtn);
        ActionPanel.add(SaveBtn);
        Form.add(ActionPanel,gbc);

        // Set Form From Top
        gbc.gridy++;
        gbc.weighty = 1;
        Form.add(Box.createVerticalGlue(), gbc);
        JScrollPane scrollPane = new JScrollPane(Form);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.CENTER);
        Actions();
    }

    public String[] getSupplierList() {
        ArrayList<String> supplierList = new ArrayList<>();
        supplierArrayList=viewModel.fetchSupplier();
        for(Supplier s : supplierArrayList){
            supplierList.add(s.getName());
        }
        return supplierList.toArray( new String[0]);
    }

    public void Actions(){
        SupplierComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedSupplier = SupplierComboBox.getSelectedIndex();
                int SupplierID = supplierArrayList.get(selectedSupplier).getSupplierId();
                productArrayList=viewModel.fetchProduct(SupplierID);
                ArrayList<String> productList = new ArrayList<>();
                String[] products;
                supplierArrayList=viewModel.fetchSupplier();
                for(Product p : productArrayList){
                    productList.add(p.getProductName());
                }
                products = productList.toArray(new String[0]);
                ProductComboBox.setModel( new DefaultComboBoxModel<>(products));
            }
        });

        SaveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Message;
                int quantity=0;
                boolean valid = false;
                Product p = productArrayList.get(ProductComboBox.getSelectedIndex());
                Supplier s = supplierArrayList.get(SupplierComboBox.getSelectedIndex());
                String Status =(String) StatusComboBox.getSelectedItem();
                String quantityStr = QuantityField.getText();
                LocalDateTime dateTime = LocalDateTime.now();
                String notes = NoteField.getText();
                try {
                    quantity = Integer.parseInt(quantityStr);
                    valid=true;
                } catch (NumberFormatException f) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid Quantity",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }


                if(EditMode==false){
                    if (valid){
                        Message = viewModel.save(p.getProductId(),s.getSupplierId(),p.getProductName(),s.getName(),
                                quantity,Status,dateTime,notes);
                        JOptionPane.showMessageDialog(null,
                                Message,
                                "Stock In Status",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }else{
                    Message = viewModel.update(transactionID,p.getProductId(),s.getSupplierId(),p.getProductName(),s.getName(),
                            quantity,Status,dateTime,notes,prevStatus);
                    JOptionPane.showMessageDialog(null,
                            Message,
                            "Stock In Status",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        CancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showStockIn();
            }
        });

        BackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showStockIn();
            }
        });
    }

    public void FetchDetails(int transactionID){
        StockIn s = viewModel.FetchExistingStockIn(transactionID);
        setSupplier(s.getSupplierID());
        productArrayList = viewModel.fetchProduct(s.getSupplierID());
        setProduct(s.getProductID());
        QuantityField.setText(String.valueOf(s.getQuantity()));
        setStatus(s.getStatus());
        NoteField.setText(s.getNotes());
    }
    public void setSupplier(int Supplier){
        for(int i =0 ;i<supplierArrayList.size();i++){
            if(supplierArrayList.get(i).getSupplierId()==Supplier){
                SupplierComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    public void setProduct(int productID){
        for(int i =0 ;i<productArrayList.size();i++){
            if(productArrayList.get(i).getProductId()==productID){
                ProductComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    public void setStatus(String status){
        for(int i = 0 ;i<Status.length;i++){
            if(Objects.equals(Status[i], status)){
                StatusComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    public static void main(String[] args) throws SQLException {
        Connection conn= DBConnection.getConnection();
        StockInDAO dao = new StockInDAO(conn);
        StockInFormViewModel pvm = new StockInFormViewModel(dao);
        StockInFormView pv =new StockInFormView(pvm,17);
        JFrame test = new JFrame();
        test.add(pv);
        test.setVisible(true);
    }





}
