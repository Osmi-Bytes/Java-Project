package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    public Main() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Main Menu");
        setSize(300, 300); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(173, 216, 230));
        
        // Stylish buttons
        JButton addUserButton = new JButton("Add User");
        customizeButton(addUserButton);
        addUserButton.addActionListener(e -> openUserUI());
        
        JButton openNotepadButton = new JButton("Open Notepad");
        customizeButton(openNotepadButton);
        openNotepadButton.addActionListener(e -> openNotepadUI());
        
        // Add buttons to the layout using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(addUserButton, gbc);
        
        gbc.gridy = 1;
        add(openNotepadButton, gbc);
    }
    
    // Method to customize button style
    private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add border
        button.setPreferredSize(new Dimension(200, 50)); // Set button size
    }
    
    private void openUserUI() {
        SwingUtilities.invokeLater(() -> {
            UserUI userUI = new UserUI();
            userUI.setVisible(true);
            dispose(); 
        });
    }
    
    private void openNotepadUI() {
        SwingUtilities.invokeLater(() -> {
            NotepadUI notepadUI = new NotepadUI(-1); // Note: Passing -1 as userId since it's not available yet
            notepadUI.setVisible(true);
            dispose(); 
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main mainClass = new Main();
            mainClass.setVisible(true);
        });
    }
}
