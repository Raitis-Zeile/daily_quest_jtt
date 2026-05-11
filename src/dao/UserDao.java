package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;
import model.User;
import util.PasswordHash;

/**
 * Data Access Object for managing user accounts.
 * Handles user registration, authentication, and account deletion.
 */
public class UserDao {
	private DBConnection db;

	/**
	 * Constructor that initializes the database connection.
	 *
	 * @throws ClassNotFoundException if JDBC driver not found
	 * @throws SQLException if database connection fails
	 */
	public UserDao() throws ClassNotFoundException, SQLException {
		this.db = new DBConnection();
	}

	/**
	 * Registers a new user account.
	 * Validates input and checks for existing username before creating account.
	 *
	 * @param user the user to register
	 * @return true if registration successful, false otherwise
	 * @throws ClassNotFoundException if JDBC driver not found
	 * @throws SQLException if database operation fails
	 */
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

	/**
	 * Authenticates a user with username and password.
	 *
	 * @param username the username
	 * @param password the password
	 * @return User object if login successful, null otherwise
	 */
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
	}

	/**
	 * Deletes a user account after password verification.
	 *
	 * @param username the username
	 * @param password the password for verification
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteAccount(String username, String password) {

		if (username == null || username.trim().isEmpty()
				|| password == null || password.trim().isEmpty()) {
			return false;
		}

		// First verify the password
		User user = login(username, password);
		if (user == null) {
			return false;
		}

		String sql = "DELETE FROM users WHERE id = ?";

		try {
			Connection conn = db.getConn();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user.getId());

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
