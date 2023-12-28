package edu.wm.cs.cs301.guimemorygame.view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.wm.cs.cs301.guimemorygame.model.MemoryGameForGUI;
import edu.wm.cs.cs301.guimemorygame.model.LeaderboardHelper;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class MemoryGrid extends JPanel {
    private final MemoryGameForGUI model;
    private final JButton[][] tileButtons;
    private int pairsRemaining;
    private JLabel turnsLabel; // Label for turns, thats why its called turnsLabel
    private LeaderboardHelper leaderboardHelper;
    private String username;//will use to get username from InstructionsDialog
    
    public MemoryGrid(MemoryFrame view, MemoryGameForGUI model) {
        this.model = model;
        this.leaderboardHelper = new LeaderboardHelper();
        int rows = model.getRows();
        int cols = model.getCols();
        pairsRemaining = (rows * cols) / 2;
        
        setLayout(new BorderLayout());
        turnsLabel = new JLabel("TURNS: 1");
        turnsLabel.setForeground(guessColor);
        turnsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        turnsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // this adds padding https://docs.oracle.com/javase/8/docs/api/javax/swing/BorderFactory.html
        add(turnsLabel, BorderLayout.NORTH);
        
        JPanel buttonsPanel = new JPanel(new GridLayout(rows, cols));
        tileButtons = new JButton[rows][cols];

        JPanel usernamePanel = createUsernamePanel(view);
        add(usernamePanel, BorderLayout.SOUTH);
        
        initializeTileButtons(buttonsPanel);
        
        add(buttonsPanel, BorderLayout.CENTER);
        
        setPreferredSize(new Dimension(300, 400));
        setVisible(true);
    }

    Color customColor = new Color(66, 0, 57);
    Color CorrectColor = new Color(220, 204, 255);
    Color guessColor = new Color(224, 123, 224);
    private void initializeTileButtons(JPanel buttonsPanel) {
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                final int row = i;
                final int col = j;
                
                JButton tileButton = new JButton();
                tileButton.setFont(new Font("Arial", Font.BOLD, 20));//made the letters bigger
                
                tileButton.setBackground(customColor); 
                //tileButton.setForeground(customColor); //not working
                tileButton.setBorder(BorderFactory.createLineBorder(Color.white));
                tileButton.addActionListener(e -> handleTileClick(row, col));
                tileButtons[i][j] = tileButton;
                buttonsPanel.add(tileButton);
            }
        }
    }
    
    private char[] symbolsArray = new char[2];
    private int[] positionsArray = new int[4]; 
    private int flippedCount = 0;
    private int turns = 1;//start at 1 turn bc it works better this way

    private void handleTileClick(int row, int col) {
        JButton clickedButton = tileButtons[row][col];
        clickedButton.setBackground(Color.white);
        clickedButton.setForeground(customColor); //not working
        clickedButton.setEnabled(false);
        if (!model.getSymbolFlip(row, col) && flippedCount < 2) {
            char symbol = model.getSymbol(row, col);
            updateTile(clickedButton, symbol);

            symbolsArray[flippedCount] = symbol;
            positionsArray[flippedCount * 2] = row; // Store row
            positionsArray[flippedCount * 2 + 1] = col; // Store col
            flippedCount++;

            if (flippedCount == 2) {
                if (symbolsArray[0] == symbolsArray[1]) {
                	

                    hideButton(positionsArray);
                    pairsRemaining--;

                    if (pairsRemaining == 0) {
                        //System.out.println("Game over! All pairs found! ARGHHHHHHHHHHHHHHHH");
                        GameOverDialog.showGameOverDialog(turns, model.getRows(), model.getCols());
                        String difficultyString = model.getRows()+" x "+model.getCols();
                        String sTurns = ""+turns;//fix this, mad sloppy
                        //username="test";
                        leaderboardHelper.addLeaderboard(username, sTurns, difficultyString);//add the score to the leaderboard
                    }
                } else {
                	turns++;
                	updateTurnsLabel();
                    resetTilesAfterDelay(positionsArray);
                }
                clearArraysAndResetCount();
                
            }
        }
    }

    private void updateTile(JButton clickedButton, char symbol) {
        clickedButton.setText("" + symbol);
    }

    private void resetTile(int row, int col) {
        JButton clickedButton = tileButtons[row][col];
        clickedButton.setBackground(customColor);
        clickedButton.setText("");
    }

    private void resetTilesAfterDelay(int[] positionsArray) {
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Delay for 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Reset both tiles after delay
            int row1 = positionsArray[0];
            int col1 = positionsArray[1];
            int row2 = positionsArray[2];
            int col2 = positionsArray[3];
            JButton clickedButton1 = tileButtons[row1][col1];
            clickedButton1.setEnabled(true);
            JButton clickedButton2 = tileButtons[row2][col2];
            clickedButton2.setEnabled(true);
            resetTile(row1, col1);
            resetTile(row2, col2);          
        }).start();
    }

    private void clearArraysAndResetCount() {
        symbolsArray = new char[2];
        positionsArray = new int[4];
        flippedCount = 0;
    }
    
    private void hideButton(int[] positionsArray) {
        for (int i = 0; i < 2; i++) {
            int row = positionsArray[i * 2];
            int col = positionsArray[i * 2 + 1];
            JButton buttonToHide = tileButtons[row][col];
            buttonToHide.setEnabled(false);
            //buttonToHide.setVisible(false); if figure out how to make it look better with disappearing 
        }
    }
    
    private void updateTurnsLabel() {
        turnsLabel.setText("TURNS: " + turns);
        turnsLabel.setForeground(guessColor);
    }
    
    private JPanel createUsernamePanel(MemoryFrame view) {
        JPanel usernamePanel = new JPanel(new FlowLayout());
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        Color customColor = new Color(66, 0, 57);
        usernamePanel.setBackground(customColor); 
        
        JLabel usernameLabel = new JLabel("Enter Username: ");
        usernameLabel.setForeground(Color.WHITE);
        JTextField usernameField = new JTextField(15);

        JButton submitButton = new JButton("Submit");
        Color customColor2 = new Color(224, 123, 224);
        submitButton.setBackground(customColor2); 
        submitButton.addActionListener(e -> {
            username = usernameField.getText().trim();
            if (username.isEmpty()) {
                username = "Anonymous";
            }
        });

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        usernamePanel.add(submitButton);
        return usernamePanel;
    }
}
