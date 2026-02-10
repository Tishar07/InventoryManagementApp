package view;


import model.User;
import utilities.Navigator;
import viewModel.RegistrationViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegisterView extends JPanel {
    private RegistrationViewModel viewModel;
    JPanel leftPanel = new JPanel();
    JLabel brandingLabel = new JLabel("<html><center><font color='white' size='6'>LOGO<br>NAME</font></center></html>");
    JPanel rightPanel = new JPanel();
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel title = new JLabel("REGISTER", SwingConstants.CENTER);
    JLabel userLabel = new JLabel("Username");
    JLabel emailLabel = new JLabel("Email");
    JLabel contactLabel = new JLabel("Contact");
    JLabel addressLabel = new JLabel("Address");
    JLabel passLabel = new JLabel("Password");
    JLabel confirmPassLabel = new JLabel("Confirm Password");
    JLabel loginLink = new JLabel("Already have an account?");
    JButton RegisterBtn = new JButton("Register");
    JButton loginBtn = new JButton("Login");
    JTextField userField = new JTextField(40);
    JTextField emailField = new JTextField(40);
    JTextField contactField = new JTextField(40);
    JPasswordField passField = new JPasswordField(40);
    JPasswordField confirmPassField = new JPasswordField(40);
    JTextField addressField = new JTextField(40);

    public RegisterView(RegistrationViewModel viewModel){
        this.viewModel = viewModel;
        initComponents();
    }

    public void initComponents() {
        //SET JFrame

        setLayout(new BorderLayout());


        //Styling LeftPanel
        leftPanel.setBackground(new Color(30, 75, 176));
        leftPanel.setPreferredSize(new Dimension(500, 500));
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.add(brandingLabel);

        //Styling RightPanel
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());

        gbc.insets = new Insets(10, 10, 10, 10); // spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        //Styling for page Fonts and font color
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(30, 75, 176));
        rightPanel.add(title, gbc);
        gbc.gridy++;
        rightPanel.add(userLabel, gbc);

        gbc.gridy++;

        //Styling UserField
        userField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(userField, gbc);
        gbc.gridy++;
        rightPanel.add(emailLabel, gbc);

        gbc.gridy++;

        //Styling emailField
        emailField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(emailField, gbc);
        gbc.gridy++;
        rightPanel.add(contactLabel, gbc);

        gbc.gridy++;

        //Styling contactField
        contactField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(contactField, gbc);
        gbc.gridy++;
        rightPanel.add(addressLabel, gbc);

        gbc.gridy++;

        //Styling addressField
        addressField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(addressField, gbc);
        gbc.gridy++;
        rightPanel.add(passLabel, gbc);

        gbc.gridy++;

        //Styling passField
        passField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(passField, gbc);
        gbc.gridy++;
        rightPanel.add(confirmPassLabel, gbc);

        gbc.gridy++;

        //Styling confirmPassField
        confirmPassField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(confirmPassField, gbc);
        gbc.gridy++;

        //Styling Register Button
        RegisterBtn.setBackground(new Color(0, 102, 102));
        RegisterBtn.setForeground(Color.WHITE);
        RegisterBtn.setPreferredSize(new Dimension(150, 40));
        rightPanel.add(RegisterBtn, gbc);
        gbc.gridy++;
        rightPanel.add(loginLink, gbc);

        gbc.gridy++;

        //Styling Login Button
        loginBtn.setForeground(new Color(255, 51, 51));
        loginBtn.setPreferredSize(new Dimension(150, 35));
        rightPanel.add(loginBtn, gbc);


        Action();
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void Action(){
        RegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passField.getPassword());
                String contact = contactField.getText().trim();
                String address = addressField.getText().trim();

                String confirmPassword = new String(confirmPassField.getPassword());
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(RegisterView.this, "Passwords do not match!");
                    return;
                }

                User user = new User(0, username, "User", password, username, email, "Active", address, contact);
                String result = viewModel.registerUser(user);
                JOptionPane.showMessageDialog(RegisterView.this, result);

                if ("Registration successful".equals(result)) {
                    userField.setText("");
                    emailField.setText("");
                    contactField.setText("");
                    addressField.setText("");
                    passField.setText("");
                    confirmPassField.setText("");
                    Navigator.showLogin();
                }
            }
        });
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showLogin();
            }
        });

    }

}


