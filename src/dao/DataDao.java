package dao;

import db.DBConnection;
import model.UserQuest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for managing user quest assignments and completions.
 * Handles database operations related to the data table which links users to quests.
 */
public class DataDao {

    private DBConnection db;

    /**
     * Constructor that initializes the database connection.
     *
     * @throws Exception if database connection fails
     */
    public DataDao() throws Exception {
        db = new DBConnection();
    }

    /**
     * Checks if the user has any quests assigned for today.
     *
     * @param userId the user's ID
     * @return true if user has quests today, false otherwise
     */
    public boolean hasQuestsToday(int userId) {

        String sql = "SELECT * FROM data WHERE user_id = ? AND quest_date = CURDATE()";

        try {

            Connection conn = db.getConn();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Assigns a quest to a user for today.
     *
     * @param userId the user's ID
     * @param questId the quest's ID
     */
    public void assignQuest(int userId, int questId) {

        String sql = "INSERT INTO data(user_id, quest_id, time, done, quest_date) VALUES (?, ?, CURTIME(), false, CURDATE())";

        try {

            Connection conn = db.getConn();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);
            stmt.setInt(2, questId);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all quests assigned to a user for today.
     *
     * @param userId the user's ID
     * @return list of UserQuest objects for today
     */
    public List<UserQuest> getTodaysQuests(int userId) {

        List<UserQuest> quests = new ArrayList<>();

        String sql = "SELECT q.id, q.quest, d.done FROM data d JOIN quest q ON d.quest_id = q.id WHERE d.user_id = ? AND d.quest_date = CURDATE()";

        try {

            Connection conn = db.getConn();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                quests.add(
                        new UserQuest(
                                rs.getInt("id"),
                                rs.getString("quest"),
                                rs.getBoolean("done")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return quests;
    }

    /**
     * Marks a quest as completed for the user today.
     *
     * @param userId the user's ID
     * @param questId the quest's ID
     */
    public void completeQuest(int userId, int questId) {

        String sql = "UPDATE data SET done = true WHERE user_id = ? AND quest_id = ? AND quest_date = CURDATE()";

        try {

            Connection conn = db.getConn();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);
            stmt.setInt(2, questId);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}