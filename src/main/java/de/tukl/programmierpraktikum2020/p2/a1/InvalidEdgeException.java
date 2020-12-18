package de.tukl.programmierpraktikum2020.p2.a1;

public class InvalidEdgeException extends GraphException {
    public final int fromId;
    public final int toId;

    public InvalidEdgeException(int fromId, int toId) {
        super("There is no edge " + fromId + " -> " + toId + "!");
        this.fromId = fromId;
        this.toId = toId;
    }
}
