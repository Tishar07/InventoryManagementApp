package view;

import DAO.ProductDAO;
import database.DBConnection;
import model.Category;
import model.Product;
import model.Subcategory;
import model.Supplier;
import utilities.*;
import view.components.SideMenuBar;
import viewModel.ProductFormViewModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;


public class ProductFormView extends JPanel{
    private ProductFormViewModel viewModel;
    view.components.SideMenuBar skeleton = new SideMenuBar();
    GridBagConstraints gbc = new GridBagConstraints();
    Category[] categoryData;
    Subcategory[] SubcategoryData;
    Supplier[] supplierData;
    JPanel Form = new JPanel();

    JPanel BackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    JButton BackBtn = ButtonFactory.createButtonPlain();

    JPanel ProductNamePanel = new JPanel();
    JLabel ProductNameLabel = LabelFactory.creatFormLabel();
    JTextField ProductNameText = TextFieldFactory.createFormTextField();

    JPanel UnitPricePanel = new JPanel();
    JLabel UnitPriceLabel = LabelFactory.creatFormLabel();
    JTextField UnitPriceField = new JTextField();

    JPanel StatusPanel = new JPanel();
    JLabel StatusLabel = LabelFactory.creatFormLabel();
    String[] Status ={"Available","Unavailable"};
    JComboBox StatusComboBox = ComboBoxFactory.createFormComboBox(Status);

    JPanel CategoryPanel = new JPanel();
    JLabel CategoryLabel = LabelFactory.creatFormLabel();
    JComboBox CategoryComboBox ; ;


    JPanel SubCategoryPanel = new JPanel();
    String[] SubCategoryValues={""};
    JLabel SubCategoryLabel = LabelFactory.creatFormLabel();
    JButton SubCategoryAddBtn = ButtonFactory.createButtonPlain();
    JButton SubCategoryRemoveBtn = ButtonFactory.createButtonPlain();
    JComboBox SubCategoryComboBox;
    JPanel SubCatPanelComboBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    JTable SubCategoryTable ;
    DefaultTableModel SubModel ;


    JPanel GenderPanel = new JPanel();
    JLabel GenderLabel = LabelFactory.creatFormLabel();
    ButtonGroup Gender = new ButtonGroup();
    JRadioButton male = new JRadioButton("Male");
    JRadioButton female = new JRadioButton("Female");
    JRadioButton unisex = new JRadioButton("Unisex");

    String [] SupplierData ;
    JPanel SupplierPanel = new JPanel();
    JLabel SupplierLabel = LabelFactory.creatFormLabel();
    JButton SupplierAddBtn = ButtonFactory.createButtonPlain();
    JButton SupplierRemoveBtn = ButtonFactory.createButtonPlain();
    JComboBox SupplierComboBox ;
    JTable SupplierTable = new JTable();
    JPanel SupplierPanelComboBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    DefaultTableModel SupplierModel ;

    JPanel UploadPanel = new JPanel();
    JLabel UploadImgLabel = LabelFactory.creatFormLabel();
    JButton UploadImgBtn = ButtonFactory.createButtonPlain();
    String PathImageFile ;
    JLabel imageLabel;

    JPanel ImagePanel = new JPanel();

    JPanel ActionPanel = new  JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
    JButton CancelBtn = ButtonFactory.createButtonPlain();
    JButton SaveBtn = ButtonFactory.createButtonPlain();

    int productID;
    Boolean EditMode=false;
    String CategorySelected;

    //Create Mode
    public ProductFormView(ProductFormViewModel ViewModel){
        this.viewModel=ViewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    //Create View/Edit
    public ProductFormView(ProductFormViewModel ViewModel,int ProductID){
        EditMode=true;
        productID = ProductID;
        this.viewModel=ViewModel;
        skeleton.SideBarInt();
        initComponents();
        SaveBtn.setText("Update");
        FetchDetails();
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

        // Product Name
        gbc.gridy++;
        ProductNameLabel.setText("Product Name");
        ProductNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ProductNamePanel.setLayout(new BoxLayout(ProductNamePanel, BoxLayout.Y_AXIS));
        ProductNamePanel.setBackground(Color.WHITE);
        ProductNamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        ProductNameText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        ProductNameText.setAlignmentX(Component.LEFT_ALIGNMENT);
        ProductNamePanel.add(ProductNameLabel);
        ProductNamePanel.add(Box.createVerticalStrut(5));
        ProductNamePanel.add(ProductNameText);
        Form.add(ProductNamePanel, gbc);

        //UnitPrice
        gbc.gridy++;
        UnitPriceLabel.setText("Unit Price");
        UnitPriceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        UnitPricePanel.setLayout(new BoxLayout(UnitPricePanel, BoxLayout.Y_AXIS));
        UnitPricePanel.setBackground(Color.WHITE);
        UnitPricePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        UnitPriceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        UnitPriceField.setAlignmentX(Component.LEFT_ALIGNMENT);

        UnitPricePanel.add(UnitPriceLabel);
        UnitPricePanel.add(Box.createVerticalStrut(5));
        UnitPricePanel.add(UnitPriceField);
        Form.add(UnitPricePanel, gbc);


        //Product Status
        gbc.gridy++;
        StatusLabel.setText("Product Status");
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



        // Category
        gbc.gridy++;
        CategoryComboBox = ComboBoxFactory.createFormComboBox(InitCategoryCombo());
        CategoryLabel.setText("Category");
        CategoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        CategoryPanel.setLayout(new BoxLayout(CategoryPanel, BoxLayout.Y_AXIS));
        CategoryPanel.setBackground(Color.WHITE);
        CategoryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        CategoryComboBox.setMaximumSize(new Dimension(300, 30));
        CategoryComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        CategoryPanel.add(CategoryLabel);
        CategoryPanel.add(Box.createVerticalStrut(5));
        CategoryPanel.add(CategoryComboBox);
        Form.add(CategoryPanel, gbc);

        //SubCategory
        gbc.gridy++;
        SubCategoryComboBox=ComboBoxFactory.createFormComboBox(SubCategoryValues);
        SubCategoryPanel.setLayout(new BoxLayout(SubCategoryPanel,BoxLayout.Y_AXIS));
        SubCategoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        SubCategoryLabel.setText("Sub Category");
        SubCategoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        SubCategoryAddBtn.setText("+ Add");
        SubCategoryAddBtn.setBackground(Color.green);
        SubCategoryAddBtn.setForeground(Color.white);

        SubCategoryRemoveBtn.setText("- Remove");
        SubCategoryRemoveBtn.setBackground(Color.red);
        SubCategoryRemoveBtn.setForeground(Color.white);

        String[] colCategory = {"Sub Category Selected"};

        //Data to Fetch
        String[] SubCol = {"Subcategory Selected"};
        SubModel= new DefaultTableModel(SubCol,0);
        SubCategoryTable = new JTable(SubModel);
        SubCategoryTable.setRowHeight(30);
        SubCategoryTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JScrollPane subCatScroll = new JScrollPane(SubCategoryTable);
        subCatScroll.setPreferredSize(new Dimension(0, 120));
        SubCatPanelComboBox.add(SubCategoryComboBox);
        SubCatPanelComboBox.add(SubCategoryAddBtn);
        SubCatPanelComboBox.add(SubCategoryRemoveBtn);

        SubCategoryPanel.add(SubCategoryLabel);
        SubCategoryPanel.add(SubCatPanelComboBox);
        SubCategoryPanel.add(subCatScroll);

        Form.add(SubCategoryPanel,gbc);

        // Gender
        gbc.gridy++;
        GenderLabel.setText("Gender");
        GenderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GenderPanel.setLayout(new BoxLayout(GenderPanel, BoxLayout.Y_AXIS));
        GenderPanel.setBackground(Color.WHITE);
        GenderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        male.setAlignmentX(Component.LEFT_ALIGNMENT);
        female.setAlignmentX(Component.LEFT_ALIGNMENT);
        unisex.setAlignmentX(Component.LEFT_ALIGNMENT);
        male.setFont(new Font("Dialog",Font.PLAIN,15));
        female.setFont(new Font("Dialog",Font.PLAIN,15));
        unisex.setFont(new Font("Dialog",Font.PLAIN,15));
        male.setBackground(Color.WHITE);
        female.setBackground(Color.WHITE);
        unisex.setBackground(Color.WHITE);
        Gender.add(male);
        Gender.add(female);
        Gender.add(unisex);
        GenderPanel.add(GenderLabel);
        GenderPanel.add(male);
        GenderPanel.add(female);
        GenderPanel.add(unisex);
        Form.add(GenderPanel, gbc);

        //Supplier Data Table
        gbc.gridy++;
        SupplierComboBox=ComboBoxFactory.createFormComboBox(InitSupplierCombo());
        SupplierPanel.setLayout(new BoxLayout(SupplierPanel,BoxLayout.Y_AXIS));
        SupplierPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        SupplierLabel.setText("Supplier(s)");
        SupplierLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        SupplierAddBtn.setText("+ Add");
        SupplierAddBtn.setBackground(Color.green);
        SupplierAddBtn.setForeground(Color.white);

        SupplierRemoveBtn.setText("- Remove");
        SupplierRemoveBtn.setBackground(Color.red);
        SupplierRemoveBtn.setForeground(Color.white);

        String[] colSupplier = {"Supplier Selected"};
        SupplierModel = new DefaultTableModel(colSupplier,0);
        SupplierTable = new JTable(SupplierModel);
        SupplierTable.setRowHeight(30);
        SupplierTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JScrollPane supplierScroll = new JScrollPane(SupplierTable);
        supplierScroll.setPreferredSize(new Dimension(0, 120));

        SupplierPanelComboBox.add(SupplierComboBox);
        SupplierPanelComboBox.add(SupplierAddBtn);
        SupplierPanelComboBox.add(SupplierRemoveBtn);

        SupplierPanel.add(SupplierLabel);
        SupplierPanel.add(SupplierPanelComboBox);
        SupplierPanel.add(supplierScroll);

        Form.add(SupplierPanel,gbc);

        //Upload Image
        gbc.gridy++;
        UploadPanel.setLayout(new BoxLayout(UploadPanel,BoxLayout.Y_AXIS));

        UploadPanel.setBackground(Color.WHITE);
        UploadPanel.add(UploadImgLabel);
        UploadPanel.add(Box.createVerticalStrut(10));
        UploadImgBtn.setText("Upload");
        UploadImgLabel.setText("Sample Image Upload ");
        UploadImgBtn.setBackground(Color.BLUE);
        UploadImgBtn.setForeground(Color.white);
        UploadPanel.add(UploadImgBtn);
        Form.add(UploadPanel,gbc);

        //Image Panel
        gbc.gridy++;
        ImagePanel.setLayout(new BoxLayout(ImagePanel,BoxLayout.Y_AXIS));
        ImagePanel.setBackground(Color.black);
        Form.add(ImagePanel,gbc);

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
    public String[] InitCategoryCombo(){
        ArrayList<String> data = new ArrayList<>();
        categoryData = viewModel.FetchCategory();
        for (Category c : categoryData){
            data.add(c.getName());
        }
        return data.toArray(new String[0]);
    }
    public String[] InitSubCategoryCombo(String category){
        ArrayList<String> data = new ArrayList<>();
        SubcategoryData = viewModel.FetchSubcategory(category);
        for (Subcategory Sc : SubcategoryData){
            data.add(Sc.getName());
        }
        return data.toArray(new String[0]);
    }
    public String[] InitSupplierCombo(){
        ArrayList<String> data = new ArrayList<>();
        supplierData = viewModel.FetchSupplier();
        for (Supplier Sc : supplierData){
            data.add(Sc.getName());
        }
        return data.toArray(new String[0]);
    }
    public void Actions(){
        BackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Change to Product main page
                Navigator.showProduct();
            }
        });
        CancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Change to Product main page
                Navigator.showLogin();
            }
        });
        CategoryComboBox.addActionListener(e -> {
            String categoryName = (String) CategoryComboBox.getSelectedItem();
            String[] subCategories = InitSubCategoryCombo(categoryName);
            if (EditMode==true || categoryName !=CategorySelected){
                SubModel.setRowCount(0);
            }
            if (EditMode == true && categoryName==CategorySelected){
                Subcategory[] s = viewModel.FetchSubcategory(productID);
                for(Subcategory sub: s){
                    SubModel.addRow(new Object[]{sub.getName()});
                }
            }

            SubCategoryComboBox.setModel(
                    new DefaultComboBoxModel<>(subCategories)
            );

        });
        SupplierTableAction();
        SubCategoryTableAction();
        UploadImage();
        Save();
    }
    public void SupplierTableAction(){
        SupplierAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object Value = SupplierComboBox.getSelectedItem();
                Object[] suppCat = new Object[SupplierModel.getRowCount()];
                for (int i = 0; i < SupplierModel.getRowCount(); i++) {
                    suppCat[i] = SupplierModel.getValueAt(i, 0);
                }
                boolean found = Arrays.asList(suppCat).contains(Value);
                if(found){
                    String StrVal = Value.toString();
                    JOptionPane.showMessageDialog(
                            null,
                            StrVal+" Already in Supplier List!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                }else{
                    SupplierModel.addRow(new Object[]{
                            Value
                    });
                }
            }
        });
        SupplierRemoveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = SupplierTable.getSelectedRow();
                if (selectedRow!=-1){
                    SupplierModel.removeRow(selectedRow);
                }else{
                    JOptionPane.showMessageDialog(
                            null,
                            "Please select a row to delete!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    public void SubCategoryTableAction(){
        SubCategoryAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object Value = SubCategoryComboBox.getSelectedItem();
                Object[] subCat = new Object[SubModel.getRowCount()];
                for (int i = 0; i < SubModel.getRowCount(); i++) {
                    subCat[i] = SubModel.getValueAt(i, 0);
                }
                boolean found = Arrays.asList(subCat).contains(Value);
                if(found){
                    String StrVal = Value.toString();
                    JOptionPane.showMessageDialog(
                            null,
                            StrVal+" Already in SubCategory List!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                }else{
                    SubModel.addRow(new Object[]{
                            Value
                    });
                }
            }
        });
        SubCategoryRemoveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = SubCategoryTable.getSelectedRow();
                if (selectedRow!=-1){
                    SubModel.removeRow(selectedRow);
                }else{
                    JOptionPane.showMessageDialog(
                            null,
                            "Please select a row to delete!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    public void UploadImage() {
        if (imageLabel == null) {
            imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);
            ImagePanel.setLayout(new BorderLayout());
            ImagePanel.add(imageLabel, BorderLayout.CENTER);
        }
        UploadImgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Image");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter imageFilter =
                        new FileNameExtensionFilter("Image Files (*.jpg, *.jpeg, *.png, *.gif)",
                                "jpg", "jpeg", "png", "gif");
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setFileFilter(imageFilter);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    PathImageFile = selectedFile.getAbsolutePath();
                    ImageIcon imageIcon = new ImageIcon(PathImageFile);
                    int panelWidth = ImagePanel.getWidth() > 0 ? ImagePanel.getWidth() : 400;
                    int panelHeight = ImagePanel.getHeight() > 0 ? ImagePanel.getHeight() : 300;
                    Image scaledImage = imageIcon.getImage().getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(scaledImage);
                    imageLabel.setIcon(imageIcon);
                    ImagePanel.revalidate();
                    ImagePanel.repaint();
                }
            }
        });
    }
    public void Save(){
        SaveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[] SelectedSubCat = new int[SubModel.getRowCount()];
                int[] SelectedSupplier =new int[SupplierModel.getRowCount()];
                int SelectedCategory=-1;
                String GenderValue="";

                //Get Product Name
                String ProductName = ProductNameText.getText();

                //Get Unit Price
                String UnitPriceStr = UnitPriceField.getText() ;
                double UnitPriceNum = 0;
                try {
                    UnitPriceNum = Double.parseDouble(UnitPriceStr);
                } catch (NumberFormatException x) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid number: " + UnitPriceStr,
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

                //Get Product Status
                Object selected = StatusComboBox.getSelectedItem();
                String StatusStr = (selected != null) ? selected.toString() : "";


                //Get Category
                Object Category = CategoryComboBox.getSelectedItem();

                String CatName = (String) Category;
                for (int i = 0 ;i<categoryData.length;i++){
                    if (CatName.equals(categoryData[i].getName())){
                        SelectedCategory = categoryData[i].getId();
                        break;
                    }
                }

                //Get SubCategory
                int Subindex = 0;
                for (int i = 0; i < SubModel.getRowCount(); i++) {
                    String subCatName = (String) SubModel.getValueAt(i, 0);
                    for (int j = 0; j < SubcategoryData.length; j++) {
                        if (subCatName.equals(SubcategoryData[j].getName())) {
                            SelectedSubCat[Subindex] = SubcategoryData[j].getId();
                            Subindex++;
                            break;
                        }
                    }
                }
                //Get Gender
                for (Enumeration<AbstractButton> buttons = Gender.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        GenderValue = button.getText();
                        break;
                    }
                }

                //Supplier
                int Supplierindex = 0;
                for (int i = 0; i < SupplierModel.getRowCount(); i++) {
                    String SupplierName = (String) SupplierModel.getValueAt(i, 0);
                    for (int j = 0; j < supplierData.length; j++) {
                        if (SupplierName.equals(supplierData[j].getName())) {
                            SelectedSupplier[Supplierindex] = supplierData[j].getSupplierid();
                            Supplierindex++;
                            break;
                        }
                    }
                }
                if(EditMode==true){
                    String UpdateStatus = viewModel.update(ProductName,UnitPriceNum,StatusStr,GenderValue,PathImageFile,SelectedCategory,SelectedSubCat,SelectedSupplier,productID);
                    if (Objects.equals(UpdateStatus, "Successfully Updated !")){
                        JOptionPane.showMessageDialog(
                                null,
                                "Successfully Saved !",
                                "Update Status",
                                JOptionPane.PLAIN_MESSAGE
                        );

                    }else{
                        JOptionPane.showMessageDialog(
                                null,
                                UpdateStatus,
                                "Update Unsuccessful",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }else{
                    String SaveStatus = viewModel.save(ProductName,UnitPriceNum,StatusStr,GenderValue,PathImageFile,SelectedCategory,SelectedSubCat,SelectedSupplier);
                    if (Objects.equals(SaveStatus, "Successfully Saved !")){
                        JOptionPane.showMessageDialog(
                                null,
                                "Successfully Saved !",
                                "Save Status",
                                JOptionPane.PLAIN_MESSAGE
                        );

                    }else{
                        JOptionPane.showMessageDialog(
                                null,
                                SaveStatus,
                                "Save Unsuccessful",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

            }
        });

    }

    // Edit/View Mode
    public void FetchDetails(){
        FetchProduct_DetailsExisting();
        FetchCategoryExisting();
        FetchSubCategoryExisting();
        FetchSupplierExisting();
    }
    public void FetchSubCategoryExisting(){
        Subcategory[] s = viewModel.FetchSubcategory(productID);
        for(Subcategory sub: s){
            SubModel.addRow(new Object[]{sub.getName()});
        }
    }
    public void FetchSupplierExisting(){
        Supplier[] s = viewModel.FetchSupplierExistingProduct(productID);
        for(Supplier sup: s){
            SupplierModel.addRow(new Object[]{sup.getName()});
        }
    }
    public void FetchCategoryExisting(){
        Category c = viewModel.FetchCategoryExistingProduct(productID);
        String value = c.getName();
        for(int i =0 ; i<= categoryData.length-1;i++){
            if (categoryData[i].getName().equals(value)){
                CategoryComboBox.setSelectedIndex(i);
                CategorySelected = categoryData[i].getName();
                break;
            }
        }
    }
    public void FetchProduct_DetailsExisting(){
        Product Existing = viewModel.FetchExistingProduct(productID);
        ProductNameText.setText(Existing.getProductName());
        UnitPriceField.setText(Existing.getUnitPrice().toString());
        String status = Existing.getProductStatus();
        switch (status){
            case "Available":
                StatusComboBox.setSelectedIndex(0);
                break;
            case "Unavailable":
                StatusComboBox.setSelectedIndex(1);
                break;
        }

        String gender = Existing.getGender();
        switch (gender){
            case "Male":
                male.setSelected(true);
                break;
            case "Female":
                female.setSelected(true);
                break;
            case "Unisex":
                unisex.setSelected(true);
                break;
            default:
                System.out.println("Invalid Gender Fetch From DB");
                break;
        }
        PathImageFile=Existing.getImagePath();
        ImageIcon imageIcon = new ImageIcon(PathImageFile);
        int panelWidth = ImagePanel.getWidth() > 0 ? ImagePanel.getWidth() : 400;
        int panelHeight = ImagePanel.getHeight() > 0 ? ImagePanel.getHeight() : 300;
        Image scaledImage = imageIcon.getImage().getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(imageIcon);
        ImagePanel.revalidate();
        ImagePanel.repaint();
    }

    public static void main(String[] args) throws SQLException {
        Connection conn= DBConnection.getConnection();
        ProductDAO dao = new ProductDAO(conn);
        ProductFormViewModel pvm = new ProductFormViewModel(dao);
        ProductFormView pv =new ProductFormView(pvm,8);
        JFrame test = new JFrame();
        test.add(pv);
        test.setVisible(true);
    }

}
