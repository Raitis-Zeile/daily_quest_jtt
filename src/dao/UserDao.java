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

	public boolean login(String username, String password) {

		if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			return false;
		}

		String sql = "SELECT password FROM users WHERE username = ?";
		try {
			Connection conn = db.getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String storedHash = rs.getString("password");
				return PasswordHash.verifyPassword(password, storedHash);
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteAccount(String username, String password) {
		if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			return false;
		}

		String selectSql = "SELECT password FROM users WHERE username = ?";
		try {
			Connection conn = db.getConn();
			PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			selectStmt.setString(1, username);
			ResultSet rs = selectStmt.executeQuery();

			if (!rs.next()) {
				return false;
			}

			String storedHash = rs.getString("password");
			if (!PasswordHash.verifyPassword(password, storedHash)) {
				return false;
			}

			String deleteSql = "DELETE FROM users WHERE username = ?";
			PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
			deleteStmt.setString(1, username);
			int deleted = deleteStmt.executeUpdate();
			deleteStmt.close();
			selectStmt.close();
			return deleted > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}