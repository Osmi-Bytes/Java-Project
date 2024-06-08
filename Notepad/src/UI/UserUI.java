package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import database.UserDAO;

public class UserUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UserUI() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Create User");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // background color
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.setBackground(Color.WHITE);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        
        JButton createUserButton = new JButton("Add User");
        createUserButton.addActionListener(e -> createUser());
        
        add(inputPanel, BorderLayout.CENTER);
        add(createUserButton, BorderLayout.SOUTH);
    }
    
    private void createUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (!username.isEmpty() && !password.isEmpty()) {
            UserDAO userDAO = new UserDAO();
            userDAO.createUser(username, password);
            JOptionPane.showMessageDialog(this, "User created successfully!");
            dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserUI userUI = new UserUI();
            userUI.setVisible(true);
        });
    }
}
