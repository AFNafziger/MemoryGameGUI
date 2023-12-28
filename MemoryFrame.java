package edu.wm.cs.cs301.guimemorygame.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import edu.wm.cs.cs301.guimemorygame.model.MemoryGameForGUI;

public class MemoryFrame {
    
    private final JFrame frame;
    private final MemoryGameForGUI model;
    private final MemoryGrid memoryGrid;
    
    public MemoryFrame(int rows, int cols, Boolean instruct) {
        this.model = new MemoryGameForGUI(rows,cols,2);
        this.memoryGrid = new MemoryGrid(this, model);
        
        this.frame = createAndShowGUI();
        showLeaderboard();
        //new LeaderboardDialog this wasnt working FIGURE OUT
        if (instruct == true) {
        new InstructionsDialog(this);
        }
        
    }

    private JFrame createAndShowGUI() {
        JFrame frame = new JFrame("Memory Game");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(createMenuBar());
        
        if (memoryGrid != null) {
            frame.add(memoryGrid, BorderLayout.CENTER);
        } else {
            System.err.println("MemoryGrid is null.");
        }
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        JMenu difficultyMenu = new JMenu("Difficulty");
        menuBar.setBackground(Color.WHITE);
        menuBar.add(helpMenu);
        menuBar.add(difficultyMenu);
        
        JMenuItem instructionsItem = new JMenuItem("Instructions...");
        instructionsItem.addActionListener(event -> new InstructionsDialog(this));
        helpMenu.add(instructionsItem);
        
        JMenuItem EasyItem = new JMenuItem("Easy 3x4");
		EasyItem.addActionListener(event -> new MemoryFrame(3, 4,false));
		difficultyMenu.add(EasyItem);
		
		JMenuItem MediumItem = new JMenuItem("Medium 4x7");
		MediumItem.addActionListener(event -> new MemoryFrame(4, 7,false));
		difficultyMenu.add(MediumItem);
		
		JMenuItem HardItem = new JMenuItem("Hard 7x8");
		HardItem.addActionListener(event -> new MemoryFrame(7, 8,false));
		difficultyMenu.add(HardItem);
		
        
        return menuBar;
    }

    private void showLeaderboard() {
        LeaderboardDialog leaderboardDialog = new LeaderboardDialog(frame);
    }
    
    public JFrame getFrame() {
        return frame;
    }
}