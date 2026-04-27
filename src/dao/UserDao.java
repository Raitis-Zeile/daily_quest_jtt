package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;
import model.User;

public class UserDao {
    private DBConnection db;
   
    public UserDao() throws ClassNotFoundException, SQLException {
        this.db = new DBConnection();
    }

    public boolean register(User user) throws ClassNotFoundException, SQLException {

        if (user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return false; 
        }

        String checkSql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = db.getConn();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
           
            checkStmt.setString(1, user.getUsername());
            ResultSet rs = checkStmt.executeQuery();
           

            if (rs.next()) {
                return false;
            }
        }
       

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = db.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
           
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String username, String password) {
  
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false; 
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = db.getConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setString(1, username);
            stmt.setString(2, password);
           
            ResultSet rs = stmt.executeQuery();
           
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
