package de.tukl.programmierpraktikum2020.p2.main;

import de.tukl.programmierpraktikum2020.p2.a2.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class for the GUI window that asks the parameters before starting a game.
 */
public class WelcomeScreen {
    private static final int MAX_PLAYERS = Color.values().length - 1;
    private final JFrame frame;
    private final JComboBox<Integer> numPlayers;
    private final JLabel[] playerTypeLabels = new JLabel[MAX_PLAYERS];
    private final JComboBox[] playerTypes = new JComboBox[MAX_PLAYERS];
    private final JSpinner boardSize;
    private final JTextField seed;
    private final Consumer<GameConfiguration> startAction;

    public WelcomeScreen(Consumer<GameConfiguration> startAction) {
        this.startAction = startAction;
        this.frame = new JFrame("Graph Coloring Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.getContentPane().add(mainPanel);

        // Greeting
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(new JLabel("Welcome to Graph Coloring Game!"));
            panel.add(Box.createHorizontalGlue());
            mainPanel.add(panel);
        }
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Number of players
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            JLabel label = new JLabel("Number of players: ");
            numPlayers = new JComboBox<>(IntStream.rangeClosed(2, MAX_PLAYERS).boxed().toArray(Integer[]::new));
            numPlayers.setMaximumSize(numPlayers.getPreferredSize());
            label.setLabelFor(numPlayers);
            panel.add(label);
            panel.add(numPlayers);
            panel.add(Box.createHorizontalGlue());
            mainPanel.add(panel);
        }
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Player types (label)
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(new JLabel("Please select the player types:"));
            panel.add(Box.createHorizontalGlue());
            mainPanel.add(panel);
        }
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Player types (grid with labels and select boxes)
        {
            JPanel panel = new JPanel(new GridBagLayout());
            for (int i = 0; i < MAX_PLAYERS; i++) {
                playerTypeLabels[i] = new JLabel(Color.values()[i + 1].name() + ": ");
                playerTypes[i] = new JComboBox<>(new String[]{"Human", "Computer"});
                playerTypes[i].setMaximumSize(playerTypes[i].getPreferredSize());
                playerTypeLabels[i].setLabelFor(playerTypes[i]);

                GridBagConstraints c = new GridBagConstraints();
                c.gridy = i;
                c.insets = new Insets(0, 0, 5, 0);
                c.gridx = 0;
                c.anchor = GridBagConstraints.LINE_END;
                panel.add(playerTypeLabels[i], c);
                c.gridx = 1;
                c.anchor = GridBagConstraints.LINE_START;
                panel.add(playerTypes[i], c);
                c.gridx = 2;
                c.weightx = 1;
                panel.add(Box.createHorizontalGlue(), c);
            }

            // Glue (if not all players are shown)
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = MAX_PLAYERS;
            c.weighty = 1;
            panel.add(Box.createVerticalGlue(), c);
            mainPanel.add(panel);
        }
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Number of nodes
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            JLabel label = new JLabel("Number of nodes in the graph: ");
            boardSize = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
            boardSize.setMaximumSize(boardSize.getPreferredSize());
            label.setLabelFor(boardSize);
            panel.add(label);
            panel.add(boardSize);
            panel.add(Box.createHorizontalGlue());
            mainPanel.add(panel);
        }
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Seed
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            JLabel label = new JLabel("Game ID (seed): ");
            seed = new JTextField(Long.toString(new Random().nextLong()), Long.toString(Long.MIN_VALUE).length());
            seed.setMaximumSize(seed.getPreferredSize());
            label.setLabelFor(seed);
            panel.add(label);
            panel.add(seed);
            panel.add(Box.createHorizontalGlue());
            mainPanel.add(panel);
        }
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));


        // Start button
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            JButton button = new JButton("Start Game");
            button.addActionListener(this::startGame);
            panel.add(button);
            panel.add(Box.createHorizontalGlue());
            mainPanel.add(panel);
        }

        // Size of frame
        frame.pack();
        updatePlayerTypes();
        numPlayers.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                updatePlayerTypes();
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    private void updatePlayerTypes() {
        int numPlayers = this.numPlayers.getSelectedIndex() + 2;
        for (int i = 2; i < numPlayers; i++) {
            playerTypeLabels[i].setVisible(true);
            playerTypes[i].setVisible(true);
        }
        for (int i = numPlayers; i < MAX_PLAYERS; i++) {
            playerTypeLabels[i].setVisible(false);
            playerTypes[i].setVisible(false);
        }
    }

    private void startGame(ActionEvent actionEvent) {
        long seed;
        try {
            seed = Long.parseLong(this.seed.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    "The Game ID is the seed for the random graph generator.\nIt must be a number of type long.",
                    "Invalid Game ID!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int numPlayers = this.numPlayers.getSelectedIndex() + 2;
        Set<Color> computerPlayers = Arrays.stream(Color.values())
                .filter(c -> c.ordinal() > 0 && c.ordinal() <= numPlayers && playerTypes[c.ordinal() - 1].getSelectedIndex() == 1)
                .collect(Collectors.toSet());
        int boardSize = (int) this.boardSize.getValue();
        frame.setVisible(false);
        frame.dispose();
        startAction.accept(new GameConfiguration(numPlayers, boardSize, seed, computerPlayers));
    }
}
