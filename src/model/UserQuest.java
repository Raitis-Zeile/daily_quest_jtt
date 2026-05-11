package model;

/**
 * Represents a user's assigned quest with completion status.
 * Links a user to a specific quest and tracks whether it has been completed.
 */
public class UserQuest {

    private int questId;
    private String questText;
    private boolean done;

    /**
     * Constructor for creating a user quest.
     *
     * @param questId the ID of the quest
     * @param questText the text description of the quest
     * @param done whether the quest has been completed
     */
    public UserQuest(int questId, String questText, boolean done) {
        this.questId = questId;
        this.questText = questText;
        this.done = done;
    }

    /**
     * Gets the quest ID.
     *
     * @return the quest identifier
     */
    public int getQuestId() {
        return questId;
    }

    /**
     * Gets the quest text description.
     *
     * @return the quest text
     */
    public String getQuestText() {
        return questText;
    }

    /**
     * Checks if the quest is completed.
     *
     * @return true if completed, false otherwise
     */
    public boolean isDone() {
        return done;
    }
}