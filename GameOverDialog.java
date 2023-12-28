package edu.wm.cs.cs301.guimemorygame.view;
import javax.swing.*;
import java.awt.*;

public class GameOverDialog {

    private static JDialog gameOverDialog;

    public static void showGameOverDialog(int turns, int rows, int cols) {
        JPanel buttonPanel = createButtonPanel(turns, rows, cols);

        gameOverDialog = new JDialog((JFrame) null, "", true);
        gameOverDialog.add(buttonPanel);
        gameOverDialog.pack();
        gameOverDialog.setLocationRelativeTo(null);
        gameOverDialog.setVisible(true);
    }

    private static void closeGameOverDialog() {
        if (gameOverDialog != null) {
            gameOverDialog.dispose();
        }
    }

    private static JPanel createButtonPanel(int turns, int rows, int cols) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel messageLabel = new JLabel("You won in " + turns + " turns!");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> {
            closeGameOverDialog();//had to reformat this so I could close the dialog
            new MemoryFrame(rows, cols, false);
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        Dimension buttonSize = new Dimension(120, 30);
        playAgainButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel);

        return panel;
    }
}
