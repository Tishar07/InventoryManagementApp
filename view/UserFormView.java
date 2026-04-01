package view;

import utilities.*;
import view.components.SideMenuBar;
import viewModel.UserFormViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;

public class UserFormView extends JPanel {
    private final UserFormViewModel viewModel;
    view.components.SideMenuBar skeleton = new SideMenuBar();
    JPanel Form = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();

    JPanel BackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    JButton BackBtn = ButtonFactory.createButtonPlain();

    JPanel NamePanel = new JPanel();
    JLabel NameLabel = LabelFactory.creatFormLabel();
    JTextField NameText = TextFieldFactory.createFormTextField();

    JPanel UsernamePanel = new JPanel();
    JLabel UsernameLabel = LabelFactory.creatFormLabel();
    JTextField UsernameText = TextFieldFactory.createFormTextField();

    JPanel EmailPanel = new JPanel();
    JLabel EmailLabel = LabelFactory.creatFormLabel();
    JTextField EmailText = TextFieldFactory.createFormTextField();

    JPanel AddressPanel = new JPanel();
    JLabel AddressLabel = LabelFactory.creatFormLabel();
    JTextField AddressText = TextFieldFactory.createFormTextField();

    JPanel ContactPanel = new JPanel();
    JLabel ContactLabel = LabelFactory.creatFormLabel();
    JTextField ContactText = TextFieldFactory.createFormTextField();

    // Change password part
    JPanel NewPasswordPanel = new JPanel();
    JLabel NewPasswordLabel = LabelFactory.creatFormLabel();
    JPasswordField NewPasswordText = new JPasswordField();

    JPanel ConfirmPasswordPanel = new JPanel();
    JLabel ConfirmPasswordLabel = LabelFactory.creatFormLabel();
    JPasswordField ConfirmPasswordText = new JPasswordField();

    JPanel ActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
    JButton CancelBtn = ButtonFactory.createButtonPlain();
    JButton UpdateBtn = ButtonFactory.createButtonPlain();

    String Username;

    public UserFormView(UserFormViewModel uvm, String username) {
        this.viewModel = uvm;
        this.Username = username;
        skeleton.SideBarInt();
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

        // name
        gbc.gridy++;
        NameLabel.setText("Name");
        NameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        NamePanel.setLayout(new BoxLayout(NamePanel, BoxLayout.Y_AXIS));
        NamePanel.setBackground(Color.WHITE);
        NamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        NameText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        NameText.setAlignmentX(Component.LEFT_ALIGNMENT);
        NamePanel.add(NameLabel);
        NamePanel.add(Box.createVerticalStrut(5));
        NamePanel.add(NameText);
        Form.add(NamePanel, gbc);

        // Username
        gbc.gridy++;
        UsernameLabel.setText("Username");
        UsernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        UsernamePanel.setLayout(new BoxLayout(UsernamePanel, BoxLayout.Y_AXIS));
        UsernamePanel.setBackground(Color.WHITE);
        UsernamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        UsernameText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        UsernameText.setAlignmentX(Component.LEFT_ALIGNMENT);
        UsernamePanel.add(UsernameLabel);
        UsernamePanel.add(Box.createVerticalStrut(5));
        UsernamePanel.add(UsernameText);
        Form.add(UsernamePanel, gbc);

        // Email
        gbc.gridy++;
        EmailLabel.setText("Email");
        EmailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        EmailPanel.setLayout(new BoxLayout(EmailPanel, BoxLayout.Y_AXIS));
        EmailPanel.setBackground(Color.WHITE);
        EmailPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        EmailText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        EmailText.setAlignmentX(Component.LEFT_ALIGNMENT);
        EmailPanel.add(EmailLabel);
        EmailPanel.add(Box.createVerticalStrut(5));
        EmailPanel.add(EmailText);
        Form.add(EmailPanel, gbc);

        // Address
        gbc.gridy++;
        AddressLabel.setText("Address");
        AddressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        AddressPanel.setLayout(new BoxLayout(AddressPanel, BoxLayout.Y_AXIS));
        AddressPanel.setBackground(Color.WHITE);
        AddressPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        AddressText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        AddressText.setAlignmentX(Component.LEFT_ALIGNMENT);
        AddressPanel.add(AddressLabel);
        AddressPanel.add(Box.createVerticalStrut(5));
        AddressPanel.add(AddressText);
        Form.add(AddressPanel, gbc);

        // Contact
        gbc.gridy++;
        ContactLabel.setText("Contact");
        ContactLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ContactPanel.setLayout(new BoxLayout(ContactPanel, BoxLayout.Y_AXIS));
        ContactPanel.setBackground(Color.WHITE);
        ContactPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        ContactText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        ContactText.setAlignmentX(Component.LEFT_ALIGNMENT);
        ContactPanel.add(ContactLabel);
        ContactPanel.add(Box.createVerticalStrut(5));
        ContactPanel.add(ContactText);
        Form.add(ContactPanel, gbc);

        // change pw
        gbc.gridy++;
        JLabel ChangePwdTitle = new JLabel("Change Password");
        ChangePwdTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        ChangePwdTitle.setForeground(new Color(100, 100, 100));
        ChangePwdTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel DividerPanel = new JPanel();
        DividerPanel.setLayout(new BoxLayout(DividerPanel, BoxLayout.Y_AXIS));
        DividerPanel.setBackground(Color.WHITE);
        DividerPanel.add(Box.createVerticalStrut(10));
        DividerPanel.add(ChangePwdTitle);
        DividerPanel.add(Box.createVerticalStrut(2));
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        DividerPanel.add(separator);
        Form.add(DividerPanel, gbc);

        // new pw
        gbc.gridy++;
        NewPasswordLabel.setText("New Password");
        NewPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        NewPasswordPanel.setLayout(new BoxLayout(NewPasswordPanel, BoxLayout.Y_AXIS));
        NewPasswordPanel.setBackground(Color.WHITE);
        NewPasswordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        NewPasswordText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        NewPasswordText.setAlignmentX(Component.LEFT_ALIGNMENT);
        NewPasswordPanel.add(NewPasswordLabel);
        NewPasswordPanel.add(Box.createVerticalStrut(5));
        NewPasswordPanel.add(NewPasswordText);
        Form.add(NewPasswordPanel, gbc);

        // confirm new pw
        gbc.gridy++;
        ConfirmPasswordLabel.setText("Confirm New Password");
        ConfirmPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ConfirmPasswordPanel.setLayout(new BoxLayout(ConfirmPasswordPanel, BoxLayout.Y_AXIS));
        ConfirmPasswordPanel.setBackground(Color.WHITE);
        ConfirmPasswordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        ConfirmPasswordText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        ConfirmPasswordText.setAlignmentX(Component.LEFT_ALIGNMENT);
        ConfirmPasswordPanel.add(ConfirmPasswordLabel);
        ConfirmPasswordPanel.add(Box.createVerticalStrut(5));
        ConfirmPasswordPanel.add(ConfirmPasswordText);
        Form.add(ConfirmPasswordPanel, gbc);

        // action buttons
        gbc.gridy++;
        ActionPanel.setBackground(Color.WHITE);

        CancelBtn.setText("Cancel");
        CancelBtn.setForeground(Color.WHITE);
        CancelBtn.setBackground(Color.GRAY);
        CancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));

        UpdateBtn.setText("Update");
        UpdateBtn.setForeground(Color.WHITE);
        UpdateBtn.setBackground(Color.GREEN);
        UpdateBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));

        ActionPanel.add(CancelBtn);
        ActionPanel.add(UpdateBtn);
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
        BackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showDashboard();
            }
        });

        CancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showDashboard();
            }
        });

        UpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Name            = NameText.getText();
                String NewUsername     = UsernameText.getText();
                String Email           = EmailText.getText();
                String Address         = AddressText.getText();
                String Contact         = ContactText.getText();
                String NewPassword     = new String(NewPasswordText.getPassword());
                String ConfirmPassword = new String(ConfirmPasswordText.getPassword());

                String Message = viewModel.update(
                        Username, Name, NewUsername, Email,
                        Address, Contact,
                        NewPassword, ConfirmPassword
                );

                if (Objects.equals(Message, "Updated Successfully")) {
                    Username = NewUsername;
                    JOptionPane.showMessageDialog(
                            null, Message, "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            null, Message, "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void FetchDetails() {
        Map<String, String> data = viewModel.FetchUserExisting(Username);
        NameText.setText(data.get("name"));
        UsernameText.setText(data.get("username"));
        EmailText.setText(data.get("email"));
        AddressText.setText(data.get("address"));
        ContactText.setText(data.get("contact"));
    }
}