package view;
import javax.swing.*;
import java.awt.*;

public class LoginView extends javax.swing.JFrame {

    public LoginView() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();
    }

    private void initComponents() {

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
        JLabel LabelRegister = new JLabel("Don't have an account?");
        JButton RegisterButton = new JButton("Sign up");

        MainPanel.setLayout(new BorderLayout());

        LeftPanel.setBackground(new Color(30,75,176));
        LeftPanel.setPreferredSize(new Dimension(250,400));
        LeftPanel.setLayout(new GridBagLayout());
        LabelLogo.setFont(new Font("Arial", Font.BOLD, 24));
        LabelLogo.setForeground(Color.WHITE);
        LeftPanel.add(LabelLogo);

        RightPanel.setLayout(null);
        RightPanel.setBackground(Color.WHITE);

        LabelTitle.setFont(new Font("Arial", Font.BOLD, 24));
        LabelTitle.setForeground(new Color(30,75,176));
        LabelTitle.setBounds(320,40,200,30);
        RightPanel.add(LabelTitle);

        LabelUsername.setFont(new Font("Arial", Font.BOLD, 14));
        LabelUsername.setBounds(220,100,100,25);
        RightPanel.add(LabelUsername);

        UsernameText.setBounds(300,100,250,30);
        UsernameText.setFont(new Font("Arial", Font.PLAIN, 14));
        UsernameText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        RightPanel.add(UsernameText);

        LabelPass.setFont(new Font("Arial", Font.BOLD, 14));
        LabelPass.setBounds(220, 150, 100, 25);
        RightPanel.add(LabelPass);

        PassText.setBounds(300, 150, 250, 30);
        PassText.setFont(new Font("Arial", Font.PLAIN, 14));
        PassText.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        RightPanel.add(PassText);

        LoginButton.setBounds(300, 210, 100, 35);
        LoginButton.setBackground(new Color(30, 90, 200));
        LoginButton.setForeground(Color.WHITE);
        LoginButton.setFont(new Font("Arial", Font.BOLD, 14));
        LoginButton.setFocusPainted(false);
        LoginButton.setBorderPainted(false);
        LoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        RightPanel.add(LoginButton);

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

        add(MainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}

