package main;

import java.sql.SQLException;
import java.util.Scanner;

import dao.UserDao;
import model.User;

public class Main {
public static void main(String[] args) throws ClassNotFoundException, SQLException {
	Scanner scanner = new Scanner(System.in);
	UserDao userDAO = new UserDao();
	
	System.out.println("1 - Register");
	System.out.println("2 - Login");
	int choice = scanner.nextInt();
	scanner.nextLine();
	
	System.out.println("Username: ");
	String username = scanner.nextLine();
	
	System.out.println("Password: ");
	String password = scanner.nextLine();
	
	if(choice == 1) {
		boolean success = userDAO.register(new User(username, password));
		System.out.println(success ? "Registered!": "Failed.");
	}else {
		boolean success = userDAO.login(username, password);
		System.out.println(success ? "Login successful!" : "Invalid credentials.");
		
	}
}
}
