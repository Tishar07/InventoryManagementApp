package view;


import utilities.*;
import view.components.SideMenuBar;
import viewModel.RetailerFormViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;

public class RetailerFormView extends JPanel {
    private final RetailerFormViewModel viewModel;
    view.components.SideMenuBar skeleton = new SideMenuBar();
    JPanel Form = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();
    JScrollPane scrollPane = new JScrollPane();

    JPanel BackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    JButton BackBtn = ButtonFactory.createButtonPlain();

    JPanel RetailerNamePanel = new JPanel();
    JLabel RetailerNameLabel = LabelFactory.creatFormLabel();
    JTextField RetailerNameText = TextFieldFactory.createFormTextField();


    JPanel RetailerEmailPanel = new JPanel();
    JLabel RetailerEmailLabel = LabelFactory.creatFormLabel();
    JTextField RetailerEmailText = TextFieldFactory.createFormTextField();

    JPanel RetailerContactNumberPanel = new JPanel();
    JLabel RetailerContactNumberLabel = LabelFactory.creatFormLabel();
    JTextField RetailerContactNumberText = TextFieldFactory.createFormTextField();

    JPanel RetailerAddressPanel = new JPanel();
    JLabel RetailerAddressLabel = LabelFactory.creatFormLabel();
    JTextField RetailerAddressText = TextFieldFactory.createFormTextField();

    JPanel StatusPanel = new JPanel();
    JLabel StatusLabel = LabelFactory.creatFormLabel();
    JComboBox StatusComboBox ;
    String [] StatusValue={"Active","Inactive"};


    JPanel ActionPanel = new  JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
    JButton CancelBtn = ButtonFactory.createButtonPlain();
    JButton SaveBtn = ButtonFactory.createButtonPlain();

    int RetailerID;
    boolean IsEdit = false;


    //Create Mode
    public  RetailerFormView(RetailerFormViewModel rvm){
        this.viewModel = rvm;
        skeleton.SideBarInt();
        initComponents();
    }

    //View/Edit Mode
    public  RetailerFormView(RetailerFormViewModel rvm,int retailerId){
        this.viewModel = rvm;
        skeleton.SideBarInt();
        RetailerID = retailerId;
        IsEdit=true;
        initComponents();
        FetchDetails();
    }

    public void initComponents(){
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

        BackPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        BackPanel.setBackground(Color.WHITE);
        BackBtn.setText("← Back");
        BackBtn.setBackground(new Color(220, 53, 69));
        BackBtn.setForeground(Color.WHITE);
        BackPanel.add(BackBtn);
        Form.add(BackPanel, gbc);


        // Retailer Name
        gbc.gridy++;
        RetailerNameLabel.setText("Retailer Name");
        RetailerNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        RetailerNamePanel.setLayout(new BoxLayout(RetailerNamePanel, BoxLayout.Y_AXIS));
        RetailerNamePanel.setBackground(Color.WHITE);
        RetailerNamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        RetailerNameText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        RetailerNameText.setAlignmentX(Component.LEFT_ALIGNMENT);
        RetailerNamePanel.add(RetailerNameLabel);
        RetailerNamePanel.add(Box.createVerticalStrut(5));
        RetailerNamePanel.add(RetailerNameText);
        Form.add(RetailerNamePanel, gbc);


        //Email
        gbc.gridy++;
        RetailerEmailLabel.setText("Email");
        RetailerEmailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        RetailerEmailPanel.setLayout(new BoxLayout(RetailerEmailPanel, BoxLayout.Y_AXIS));
        RetailerEmailPanel.setBackground(Color.WHITE);
        RetailerEmailPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        RetailerEmailText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        RetailerEmailText.setAlignmentX(Component.LEFT_ALIGNMENT);
        RetailerEmailPanel.add(RetailerEmailLabel);
        RetailerEmailPanel.add(Box.createVerticalStrut(5));
        RetailerEmailPanel.add(RetailerEmailText);
        Form.add(RetailerEmailPanel, gbc);

        //Address
        gbc.gridy++;
        RetailerAddressLabel.setText("Address");
        RetailerAddressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        RetailerAddressPanel.setLayout(new BoxLayout(RetailerAddressPanel, BoxLayout.Y_AXIS));
        RetailerAddressPanel.setBackground(Color.WHITE);
        RetailerAddressPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        RetailerAddressText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        RetailerAddressText.setAlignmentX(Component.LEFT_ALIGNMENT);
        RetailerAddressPanel.add(RetailerAddressLabel);
        RetailerAddressPanel.add(Box.createVerticalStrut(5));
        RetailerAddressPanel.add(RetailerAddressText);
        Form.add(RetailerAddressPanel, gbc);

        //Contact Number
        gbc.gridy++;
        RetailerContactNumberLabel.setText("Contact Number");
        RetailerContactNumberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        RetailerContactNumberPanel.setLayout(new BoxLayout(RetailerContactNumberPanel, BoxLayout.Y_AXIS));
        RetailerContactNumberPanel.setBackground(Color.WHITE);
        RetailerContactNumberPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        RetailerContactNumberText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        RetailerContactNumberText.setAlignmentX(Component.LEFT_ALIGNMENT);
        RetailerContactNumberPanel.add(RetailerContactNumberLabel);
        RetailerContactNumberPanel.add(Box.createVerticalStrut(5));
        RetailerContactNumberPanel.add(RetailerContactNumberText);
        Form.add(RetailerContactNumberPanel, gbc);


        //Status
        gbc.gridy++;
        StatusComboBox = ComboBoxFactory.createFormComboBox(StatusValue);
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

        //Action Panel
        gbc.gridy++;

        ActionPanel.setBackground(Color.WHITE);

        CancelBtn.setText("Cancel");
        CancelBtn.setForeground(Color.WHITE);
        CancelBtn.setBackground(Color.GRAY);
        CancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));


        if (!IsEdit){
            SaveBtn.setText("Save");
        }else {
            SaveBtn.setText("Update");
        }
        SaveBtn.setForeground(Color.WHITE);
        SaveBtn.setBackground(Color.GREEN);
        SaveBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));

        ActionPanel.add(CancelBtn);
        ActionPanel.add(SaveBtn);
        Form.add(ActionPanel,gbc);

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
    public void Actions(){
        BackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showRetailer();
            }
        });

        CancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Navigator.showRetailer();
            }
        });
        SaveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String RetailerName = RetailerNameText.getText();
                String RetailerEmail =RetailerEmailText.getText();
                String RetailerAddress = RetailerAddressText.getText();
                String RetailerContactNumber = RetailerContactNumberText.getText();
                String Status = (String) StatusComboBox.getSelectedItem();
                String Message;
                if(!IsEdit){
                    Message=viewModel.save(RetailerName,RetailerEmail,RetailerAddress,RetailerContactNumber,Status);
                    if (Objects.equals(Message, "Saved Successfully")){
                        JOptionPane.showMessageDialog(
                                null,
                                Message,
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        Navigator.showRetailer();
                    }else{
                        JOptionPane.showMessageDialog(
                                null,
                                Message,
                                "Warning", JOptionPane.WARNING_MESSAGE);

                    }
                }else{
                    Message =viewModel.update(RetailerID,RetailerName,RetailerEmail,RetailerAddress,RetailerContactNumber,Status);
                    if (Objects.equals(Message, "Updated Successfully")){
                        JOptionPane.showMessageDialog(
                                null,
                                Message,
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        Navigator.showRetailer();
                    }else{
                        JOptionPane.showMessageDialog(
                                null,
                                Message,
                                "Warning", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });

    }

    public void FetchDetails(){
        //get Existing Retailer from vm
        Map<String,String> data = viewModel.FetchRetailerExisting(RetailerID);
        RetailerNameText.setText(data.get("name"));
        RetailerEmailText.setText(data.get("email"));
        RetailerContactNumberText.setText(data.get("contact"));
        RetailerAddressText.setText(data.get("address"));
        if(Objects.equals(data.get("status"), "Active")){
            StatusComboBox.setSelectedIndex(0);
        }else {
            StatusComboBox.setSelectedIndex(1);
        }

    }



}
