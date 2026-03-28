package view;

import utilities.*;
import view.components.SideMenuBar;
import viewModel.SupplierFormViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;

public class SupplierFormView extends JPanel {
    private final SupplierFormViewModel viewModel;
    SideMenuBar skeleton = new SideMenuBar();
    JPanel Form = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();
//    JScrollPane scrollPane = new JScrollPane();

    JPanel BackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    JButton BackBtn = ButtonFactory.createButtonPlain();

    JPanel SupplierNamePanel = new JPanel();
    JLabel SupplierNameLabel = LabelFactory.creatFormLabel();
    JTextField SupplierNameText = TextFieldFactory.createFormTextField();

    JPanel SupplierEmailPanel = new JPanel();
    JLabel SupplierEmailLabel = LabelFactory.creatFormLabel();
    JTextField SupplierEmailText = TextFieldFactory.createFormTextField();

    JPanel SupplierContactNumberPanel = new JPanel();
    JLabel SupplierContactNumberLabel = LabelFactory.creatFormLabel();
    JTextField SupplierContactNumberText = TextFieldFactory.createFormTextField();

    JPanel SupplierAddressPanel = new JPanel();
    JLabel SupplierAddressLabel = LabelFactory.creatFormLabel();
    JTextField SupplierAddressText = TextFieldFactory.createFormTextField();

    JPanel StatusPanel = new JPanel();
    JLabel StatusLabel = LabelFactory.creatFormLabel();
    JComboBox StatusComboBox;
    String[] StatusValue = {"Active", "Inactive"};

    JPanel ActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
    JButton CancelBtn = ButtonFactory.createButtonPlain();
    JButton SaveBtn = ButtonFactory.createButtonPlain();

    int SupplierID;
    boolean IsEdit = false;

    public SupplierFormView(SupplierFormViewModel svm) {
        this.viewModel = svm;
        skeleton.SideBarInt();
        initComponents();
    }

    public SupplierFormView(SupplierFormViewModel svm, int supplierId) {
        this.viewModel = svm;
        skeleton.SideBarInt();
        SupplierID = supplierId;
        IsEdit = true;
        initComponents();
        FetchDetails();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        JPanel pagePanel = new JPanel(new BorderLayout());

        Form = skeleton.getMainPanel();
        Form.setLayout(new GridBagLayout());
        Form.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 15, 10, 15);

        BackPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        BackPanel.setBackground(Color.WHITE);
        BackBtn.setText("← Back");
        BackBtn.setBackground(new Color(220, 53, 69));
        BackBtn.setForeground(Color.WHITE);
        BackPanel.add(BackBtn);
        Form.add(BackPanel, gbc);

        gbc.gridy++;
        SupplierNameLabel.setText("Supplier Name");
        SupplierNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        SupplierNamePanel.setLayout(new BoxLayout(SupplierNamePanel, BoxLayout.Y_AXIS));
        SupplierNamePanel.setBackground(Color.WHITE);
        SupplierNamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        SupplierNameText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        SupplierNameText.setAlignmentX(Component.LEFT_ALIGNMENT);
        SupplierNamePanel.add(SupplierNameLabel);
        SupplierNamePanel.add(Box.createVerticalStrut(5));
        SupplierNamePanel.add(SupplierNameText);
        Form.add(SupplierNamePanel, gbc);

        gbc.gridy++;
        SupplierEmailLabel.setText("Email");
        SupplierEmailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        SupplierEmailPanel.setLayout(new BoxLayout(SupplierEmailPanel, BoxLayout.Y_AXIS));
        SupplierEmailPanel.setBackground(Color.WHITE);
        SupplierEmailPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        SupplierEmailText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        SupplierEmailText.setAlignmentX(Component.LEFT_ALIGNMENT);
        SupplierEmailPanel.add(SupplierEmailLabel);
        SupplierEmailPanel.add(Box.createVerticalStrut(5));
        SupplierEmailPanel.add(SupplierEmailText);
        Form.add(SupplierEmailPanel, gbc);

        gbc.gridy++;
        SupplierAddressLabel.setText("Address");
        SupplierAddressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        SupplierAddressPanel.setLayout(new BoxLayout(SupplierAddressPanel, BoxLayout.Y_AXIS));
        SupplierAddressPanel.setBackground(Color.WHITE);
        SupplierAddressPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        SupplierAddressText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        SupplierAddressText.setAlignmentX(Component.LEFT_ALIGNMENT);
        SupplierAddressPanel.add(SupplierAddressLabel);
        SupplierAddressPanel.add(Box.createVerticalStrut(5));
        SupplierAddressPanel.add(SupplierAddressText);
        Form.add(SupplierAddressPanel, gbc);

        gbc.gridy++;
        SupplierContactNumberLabel.setText("Contact Number");
        SupplierContactNumberLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // added

        SupplierContactNumberPanel.setLayout(new BoxLayout(SupplierContactNumberPanel, BoxLayout.Y_AXIS));
        SupplierContactNumberPanel.setBackground(Color.WHITE);
        SupplierContactNumberPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        SupplierContactNumberText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        SupplierContactNumberText.setAlignmentX(Component.LEFT_ALIGNMENT); // added
        SupplierContactNumberPanel.add(SupplierContactNumberLabel);
        SupplierContactNumberPanel.add(Box.createVerticalStrut(5));
        SupplierContactNumberPanel.add(SupplierContactNumberText);
        Form.add(SupplierContactNumberPanel, gbc);

        gbc.gridy++;
        StatusComboBox = ComboBoxFactory.createFormComboBox(StatusValue);
        StatusLabel.setText("Status");
        StatusLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // added
        StatusPanel.setLayout(new BoxLayout(StatusPanel, BoxLayout.Y_AXIS));
        StatusPanel.setBackground(Color.WHITE);
        StatusPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        StatusComboBox.setAlignmentX(Component.LEFT_ALIGNMENT); // added
        StatusPanel.add(StatusLabel);
        StatusPanel.add(Box.createVerticalStrut(5));
        StatusPanel.add(StatusComboBox);
        Form.add(StatusPanel, gbc);

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

        BackBtn.addActionListener(e -> Navigator.showSupplier());
        CancelBtn.addActionListener(e -> Navigator.showSupplier());

        SaveBtn.addActionListener(e -> {
            String SupplierName = SupplierNameText.getText();
            String SupplierEmail = SupplierEmailText.getText();
            String SupplierAddress = SupplierAddressText.getText();
            String SupplierContactNumber = SupplierContactNumberText.getText();
            String Status = (String) StatusComboBox.getSelectedItem();
            String Message;

            if (!IsEdit) {
                Message = viewModel.save(
                        SupplierName,
                        SupplierEmail,
                        SupplierAddress,
                        SupplierContactNumber,
                        Status
                );
            } else {
                Message = viewModel.update(
                        SupplierID,
                        SupplierName,
                        SupplierEmail,
                        SupplierAddress,
                        SupplierContactNumber,
                        Status
                );
            }

            if (Objects.equals(Message, IsEdit ? "Updated Successfully" : "Saved Successfully")) {
                JOptionPane.showMessageDialog(null, Message, "Success", JOptionPane.INFORMATION_MESSAGE);
                Navigator.showSupplier();
            } else {
                JOptionPane.showMessageDialog(null, Message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public void FetchDetails() {
        Map<String, String> data = viewModel.FetchSupplierExisting(SupplierID);
        SupplierNameText.setText(data.get("name"));
        SupplierEmailText.setText(data.get("email"));
        SupplierContactNumberText.setText(data.get("contact"));
        SupplierAddressText.setText(data.get("address"));

        StatusComboBox.setSelectedIndex(
                Objects.equals(data.get("status"), "Active") ? 0 : 1
        );
    }
}