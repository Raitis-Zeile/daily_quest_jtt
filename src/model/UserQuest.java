package model;

public class UserQuest {

    private int questId;
    private String questText;
    private boolean done;

    public UserQuest(int questId, String questText, boolean done) {
        this.questId = questId;
        this.questText = questText;
        this.done = done;
    }

    public int getQuestId() {
        return questId;
    }

    public String getQuestText() {
        return questText;
    }

    public boolean isDone() {
        return done;
    }
}