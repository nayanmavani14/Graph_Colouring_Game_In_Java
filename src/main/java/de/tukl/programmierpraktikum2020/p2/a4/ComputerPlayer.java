package de.tukl.programmierpraktikum2020.p2.a4;

import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.GraphException;
import de.tukl.programmierpraktikum2020.p2.a2.Color;
import de.tukl.programmierpraktikum2020.p2.a2.GameMove;

public interface ComputerPlayer {
    void initialize(Graph<Color, Integer> graph, GameMove move, Color player, int numPlayers) throws GraphException;

    void makeMove() throws GraphException;
}
