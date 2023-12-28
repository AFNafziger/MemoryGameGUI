package edu.wm.cs.cs301.guimemorygame.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.guimemorygame.model.LeaderboardHelper;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;


public class InstructionsDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final CancelAction cancelAction;
    private JTextField usernameField;
    
    public InstructionsDialog(MemoryFrame view) {
        super(view.getFrame(), "Instructions", true);
        this.cancelAction = new CancelAction();
        add(createMainPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(view.getFrame());
        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        String instructionsText = "HOW TO PLAY: The tiles each have a symbol on their face and come in pairs so that there are exactly two of every symbol."
                + " To play the game, the tiles all start face down and then are shuffled randomly so that the location of any symbol"
                + " is not known. The player chooses two tiles by flipping them over. If their symbols match, the tiles are left face up"
                + " and the player’s turn continues. If they don’t match, the tiles are returned to the face-down position and the turn is over."
                + " The objective of the game is to reveal all pairs of tiles in the shortest number of turns. There are 3 different difficulties. Good luck!";

        JTextArea instructionsArea = new JTextArea(instructionsText);
        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(instructionsArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelAction");
        ActionMap actionMap = panel.getActionMap();
        actionMap.put("cancelAction", cancelAction);

        JButton button = new JButton("Exit");
        button.addActionListener(cancelAction);
        panel.add(button);

        return panel;
    }

    private class CancelAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent event) {
            dispose();
        }
    }
}
