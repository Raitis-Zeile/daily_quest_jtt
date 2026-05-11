package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manages database connections to the MySQL database.
 * Provides a connection instance for database operations.
 */
public class DBConnection {
	protected Connection conn;

	/**
	 * Constructor that establishes a connection to the MySQL database.
	 * Uses hardcoded connection parameters for the local database.
	 *
	 * @throws SQLException if connection fails
	 * @throws ClassNotFoundException if JDBC driver not found
	 */
	public DBConnection() throws SQLException, ClassNotFoundException {
		Properties properties = new Properties();
		properties.put("charSet", "utf-8");
		properties.put("user", "admin");
		properties.put("password", "jtt");
		String url = "jdbc:mysql://localhost:3306/jtt_db";

		this.conn = DriverManager.getConnection(url, properties);
	}

	/**
	 * Gets the database connection instance.
	 *
	 * @return the Connection object
	 */
	public Connection getConn() {
		return this.conn;
	}
}
