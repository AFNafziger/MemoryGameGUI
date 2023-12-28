package edu.wm.cs.cs301.guimemorygame.view;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LeaderboardDialog extends JDialog {
    public LeaderboardDialog(JFrame parentFrame) {
        super(parentFrame, "Leaderboard", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        String leaderboardData = readLeaderboardData();

        JTextArea leaderboardTextArea = new JTextArea(20, 40);
        leaderboardTextArea.setText(leaderboardData);
        leaderboardTextArea.setEditable(false);

        Font font = new Font("Arial", Font.BOLD, 12);//make the words bigger
        leaderboardTextArea.setFont(font);

        JScrollPane scrollPane = new JScrollPane(leaderboardTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parentFrame);
        setVisible(true);
    }

    private String readLeaderboardData() {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/leaderboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }
}