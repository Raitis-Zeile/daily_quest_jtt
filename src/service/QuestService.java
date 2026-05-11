package service;

import dao.DataDao;
import dao.QuestDao;

import model.Quest;

import java.util.List;

/**
 * Service class for managing quest generation and assignment.
 * Handles the business logic for creating daily quests for users.
 */
public class QuestService {

    private QuestDao questDao;
    private DataDao dataDao;

    /**
     * Constructor that initializes the DAO dependencies.
     *
     * @throws Exception if DAO initialization fails
     */
    public QuestService() throws Exception {

        questDao = new QuestDao();
        dataDao = new DataDao();
    }

    /**
     * Generates daily quests for a user if they don't already have quests for today.
     * Selects 3 random quests and assigns them to the user.
     *
     * @param userId the user's ID
     */
    public void generateDailyQuests(int userId) {

        if (dataDao.hasQuestsToday(userId)) {
            return;
        }

        List<Quest> randomQuests =
                questDao.getRandomQuests(3);

        for (Quest q : randomQuests) {
            dataDao.assignQuest(userId, q.getId());
        }
    }
}