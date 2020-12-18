package de.tukl.programmierpraktikum2020.p2.a2;

public class NegativeWeightException extends Exception {
    public final int fromId;
    public final int toId;

    public NegativeWeightException(int fromId, int toId) {
        super("Cannot apply a negative weight to edge " + fromId + " -> " + toId + "!");
        this.fromId = fromId;
        this.toId = toId;
    }
}
