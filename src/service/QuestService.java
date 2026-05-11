package service;

import dao.DataDao;
import dao.QuestDao;

import model.Quest;

import java.util.List;

public class QuestService {

    private QuestDao questDao;
    private DataDao dataDao;

    public QuestService() throws Exception {

        questDao = new QuestDao();
        dataDao = new DataDao();
    }

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