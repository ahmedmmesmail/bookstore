package Frames;

import Sql.SqlCon;

import javax.swing.*;
import java.awt.*;


public class Login {

    public static void openLoginFrame() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(800, 600);
        loginFrame.setDefaultCloseOperation(loginFrame.getDefaultCloseOperation());
        loginFrame.setLayout(null);
//        loginFrame.getContentPane().setBackground(new Color(0x172310));
//        loginFrame.setVisible(true);
//        loginFrame.setLocationRelativeTo(null);
//        loginFrame.setResizable(false);
        Image icon = new ImageIcon(Login.class.getResource("/images/icon.png")).getImage();
        loginFrame.setIconImage(icon);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(new Color(0x95bf74));
        usernameLabel.setBounds(200, 160, 120, 35);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 230, 120, 35);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passwordLabel.setForeground(new Color(0x95bf74));

        JTextField usernameField = new JTextField();
        usernameField.setBounds(330, 160, 250, 35);
        usernameField.setFont(new Font("Arial", Font.BOLD, 20));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(330, 230, 250, 35);
        passwordField.setFont(new Font("Arial", Font.BOLD, 20));

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(205, 300, 385, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setForeground(new Color(0xf3f8ed));
        loginButton.setBackground(new Color(0x517c34));

        JButton customerButton = new JButton("Login as Customer");
        customerButton.setBounds(205, 370, 385, 40);
        customerButton.setFont(new Font("Arial", Font.BOLD, 20));
        customerButton.setForeground(new Color(0xf3f8ed));
        customerButton.setBackground(new Color(0x517c34));

        loginFrame.add(passwordLabel);
        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(customerButton);

        loginFrame.getContentPane().setBackground(new Color(0x172310));
        loginFrame.setVisible(true);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame, "There is at least one empty field! Please fill in all fields. ", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (user.equals(SqlCon.user) && pass.equals(SqlCon.pass)) {
                MainFrame.openMainFrame();
                loginFrame.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(loginFrame, "Incorrect username or password. Please check and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        customerButton.addActionListener(e -> MainFrame.openMainFrame());
    }
}