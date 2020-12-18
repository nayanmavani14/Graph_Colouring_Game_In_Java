package de.tukl.programmierpraktikum2020.p2.a2;

public class ForcedColorException extends Exception {
    public final int nodeId;
    public final Color color;

    public ForcedColorException(int nodeId, Color color) {
        super("Cannot set color of node " + nodeId + " because it is forced to " + color + "!");
        this.nodeId = nodeId;
        this.color = color;
    }
}
