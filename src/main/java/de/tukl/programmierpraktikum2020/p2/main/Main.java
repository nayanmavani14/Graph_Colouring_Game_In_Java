package de.tukl.programmierpraktikum2020.p2.main;

import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a2.Color;
import de.tukl.programmierpraktikum2020.p2.a4.ComputerPlayer;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        new WelcomeScreen(conf -> {
            try {
                System.out.println("Starting game with " + conf.numPlayers + " players and board size " + conf.boardSize);
                System.out.println("Game ID: " + conf.seed);
                System.out.println("Computer players: " + conf.computerPlayers);

                // Generate graph
                GraphGenerator gg = new GraphGenerator(conf.seed);
                Graph<Color, Integer> graph = Factory.constructGraph();
                gg.generate(graph, conf.boardSize);

                // Instantiate computer players
                Map<Color, ComputerPlayer> computerPlayers = new HashMap<>();
                for (Color computerColor : conf.computerPlayers) {
                    computerPlayers.put(computerColor, Factory.constructComputerPlayer());
                }

                // Show GameBoard
                new GameBoard(new Game(graph, conf.numPlayers, computerPlayers), gg.getNodeCoordinates()).show();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }).show();
    }
}
