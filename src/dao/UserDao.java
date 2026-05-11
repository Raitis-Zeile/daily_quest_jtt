package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;
import model.User;
import util.PasswordHash;

public class UserDao {
	private DBConnection db;

	public UserDao() throws ClassNotFoundException, SQLException {
		this.db = new DBConnection();
	}

	public boolean register(User user) throws ClassNotFoundException, SQLException {

		if (user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getPassword() == null
				|| user.getPassword().trim().isEmpty()) {
			return false;
		}

		String checkSql = "SELECT * FROM users WHERE username = ?";
		try {
			Connection conn = db.getConn(); 
			PreparedStatement checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setString(1, user.getUsername());
			ResultSet rs = checkStmt.executeQuery();

			if (rs.next()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
		try {
			Connection conn = db.getConn(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			String hashedPassword = PasswordHash.hashPassword(user.getPassword());
			stmt.setString(2, hashedPassword);

			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


public User login(String username, String password) {

    if (username == null || username.trim().isEmpty()
            || password == null || password.trim().isEmpty()) {
        return null;
    }

    String sql = "SELECT * FROM users WHERE username = ?";

    try {
        Connection conn = db.getConn();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            String storedHash = rs.getString("password");

            if (PasswordHash.verifyPassword(password, storedHash)) {

                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        storedHash
                );
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}}
