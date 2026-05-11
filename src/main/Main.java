package main;

import dao.DataDao;
import dao.UserDao;

import model.User;
import model.UserQuest;

import service.QuestService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        UserDao userDAO = new UserDao();

        boolean running = true;

        while (running) {

            System.out.println("\n=== Side Quest ===");
            System.out.println("1 - Register");
            System.out.println("2 - Login");
            System.out.println("3 - Delete account");
            System.out.println("4 - Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 4) {
                System.out.println("Goodbye!");
                break;
            }

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (choice == 1) {

                boolean success =
                        userDAO.register(new User(username, password));

                System.out.println(success
                        ? "Registered!"
                        : "Registration failed.");

            } else if (choice == 2) {

                User loggedInUser =
                        userDAO.login(username, password);

                if (loggedInUser == null) {

                    System.out.println("Invalid credentials.");
                    continue;
                }

                System.out.println("Login successful!");

                QuestService questService =
                        new QuestService();

                DataDao dataDao =
                        new DataDao();

                questService.generateDailyQuests(
                        loggedInUser.getId()
                );

                boolean loggedIn = true;

                while (loggedIn) {

                    List<UserQuest> quests =
                            dataDao.getTodaysQuests(
                                    loggedInUser.getId()
                            );

                    System.out.println("\n=== Today's Quests ===");

                    for (int i = 0; i < quests.size(); i++) {

                        UserQuest q = quests.get(i);

                        System.out.println(
                                (i + 1) + ". "
                                        + q.getQuestText()
                                        + (q.isDone()
                                        ? " [DONE]"
                                        : " [ ]")
                        );
                    }

                    System.out.println("\n1 - Complete Quest");
                    System.out.println("2 - Refresh");
                    System.out.println("3 - Logout");

                    int questChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (questChoice == 1) {

                        System.out.print("Quest number: ");

                        int questNumber =
                                scanner.nextInt();

                        scanner.nextLine();

                        if (questNumber >= 1
                                && questNumber <= quests.size()) {

                            UserQuest selected =
                                    quests.get(questNumber - 1);

                            dataDao.completeQuest(
                                    loggedInUser.getId(),
                                    selected.getQuestId()
                            );

                            System.out.println(
                                    "Quest completed!"
                            );

                        } else {
                            System.out.println(
                                    "Invalid quest number."
                            );
                        }

                    } else if (questChoice == 2) {

                        System.out.println("Refreshing...");

                    } else if (questChoice == 3) {

                        loggedIn = false;
                        System.out.println("Logged out.");

                    } else {

                        System.out.println("Invalid option.");
                    }
                }

            } else if (choice == 3) {

                boolean success =
                        userDAO.deleteAccount(username, password);

                System.out.println(success
                        ? "Account deleted."
                        : "Delete failed.");

            } else {

                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}