/*package view;
import model.Product;
import view.components.SideMenuBar;
import viewModel.ProductViewModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductView extends JFrame{
    private JTable productTable;

    JPanel MainPanel = new JPanel();
    JPanel RightPanel = new JPanel();

    JLabel LabelTitle = new JLabel("PRODUCT MANAGEMENT");
    JLabel LabelName = new JLabel("Product Name:");
    JLabel LabelPrice = new JLabel("Price:");
    JLabel LabelQuantity = new JLabel("Quantity:");
    JLabel LabelSubCategory = new JLabel("SubCategory:");

    JTextField TxtName = new JTextField();
    JTextField TxtPrice = new JTextField();
    JTextField TxtQuantity = new JTextField();
    JComboBox<String> CmbSubCategory = new JComboBox<>();


    JButton AddButton = new JButton("Add Product");
    JButton UpdateButton = new JButton("Update");
    JButton DeleteButton = new JButton("Delete");
    JButton ClearButton = new JButton("Clear");

    public ProductView(ProductViewModel viewModel) {
        this.viewModel = viewModel;
        initComponents();
    }

    private void initComponents() {
        //SET JFrame Properties
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PRODUCT");
        setLocationRelativeTo(null);
        setResizable(true);

        //Set Panel Layout Manager
        MainPanel.setLayout(new BorderLayout());

        //Side Menu
        SideMenuBar sideMenu = new SideMenuBar();
        sideMenu.SideBarInt();
        MainPanel.add(sideMenu, BorderLayout.WEST);

        //Styling thr Right Panel
        RightPanel.setLayout(null);
        RightPanel.setBackground(Color.WHITE);


        // Styling the title
        LabelTitle.setFont(new Font("Arial", Font.BOLD, 22));
        LabelTitle.setForeground(new Color(30, 75, 176));
        LabelTitle.setBounds(40, 30, 400, 30);
        RightPanel.add(LabelTitle);

        //Styling the form

        //styling for name
        LabelName.setBounds(40, 90, 120, 25);
        TxtName.setBounds(160, 90, 220, 30);
        RightPanel.add(LabelName);
        RightPanel.add(TxtName);
        //styling for price
        LabelPrice.setBounds(40, 140, 120, 25);
        TxtPrice.setBounds(160, 140, 220, 30);
        RightPanel.add(LabelPrice);
        RightPanel.add(TxtPrice);
        //styling for quantity
        LabelQuantity.setBounds(40, 190, 120, 25);
        TxtQuantity.setBounds(160, 190, 220, 30);
        RightPanel.add(LabelQuantity);
        RightPanel.add(TxtQuantity);
        //styling for subcategory
        LabelSubCategory.setBounds(40, 240, 120, 25);
        CmbSubCategory.setBounds(160, 240, 220, 30);
        RightPanel.add(LabelSubCategory);
        RightPanel.add(CmbSubCategory);

        //Styling for add button
        AddButton.setBounds(420, 90, 120, 35);
        AddButton.setBackground(new Color(30, 90, 200));
        AddButton.setForeground(Color.WHITE);
        AddButton.setFont(new Font("Arial", Font.BOLD, 14));
        AddButton.setFocusPainted(false);
        AddButton.setBorderPainted(false);
        AddButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        RightPanel.add(AddButton);

        //Styling for update button
        UpdateButton.setBounds(420, 90, 120, 35);
        UpdateButton.setBackground(new Color(30, 90, 200));
        UpdateButton.setForeground(Color.WHITE);
        UpdateButton.setFont(new Font("Arial", Font.BOLD, 14));
        UpdateButton.setFocusPainted(false);
        UpdateButton.setBorderPainted(false);
        UpdateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        UpdateButton.add(UpdateButton);
        RightPanel.add(UpdateButton);

        //Styling for delete button
        DeleteButton.setBounds(420, 90, 120, 35);
        DeleteButton.setBackground(new Color(30, 90, 200));
        DeleteButton.setForeground(Color.WHITE);
        DeleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        DeleteButton.setFocusPainted(false);
        DeleteButton.setBorderPainted(false);
        DeleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        DeleteButton.add(DeleteButton);
        RightPanel.add(DeleteButton);

        //Styling for clear button
        ClearButton.setBounds(420, 90, 120, 35);
        ClearButton.setBackground(new Color(30, 90, 200));
        ClearButton.setForeground(Color.WHITE);
        ClearButton.setFont(new Font("Arial", Font.BOLD, 14));
        ClearButton.setFocusPainted(false);
        ClearButton.setBorderPainted(false);
        ClearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ClearButton.add(ClearButton);
        RightPanel.add(ClearButton);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name","SubCategory", "Price", "Quantity"}, 0);
        productTable.setModel(tableMode);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(40, 310, 700, 300);
        RightPanel.add(scrollPane);

        MainPanel.add(RightPanel, BorderLayout.CENTER);
        Actions();
        add(MainPanel);
    }

    public void Actions() {

        // Add product
        ButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.addProduct();
            }
        });

        // Update product
        ButtonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.updateProduct();
            }
        });

        // Delete product
        ButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.deleteProduct();
            }
        });

        // Clear form
        ButtonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.clearForm();
            }
        });

        // Table
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                viewModel.onTableSelect();
            }
        });

    public JTextField getTxtName() { return TxtName; }
    public JTextField getTxtPrice() { return TxtPrice; }
    public JTextField getTxtQuantity() { return TxtQuantity; }
    public JComboBox<String> getCmbSubCategory() { return CmbSubCategory; }
    public JTable getProductTable() { return productTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    }

*/