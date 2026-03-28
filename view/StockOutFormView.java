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

    JButton BackBtn = ButtonFactory.createButtonPlain();

    // ── Type dropdown (RETAILER_OUT / DISPOSED) ──────────────────
    JPanel TypePanel = new JPanel();
    JLabel TypeLabel = LabelFactory.creatFormLabel();
    String[] TypeValues = {"RETAILER_OUT", "DISPOSED"};
    JComboBox<String> TypeComboBox = ComboBoxFactory.createFormComboBox(TypeValues);

    // ── Retailer (hidden when DISPOSED) ─────────────────────────
    JPanel RetailerPanel = new JPanel();
    JLabel RetailerLabel = LabelFactory.creatFormLabel();
    JComboBox<String> RetailerComboBox = ComboBoxFactory.createFormComboBox(new String[]{});
    int[] retailerIds;

    JPanel ProductPanel = new JPanel();
    JLabel ProductLabel = LabelFactory.creatFormLabel();
    JComboBox<String> ProductComboBox = ComboBoxFactory.createFormComboBox(new String[]{});
    int[] productIds;

    JPanel QuantityPanel = new JPanel();
    JLabel QuantityLabel = LabelFactory.creatFormLabel();
    JTextField QuantityText = TextFieldFactory.createFormTextField();

    JPanel StatusPanel = new JPanel();
    JLabel StatusLabel = LabelFactory.creatFormLabel();
    String[] StatusValues = {"Waiting", "Completed", "Cancelled"};
    JComboBox<String> StatusComboBox = ComboBoxFactory.createFormComboBox(StatusValues);

    JPanel NotesPanel = new JPanel();
    JLabel NotesLabel = LabelFactory.creatFormLabel();
    JTextArea NotesArea = new JTextArea();

    JPanel ActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
    JButton CancelBtn = ButtonFactory.createButtonPlain();
    JButton SaveBtn   = ButtonFactory.createButtonPlain();

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

        Dimension fieldSize = new Dimension(300, 30);

        // ── Back ─────────────────────────────────────────────────
        JPanel BackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        BackPanel.setBackground(Color.WHITE);
        BackBtn.setText("← Back");
        BackBtn.setBackground(new Color(220, 53, 69));
        BackBtn.setForeground(Color.WHITE);
        BackPanel.add(BackBtn);
        Form.add(BackPanel, gbc);

        // ── Type ─────────────────────────────────────────────────
        gbc.gridy++;
        TypeLabel.setText("Type");
        TypePanel.setLayout(new BoxLayout(TypePanel, BoxLayout.Y_AXIS));
        TypePanel.setBackground(Color.WHITE);
        TypeComboBox.setMaximumSize(fieldSize);
        TypeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        TypePanel.add(TypeLabel);
        TypePanel.add(Box.createVerticalStrut(5));
        TypePanel.add(TypeComboBox);
        Form.add(TypePanel, gbc);

        // ── Retailer (visible by default for RETAILER_OUT) ───────
        gbc.gridy++;
        RetailerLabel.setText("Retailer");
        RetailerPanel.setLayout(new BoxLayout(RetailerPanel, BoxLayout.Y_AXIS));
        RetailerPanel.setBackground(Color.WHITE);
        RetailerComboBox.setMaximumSize(fieldSize);
        RetailerComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        Object[][] retailers = viewModel.FetchRetailers();
        retailerIds = new int[retailers.length];
        for (int i = 0; i < retailers.length; i++) {
            retailerIds[i] = (int) retailers[i][0];
            RetailerComboBox.addItem((String) retailers[i][1]);
        }
        RetailerPanel.add(RetailerLabel);
        RetailerPanel.add(Box.createVerticalStrut(5));
        RetailerPanel.add(RetailerComboBox);
        Form.add(RetailerPanel, gbc);

        // ── Product ──────────────────────────────────────────────
        gbc.gridy++;
        ProductLabel.setText("Product");
        ProductPanel.setLayout(new BoxLayout(ProductPanel, BoxLayout.Y_AXIS));
        ProductPanel.setBackground(Color.WHITE);
        ProductComboBox.setMaximumSize(fieldSize);
        ProductComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        // ── Quantity ─────────────────────────────────────────────
        gbc.gridy++;
        QuantityLabel.setText("Quantity");
        QuantityPanel.setLayout(new BoxLayout(QuantityPanel, BoxLayout.Y_AXIS));
        QuantityPanel.setBackground(Color.WHITE);
        QuantityText.setMaximumSize(fieldSize);
        QuantityText.setAlignmentX(Component.LEFT_ALIGNMENT);
        QuantityPanel.add(QuantityLabel);
        QuantityPanel.add(Box.createVerticalStrut(5));
        QuantityPanel.add(QuantityText);
        Form.add(QuantityPanel, gbc);

        // ── Status ───────────────────────────────────────────────
        gbc.gridy++;
        StatusLabel.setText("Status");
        StatusPanel.setLayout(new BoxLayout(StatusPanel, BoxLayout.Y_AXIS));
        StatusPanel.setBackground(Color.WHITE);
        StatusComboBox.setMaximumSize(fieldSize);
        StatusComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        StatusPanel.add(StatusLabel);
        StatusPanel.add(Box.createVerticalStrut(5));
        StatusPanel.add(StatusComboBox);
        Form.add(StatusPanel, gbc);

        // ── Notes ────────────────────────────────────────────────
        gbc.gridy++;
        NotesLabel.setText("Notes");
        NotesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        NotesPanel.setLayout(new BoxLayout(NotesPanel, BoxLayout.Y_AXIS));
        NotesPanel.setBackground(Color.WHITE);
        NotesArea.setLineWrap(true);
        NotesArea.setWrapStyleWord(true);
        NotesArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        NotesArea.setRows(3);
        NotesArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        JScrollPane noteScroll = new JScrollPane(NotesArea);
        noteScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        noteScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        NotesPanel.add(NotesLabel);
        NotesPanel.add(Box.createVerticalStrut(5));
        NotesPanel.add(noteScroll);
        Form.add(NotesPanel, gbc);

        // ── Action buttons ───────────────────────────────────────
        gbc.gridy++;
        ActionPanel.setBackground(Color.WHITE);
        CancelBtn.setText("Cancel");
        CancelBtn.setForeground(Color.WHITE);
        CancelBtn.setBackground(Color.GRAY);
        CancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        SaveBtn.setText(IsEdit ? "Update" : "Save");
        SaveBtn.setForeground(Color.WHITE);
        SaveBtn.setBackground(Color.GREEN);
        SaveBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        ActionPanel.add(CancelBtn);
        ActionPanel.add(SaveBtn);
        Form.add(ActionPanel, gbc);

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

    public void Actions() {
        BackBtn.addActionListener(e -> Navigator.showStockOut());
        CancelBtn.addActionListener(e -> Navigator.showStockOut());

        // ── Hide/show Retailer based on Type selection ────────────
        TypeComboBox.addActionListener(e -> {
            boolean isRetailerOut = Objects.equals(
                    TypeComboBox.getSelectedItem(), "RETAILER_OUT");
            RetailerPanel.setVisible(isRetailerOut);
        });

        SaveBtn.addActionListener(e -> {
            String type = (String) TypeComboBox.getSelectedItem();
            boolean isRetailerOut = "RETAILER_OUT".equals(type);

            // Retailer is 0 if DISPOSED
            int selectedRetailerId = isRetailerOut
                    ? retailerIds[RetailerComboBox.getSelectedIndex()]
                    : 0;
            int selectedProductId = productIds[ProductComboBox.getSelectedIndex()];
            String quantity = QuantityText.getText();
            String status   = (String) StatusComboBox.getSelectedItem();
            String notes    = NotesArea.getText();

            String message;
            if (!IsEdit) {
                message = viewModel.save(
                        selectedRetailerId, selectedProductId,
                        quantity, type, status, notes);
            } else {
                message = viewModel.update(
                        TransactionID, selectedRetailerId, selectedProductId,
                        quantity, type, status, notes);
            }

            String successMsg = IsEdit ? "Updated Successfully" : "Saved Successfully";
            if (Objects.equals(message, successMsg)) {
                JOptionPane.showMessageDialog(null, message, "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                Navigator.showStockOut();
            } else {
                JOptionPane.showMessageDialog(null, message, "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public void FetchDetails() {
        Map<String, String> data = viewModel.FetchExistingStockOut(TransactionID);
        if (data.isEmpty()) return;

        // Pre-select Type
        String existingType = data.get("transactionType");
        for (int i = 0; i < TypeValues.length; i++) {
            if (TypeValues[i].equals(existingType)) {
                TypeComboBox.setSelectedIndex(i);
                break;
            }
        }

        // Show/hide retailer based on type
        boolean isRetailerOut = "RETAILER_OUT".equals(existingType);
        RetailerPanel.setVisible(isRetailerOut);

        if (isRetailerOut) {
            int existingRetailerId = Integer.parseInt(data.get("retailerId"));
            for (int i = 0; i < retailerIds.length; i++) {
                if (retailerIds[i] == existingRetailerId) {
                    RetailerComboBox.setSelectedIndex(i);
                    break;
                }
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
        NotesArea.setText(data.get("notes"));

        String existingStatus = data.get("status");
        for (int i = 0; i < StatusValues.length; i++) {
            if (StatusValues[i].equals(existingStatus)) {
                StatusComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
}