package de.tukl.programmierpraktikum2020.p2.main;

import de.tukl.programmierpraktikum2020.p2.a2.Color;

import java.util.Collections;
import java.util.Set;

/**
 * Simple class for storing the settings selected in {@link WelcomeScreen}.
 */
public class GameConfiguration {
    public final int numPlayers;
    public final int boardSize;
    public final long seed;
    public final Set<Color> computerPlayers;

    GameConfiguration(int numPlayers, int boardSize, long seed, Set<Color> computerPlayers) {
        this.numPlayers = numPlayers;
        this.boardSize = boardSize;
        this.seed = seed;
        this.computerPlayers = Collections.unmodifiableSet(computerPlayers);
    }
}
