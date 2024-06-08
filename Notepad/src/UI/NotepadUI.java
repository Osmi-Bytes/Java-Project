package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import database.NoteDAO;
import database.UserDAO;
import model.Note;
import model.User;

public class NotepadUI extends JFrame {
    private JPanel notesPanel;
    private JTextArea noteContent;
    private JButton addButton, viewButton, deleteButton;
    private NoteDAO noteDAO;
    private JList<Note> notesList;
    private DefaultListModel<Note> listModel;
    private int userId; 

    public NotepadUI(int userId) { 
        this.userId = userId;
        noteDAO = new NoteDAO();
        initializeUI();
        loadNotes();
    }
    
    private void initializeUI() {
        setTitle("Notepad");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); //background color
        
        notesPanel = new JPanel(new BorderLayout());
        notesPanel.setBackground(Color.LIGHT_GRAY); // panel background color

        listModel = new DefaultListModel<>();
        notesList = new JList<>(listModel);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        notesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && notesList.getSelectedIndex() != -1) {
                    Note selectedNote = notesList.getSelectedValue();
                    noteContent.setText(selectedNote.getContent());
               
                    deleteButton.setEnabled(true);
                }
            }
        });
        
        notesList.setBackground(new Color(173, 216, 230)); //Sky Color
        notesList.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        
        noteContent = new JTextArea();
        noteContent.setBackground(Color.WHITE); 
        noteContent.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border

        noteContent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (notesList.getSelectedIndex() != -1) {
                    Note selectedNote = notesList.getSelectedValue();
                    selectedNote.setContent(noteContent.getText());
                    noteDAO.updateNote(selectedNote);
                }
            }
        });
        
        addButton = new JButton("+");
        addButton.setBackground(Color.GREEN); // Set button background color
        addButton.setForeground(Color.WHITE); // Set button text color
        addButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set button font
        addButton.setPreferredSize(new Dimension(50, 50)); // Set button size
        addButton.addActionListener(e -> addNewNote());

        viewButton = new JButton("Users");
        viewButton.setBackground(Color.BLUE); // Set button background color
        viewButton.setForeground(Color.WHITE); // Set button text color
        viewButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set button font
        viewButton.setPreferredSize(new Dimension(100, 50)); // Set button size
        viewButton.addActionListener(e -> viewNote());

        deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.RED); // Set button background color
        deleteButton.setForeground(Color.WHITE); // Set button text color
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set button font
        deleteButton.setPreferredSize(new Dimension(100, 50)); // Set button size
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> deleteNote());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY); // Set panel background color
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        notesPanel.add(new JScrollPane(notesList), BorderLayout.CENTER);
        notesPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(notesPanel, BorderLayout.WEST);
        add(new JScrollPane(noteContent), BorderLayout.CENTER);
    }

    private void loadNotes() {
        listModel.clear();
        List<Note> notes = noteDAO.getAllNotes(userId); 
        for (Note note : notes) {
            listModel.addElement(note);
        }
    }
    
    private void addNewNote() {
        String content = noteContent.getText().trim();
        if (content.isEmpty()) {
            content = JOptionPane.showInputDialog("Enter note content:");
        }

        if (content != null && !content.isEmpty()) {
            Note newNote = new Note();
            newNote.setUserId(userId); 
            newNote.setContent(content); 
            noteDAO.createNote(newNote);
            listModel.addElement(newNote);
        }
    }

    
    public static void viewNote() {
        // Fetch all users from the database using UserDAO
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        
        // Create an array of user names
        String[] userNames = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userNames[i] = users.get(i).getUsername();
        }
        
        // Show dialog box to select a user
        String selectedUserName = (String) JOptionPane.showInputDialog(null, "Select a user:", "Select User",
                JOptionPane.QUESTION_MESSAGE, null, userNames, userNames[0]);

        // Find the user ID based on the selected user name
        int userId = -1;
        for (User user : users) {
            if (user.getUsername().equals(selectedUserName)) {
                userId = user.getId();
                break;
            }
        }

        if (userId != -1) {
        	final int finalUserId = userId;
            SwingUtilities.invokeLater(() -> new NotepadUI(finalUserId).setVisible(true));
        }
    }



    private void deleteNote() {
        Note selectedNote = notesList.getSelectedValue();
        noteDAO.deleteNote(selectedNote.getId());
        listModel.removeElement(selectedNote);
        noteContent.setText("");
        deleteButton.setEnabled(false);
    }
    
    

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> viewNote());
    }
}
