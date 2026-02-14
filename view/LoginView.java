package view;

import utilities.Navigator;
import viewModel.LoginViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginView extends JPanel  {
    private LoginViewModel viewModel;
    JPanel MainPanel = new JPanel();
    JPanel LeftPanel = new JPanel();
    JLabel LabelLogo = new JLabel("LOGO");
    JPanel RightPanel = new JPanel();
    JLabel LabelTitle = new JLabel("LOGIN");
    JLabel LabelUsername = new JLabel("Username: ");
    JLabel LabelPass = new JLabel("Password: ");
    JTextField UsernameText = new JTextField();
    JPasswordField PassText = new JPasswordField();
    JButton LoginButton = new JButton("Login");
    JButton RegisterButton = new JButton("Sign up");
    JLabel LabelRegister = new JLabel("Don't have an account?");

    //private UserDAO userDAO = new UserDAO();

    public LoginView(LoginViewModel viewModel){
        this.viewModel = viewModel;
        initComponents();
    }

    private void initComponents() {


        //SET Panel Layout Manager
        MainPanel.setLayout(new BorderLayout());

        //Styling Left panel
        LeftPanel.setBackground(new Color(30,75,176));
        LeftPanel.setPreferredSize(new Dimension(250,400));
        LeftPanel.setLayout(new GridBagLayout());
        LabelLogo.setFont(new Font("Arial", Font.BOLD, 24));
        LabelLogo.setForeground(Color.WHITE);
        LeftPanel.add(LabelLogo);

        //Styling Right Panel
        RightPanel.setLayout(null);
        RightPanel.setBackground(Color.WHITE);

        //Styling for Login main Text
        LabelTitle.setFont(new Font("Arial", Font.BOLD, 24));
        LabelTitle.setForeground(new Color(30,75,176));
        LabelTitle.setBounds(320,40,200,30);
        RightPanel.add(LabelTitle);

        //Styling for Username
        LabelUsername.setFont(new Font("Arial", Font.BOLD, 14));
        LabelUsername.setBounds(220,100,100,25);
        RightPanel.add(LabelUsername);

        UsernameText.setBounds(300,100,250,30);
        UsernameText.setFont(new Font("Arial", Font.PLAIN, 14));
        UsernameText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        RightPanel.add(UsernameText);

        //Styling for Password
        LabelPass.setFont(new Font("Arial", Font.BOLD, 14));
        LabelPass.setBounds(220, 150, 100, 25);
        RightPanel.add(LabelPass);

        PassText.setBounds(300, 150, 250, 30);
        //PassText.setFont(new Font("Arial", Font.PLAIN, 14));
        PassText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        RightPanel.add(PassText);

        //Styling for Login Button
        LoginButton.setBounds(300, 210, 100, 35);
        LoginButton.setBackground(new Color(30, 90, 200));
        LoginButton.setForeground(Color.WHITE);
        LoginButton.setFont(new Font("Arial", Font.BOLD, 14));
        LoginButton.setFocusPainted(false);
        LoginButton.setBorderPainted(false);
        LoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        RightPanel.add(LoginButton);

        //Styling for Register Button
        LabelRegister.setFont(new Font("Arial", Font.PLAIN, 13));
        LabelRegister.setBounds(300, 270, 160, 25);
        RightPanel.add(LabelRegister);

        RegisterButton.setBounds(460, 270, 90, 25);
        RegisterButton.setFont(new Font("Arial", Font.BOLD, 13));
        RegisterButton.setBackground(Color.WHITE);
        RegisterButton.setForeground(new Color(30, 90, 200));
        RegisterButton.setBorder(BorderFactory.createLineBorder(new Color(30, 90, 200)));
        RegisterButton.setFocusPainted(false);
        RegisterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        RightPanel.add(RegisterButton);

        MainPanel.add(LeftPanel, BorderLayout.WEST);
        MainPanel.add(RightPanel, BorderLayout.CENTER);

        Actions();
        setLayout(new BorderLayout());
        add(MainPanel);
    }

    public void Actions(){
        //EVENT LISTENER For Login
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(PassText.getPassword());
                boolean Success = viewModel.login(
                        UsernameText.getText(),
                        password
                );
                if (Success==true){
                    Navigator.showDashboard();
                }else {
                    JOptionPane.showMessageDialog(LoginView.this,"Login Invalid");
                }
            }
        });
        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navigator.showRegister();
            }
        });
    }

}

