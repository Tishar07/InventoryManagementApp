package view;

import utilities.ButtonFactory;
import utilities.ComboBoxFactory;
import utilities.LabelFactory;
import utilities.Navigator;
import utilities.TextFieldFactory;
import view.components.SideMenuBar;
import viewModel.StockOutFormViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class StockOutFormView extends JPanel {

    private final StockOutFormViewModel viewModel;
    SideMenuBar skeleton = new SideMenuBar();

    // Back button
    JPanel BackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    JButton BackBtn = ButtonFactory.createButtonPlain();

    // Retailer
    JPanel RetailerPanel = new JPanel();
    JLabel RetailerLabel = LabelFactory.creatFormLabel();
    JComboBox<String> RetailerComboBox = new JComboBox<>();
    int[] retailerIds;

    // Product
    JPanel ProductPanel = new JPanel();
    JLabel ProductLabel = LabelFactory.creatFormLabel();
    JComboBox<String> ProductComboBox = new JComboBox<>();
    int[] productIds;

    // Quantity
    JPanel QuantityPanel = new JPanel();
    JLabel QuantityLabel = LabelFactory.creatFormLabel();
    JTextField QuantityText = TextFieldFactory.createFormTextField();

    // Status
    JPanel StatusPanel = new JPanel();
    JLabel StatusLabel = LabelFactory.creatFormLabel();
    String[] StatusValues = {"Waiting", "Completed", "Cancelled"};
    JComboBox<String> StatusComboBox = ComboBoxFactory.createFormComboBox(StatusValues);

    // Notes
    JPanel NotesPanel = new JPanel();
    JLabel NotesLabel = LabelFactory.creatFormLabel();
    JTextField NotesText = TextFieldFactory.createFormTextField();

    // Action buttons
    JPanel ActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
    JButton CancelBtn = ButtonFactory.createButtonPlain();
    JButton SaveBtn = ButtonFactory.createButtonPlain();

    int TransactionID;
    boolean IsEdit = false;

    public StockOutFormView(StockOutFormViewModel viewModel) {
        this.viewModel = viewModel;
        skeleton.SideBarInt();
        initComponents();
    }

    public StockOutFormView(StockOutFormViewModel viewModel, int transactionId) {
        this.viewModel = viewModel;
        skeleton.SideBarInt();
        this.TransactionID = transactionId;
        this.IsEdit = true;
        initComponents();
        FetchDetails();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());

        JPanel Form = skeleton.getMainPanel();
        Form.setLayout(new GridBagLayout());
        Form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.NORTH;

        // ── Back Panel ─────────────────────────────
        BackPanel.setBackground(Color.WHITE);
        BackBtn.setText("← Back");
        BackBtn.setBackground(new Color(220, 53, 69));
        BackBtn.setForeground(Color.WHITE);
        BackPanel.add(BackBtn);
        Form.add(BackPanel, gbc);

        // ── Retailer ───────────────────────────────
        gbc.gridy++;
        RetailerLabel.setText("Retailer");
        RetailerPanel.setLayout(new BoxLayout(RetailerPanel, BoxLayout.Y_AXIS));
        RetailerPanel.setBackground(Color.WHITE);
        RetailerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        RetailerComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        RetailerPanel.add(RetailerLabel);
        RetailerPanel.add(Box.createVerticalStrut(5));

        Object[][] retailers = viewModel.FetchRetailers();
        retailerIds = new int[retailers.length];
        for (int i = 0; i < retailers.length; i++) {
            retailerIds[i] = (int) retailers[i][0];
            RetailerComboBox.addItem((String) retailers[i][1]);
        }

        RetailerPanel.add(RetailerComboBox);
        Form.add(RetailerPanel, gbc);

        // ── Product ───────────────────────────────
        gbc.gridy++;
        ProductLabel.setText("Product");
        ProductPanel.setLayout(new BoxLayout(ProductPanel, BoxLayout.Y_AXIS));
        ProductPanel.setBackground(Color.WHITE);
        ProductPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        ProductComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        Object[][] products = viewModel.FetchProducts();
        productIds = new int[products.length];
        for (int i = 0; i < products.length; i++) {
            productIds[i] = (int) products[i][0];
            ProductComboBox.addItem((String) products[i][1]);
        }

        ProductPanel.add(ProductLabel);
        ProductPanel.add(Box.createVerticalStrut(5));
        ProductPanel.add(ProductComboBox);
        Form.add(ProductPanel, gbc);

        // ── Quantity ──────────────────────────────
        gbc.gridy++;
        QuantityLabel.setText("Quantity");
        QuantityPanel.setLayout(new BoxLayout(QuantityPanel, BoxLayout.Y_AXIS));
        QuantityPanel.setBackground(Color.WHITE);
        QuantityPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        QuantityText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        QuantityPanel.add(QuantityLabel);
        QuantityPanel.add(Box.createVerticalStrut(5));
        QuantityPanel.add(QuantityText);
        Form.add(QuantityPanel, gbc);

        // ── Status ────────────────────────────────
        gbc.gridy++;
        StatusLabel.setText("Status");
        StatusPanel.setLayout(new BoxLayout(StatusPanel, BoxLayout.Y_AXIS));
        StatusPanel.setBackground(Color.WHITE);
        StatusPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        StatusComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        StatusPanel.add(StatusLabel);
        StatusPanel.add(Box.createVerticalStrut(5));
        StatusPanel.add(StatusComboBox);
        Form.add(StatusPanel, gbc);

        // ── Notes ─────────────────────────────────
        gbc.gridy++;
        NotesLabel.setText("Notes");
        NotesPanel.setLayout(new BoxLayout(NotesPanel, BoxLayout.Y_AXIS));
        NotesPanel.setBackground(Color.WHITE);
        NotesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        NotesText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        NotesPanel.add(NotesLabel);
        NotesPanel.add(Box.createVerticalStrut(5));
        NotesPanel.add(NotesText);
        Form.add(NotesPanel, gbc);

        // ── Action Panel ──────────────────────────
        gbc.gridy++;
        ActionPanel.setBackground(Color.WHITE);
        CancelBtn.setText("Cancel");
        CancelBtn.setForeground(Color.WHITE);
        CancelBtn.setBackground(Color.GRAY);
        SaveBtn.setText(IsEdit ? "Update" : "Save");
        SaveBtn.setForeground(Color.WHITE);
        SaveBtn.setBackground(Color.GREEN);
        ActionPanel.add(CancelBtn);
        ActionPanel.add(SaveBtn);
        Form.add(ActionPanel, gbc);

        // ── Fill remaining space ──────────────────
        gbc.gridy++;
        gbc.weighty = 1.0;
        Form.add(Box.createVerticalGlue(), gbc);

        // ── Scroll Pane ──────────────────────────
        JScrollPane scrollPane = new JScrollPane(Form);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        pagePanel.add(skeleton, BorderLayout.WEST);
        pagePanel.add(scrollPane, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.CENTER);

        Actions();
    }

    public void Actions() {
        BackBtn.addActionListener(e -> Navigator.showStockOut());
        CancelBtn.addActionListener(e -> Navigator.showStockOut());

        SaveBtn.addActionListener(e -> {
            int selectedRetailerId = retailerIds[RetailerComboBox.getSelectedIndex()];
            int selectedProductId = productIds[ProductComboBox.getSelectedIndex()];
            String quantity = QuantityText.getText();
            String status = (String) StatusComboBox.getSelectedItem();
            String notes = NotesText.getText();

            String message;
            if (!IsEdit) {
                message = viewModel.save(selectedRetailerId, selectedProductId, quantity, status, notes);
            } else {
                message = viewModel.update(TransactionID, selectedRetailerId, selectedProductId, quantity, status, notes);
            }

            String successMsg = IsEdit ? "Updated Successfully" : "Saved Successfully";
            if (Objects.equals(message, successMsg)) {
                JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                Navigator.showStockOut();
            } else {
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public void FetchDetails() {
        Map<String, String> data = viewModel.FetchExistingStockOut(TransactionID);
        if (data.isEmpty()) return;

        int existingRetailerId = Integer.parseInt(data.get("retailerId"));
        for (int i = 0; i < retailerIds.length; i++) {
            if (retailerIds[i] == existingRetailerId) {
                RetailerComboBox.setSelectedIndex(i);
                break;
            }
        }

        int existingProductId = Integer.parseInt(data.get("productId"));
        for (int i = 0; i < productIds.length; i++) {
            if (productIds[i] == existingProductId) {
                ProductComboBox.setSelectedIndex(i);
                break;
            }
        }

        QuantityText.setText(data.get("quantity"));
        NotesText.setText(data.get("notes"));

        String existingStatus = data.get("status");
        for (int i = 0; i < StatusValues.length; i++) {
            if (StatusValues[i].equals(existingStatus)) {
                StatusComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
}