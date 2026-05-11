package model;

/**
 * Represents a quest template in the Daily Quest system.
 * Contains the quest ID and the quest description text.
 */
public class Quest {

    private int id;
    private String quest;

    /**
     * Constructor for creating a quest.
     *
     * @param id the unique identifier for the quest
     * @param quest the quest description text
     */
    public Quest(int id, String quest) {
        this.id = id;
        this.quest = quest;
    }

    /**
     * Gets the quest's unique identifier.
     *
     * @return the quest ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the quest description text.
     *
     * @return the quest text
     */
    public String getQuest() {
        return quest;
    }
}