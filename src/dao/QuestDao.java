package dao;

import db.DBConnection;
import model.Quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class QuestDao {

    private DBConnection db;

    public QuestDao() throws Exception {
        db = new DBConnection();
    }

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