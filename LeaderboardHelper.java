package edu.wm.cs.cs301.guimemorygame.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardHelper {
	private List<LeaderboardInfo> leaderboard;
	
	public LeaderboardHelper() {
		leaderboard = new ArrayList<>();
		String leaderboardPath = new File("resources/leaderboard.txt").getAbsolutePath();
        readLeaderboard(leaderboardPath);
	}
    private void readLeaderboard(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String turns = parts[1];
                    String difficulty = parts[2];
                    leaderboard.add(new LeaderboardInfo(name, turns, difficulty));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeLeaderboard(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (LeaderboardInfo entry : leaderboard) {
                writer.write(entry.getName() + "," + entry.getTurns() + "," + entry.getDifficulty());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printLeaderboard() {
        System.out.println("Leaderboard:\n");
        for (LeaderboardInfo entry : leaderboard) {
            System.out.println("Name: " + entry.getName() + ", Turns: " + entry.getTurns() + ", Difficulty: " + entry.getDifficulty());
        }
        System.out.println();
    }

    public void addLeaderboard(String name, String turns, String difficulty) {
        leaderboard.add(new LeaderboardInfo(name, turns, difficulty));
        writeLeaderboard("resources/leaderboard.txt"); // Save the updated leaderboard to the file WORKING
    }
    
    public List<LeaderboardInfo> getLeaderboard() {
        return leaderboard;
    }
}
