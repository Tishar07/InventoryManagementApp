package view;


import model.User;
import viewModel.RegistrationViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegisterView extends JFrame {

    public RegisterView() {
        RegistrationViewModel vm = new RegistrationViewModel();

        setTitle("Registration Form");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(30, 75, 176));
        leftPanel.setPreferredSize(new Dimension(500, 500));
        leftPanel.setLayout(new GridBagLayout());

        JLabel brandingLabel = new JLabel("<html><center><font color='white' size='6'>LOGO<br>NAME</font></center></html>");
        leftPanel.add(brandingLabel);


        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel title = new JLabel("REGISTER", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(30, 75, 176));
        rightPanel.add(title, gbc);

        gbc.gridy++;
        JLabel userLabel = new JLabel("Username");
        rightPanel.add(userLabel, gbc);

        gbc.gridy++;
        JTextField userField = new JTextField(40);
        userField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(userField, gbc);

        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email");
        rightPanel.add(emailLabel, gbc);

        gbc.gridy++;
        JTextField emailField = new JTextField(40);
        emailField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(emailField, gbc);

        gbc.gridy++;
        JLabel contactLabel = new JLabel("Contact");
        rightPanel.add(contactLabel, gbc);

        gbc.gridy++;
        JTextField contactField = new JTextField(40);
        contactField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(contactField, gbc);

        gbc.gridy++;
        JLabel addressLabel = new JLabel("Address");
        rightPanel.add(addressLabel, gbc);

        gbc.gridy++;
        JTextField addressField = new JTextField(40);
        addressField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(addressField, gbc);


        gbc.gridy++;
        JLabel passLabel = new JLabel("Password");
        rightPanel.add(passLabel, gbc);

        gbc.gridy++;
        JPasswordField passField = new JPasswordField(40);
        passField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(passField, gbc);

        gbc.gridy++;
        JLabel confirmPassLabel = new JLabel("Confirm Password");
        rightPanel.add(confirmPassLabel, gbc);

        gbc.gridy++;
        JPasswordField confirmPassField = new JPasswordField(40);
        confirmPassField.setPreferredSize(new Dimension(400, 40));
        rightPanel.add(confirmPassField, gbc);

        gbc.gridy++;
        JButton RegisterBtn = new JButton("Register");
        RegisterBtn.setBackground(new Color(0, 102, 102));
        RegisterBtn.setForeground(Color.WHITE);
        RegisterBtn.setPreferredSize(new Dimension(150, 40));
        rightPanel.add(RegisterBtn, gbc);

        gbc.gridy++;
        JLabel loginLink = new JLabel("Already have an account?");
        rightPanel.add(loginLink, gbc);

        gbc.gridy++;
        JButton loginBtn = new JButton("Login");
        loginBtn.setForeground(new Color(255, 51, 51));
        loginBtn.setPreferredSize(new Dimension(150, 35));
        rightPanel.add(loginBtn, gbc);

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
                String result = vm.registerUser(user);
                JOptionPane.showMessageDialog(RegisterView.this, result);

                if ("Registration successful".equals(result)) {
                    userField.setText("");
                    emailField.setText("");
                    contactField.setText("");
                    addressField.setText("");
                    passField.setText("");
                    confirmPassField.setText("");
                }
            }
        });

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterView();
    }
}


