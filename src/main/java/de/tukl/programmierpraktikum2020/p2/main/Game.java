package de.tukl.programmierpraktikum2020.p2.main;

import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.GraphException;
import de.tukl.programmierpraktikum2020.p2.a2.Color;
import de.tukl.programmierpraktikum2020.p2.a2.GameMove;
import de.tukl.programmierpraktikum2020.p2.a4.ComputerPlayer;

import java.util.Map;

/**
 * Class that manages the status (board, current player) of a game and triggers computer player moves.
 */
class Game {
    final Graph<Color, Integer> graph;
    final GameMove move;
    final int numPlayers;
    private final Map<Color, ComputerPlayer> computerPlayers;
    private Color currentPlayer = Color.values()[0]; // nextPlayer() has to be called at the beginning

    Game(Graph<Color, Integer> graph, int numPlayers, Map<Color, ComputerPlayer> computerPlayers) throws GraphException {
        this.graph = graph;
        this.move = Factory.constructGameMove(graph);
        this.numPlayers = numPlayers;
        this.computerPlayers = computerPlayers;
        for (Map.Entry<Color, ComputerPlayer> entry : computerPlayers.entrySet()) {
            entry.getValue().initialize(graph, move, entry.getKey(), numPlayers);
        }
    }

    Color currentPlayer() {
        return currentPlayer;
    }

    void nextPlayer(Runnable updateGUI, Runnable releaseLock) {
        // Move to next player and update the GUI label
        currentPlayer = Color.values()[(currentPlayer.ordinal()) % numPlayers + 1];
        updateGUI.run();

        // Check whether we need to do a computer player move
        ComputerPlayer computerPlayer = computerPlayers.get(currentPlayer);
        if (computerPlayer == null) {
            // No computer player -> release the lock
            releaseLock.run();
        } else {
            // Computer player(s) -> start a new thread in order to not freeze the GUI
            new Thread(() -> {
                ComputerPlayer cp = computerPlayer; // need local variable in lambda expression
                do {
                    // Wait before doing the move
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ignored) {
                    }

                    // Make move
                    try {
                        cp.makeMove();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                    }

                    // Again, move to next player and update the GUI label
                    currentPlayer = Color.values()[(currentPlayer.ordinal()) % numPlayers + 1];
                    updateGUI.run();

                    // Check whether the next player is a computer, too
                    cp = computerPlayers.get(currentPlayer);
                } while (cp != null); // Do all computer player moves in the same thread

                // After the last computer player move, release the lock such that human can make a move
                releaseLock.run();
            }).start();
        }
    }
}
