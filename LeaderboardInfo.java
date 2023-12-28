package edu.wm.cs.cs301.guimemorygame.model;

public class LeaderboardInfo {
    private String name;
    private String turns;
    private String difficulty;

    public LeaderboardInfo(String name, String turns, String difficulty) {
        this.name = name;
        this.turns = turns;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public String getTurns() {
        return turns;
    }
    public String getDifficulty() {
        return difficulty;
    }
}

