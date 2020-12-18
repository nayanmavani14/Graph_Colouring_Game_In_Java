package de.tukl.programmierpraktikum2020.p2.a2;

import de.tukl.programmierpraktikum2020.p2.a1.GraphException;

public interface GameMove {
    void setColor(int nodeId, Color color) throws GraphException, ForcedColorException;

    void increaseWeight(int fromId, int toId) throws GraphException;

    void decreaseWeight(int fromId, int toId) throws GraphException, NegativeWeightException;
}
