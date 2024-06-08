package database;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public User getUserById(int userId) {
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }

    public void createUser(User user) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void createUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();){
             PreparedStatement stmt = conn.prepareStatement(query) ;
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        public List<User> getAllUsers() {
            List<User> users = new ArrayList<>();
            String query = "SELECT * FROM users";

            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return users;
        }
    

}
