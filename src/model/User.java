package model;

/**
 * Represents a user in the Daily Quest system.
 * Contains user identification, username, and password information.
 */
public class User {

    private int id;
    private String username;
    private String password;

    /**
     * Constructor for creating a user with all fields.
     *
     * @param id the unique identifier for the user
     * @param username the user's username
     * @param password the user's password
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor for creating a new user without ID (for registration).
     *
     * @param username the user's username
     * @param password the user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the user's unique identifier.
     *
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the user's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}