package database;

import model.Note;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class NoteDAO {
    public List<Note> getAllNotes(int userId) {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Note note = new Note();
                note.setId(rs.getInt("id"));
                note.setUserId(rs.getInt("user_id"));
                note.setContent(rs.getString("content"));
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return notes;
    }

    public void createNote(Note note) {
        String query = "INSERT INTO notes (user_id, content) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, note.getUserId());
            stmt.setString(2, note.getContent());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                note.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(Note note) {
        String query = "UPDATE notes SET content = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, note.getContent());
            stmt.setInt(2, note.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(int noteId) {
        String query = "DELETE FROM notes WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, noteId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
