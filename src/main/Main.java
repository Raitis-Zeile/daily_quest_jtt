package main;

import java.sql.SQLException;
import java.util.Scanner;

import dao.UserDao;
import model.User;

public class Main {
public static void main(String[] args) throws ClassNotFoundException, SQLException {
	Scanner scanner = new Scanner(System.in);
	UserDao userDAO = new UserDao();
	
	boolean running = true;
	while(running) {
		System.out.println("\n=== Side Quest ===");
		System.out.println("1 - Register");
		System.out.println("2 - Login");
		System.out.println("3 - Delete account");
		System.out.println("4 - Exit");
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 4) {
			System.out.println("Goodbye!");
			running = false;
			continue;
		}
		
		System.out.println("Username: ");
		String username = scanner.nextLine();
		
		System.out.println("Password: "); 
		String password = scanner.nextLine();
		
		if(choice == 1) {
			boolean success = userDAO.register(new User(username, password));
			System.out.println(success ? "Registered!" : "Failed.");
		} else if (choice == 2) {
			boolean success = userDAO.login(username, password);
			System.out.println(success ? "Login successful!" : "Invalid credentials.");
		} else if (choice == 3) {
			boolean success = userDAO.deleteAccount(username, password);
			System.out.println(success ? "Account deleted." : "Delete failed. Check credentials.");
		} else {
			System.out.println("Invalid choice. Type 1, 2, 3 or 4");
		}
	}
	scanner.close();
}
}
