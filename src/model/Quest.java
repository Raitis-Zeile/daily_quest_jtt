package model;

public class Quest {

    private int id;
    private String quest;

    public Quest(int id, String quest) {
        this.id = id;
        this.quest = quest;
    }

    public int getId() {
        return id;
    }

    public String getQuest() {
        return quest;
    }
}