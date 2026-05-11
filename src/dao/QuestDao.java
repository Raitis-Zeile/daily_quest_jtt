package dao;

import db.DBConnection;
import model.Quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for managing quest templates.
 * Handles database operations related to the quest table.
 */
public class QuestDao {

    private DBConnection db;

    /**
     * Constructor that initializes the database connection.
     *
     * @throws Exception if database connection fails
     */
    public QuestDao() throws Exception {
        db = new DBConnection();
    }

    /**
     * Retrieves a specified number of random quests from the database.
     * Used for generating daily quests for users.
     *
     * @param amount the number of random quests to retrieve
     * @return list of random Quest objects
     */
    public List<Quest> getRandomQuests(int amount) {

        List<Quest> quests = new ArrayList<>();

        String sql = "SELECT * FROM quest ORDER BY RAND() LIMIT ?";

        try {

            Connection conn = db.getConn();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, amount);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                quests.add(
                        new Quest(
                                rs.getInt("id"),
                                rs.getString("quest")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return quests;
    }
}