package de.tukl.programmierpraktikum2020.p2.a4;

import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.GraphException;
import de.tukl.programmierpraktikum2020.p2.a2.Color;
import de.tukl.programmierpraktikum2020.p2.a2.ForcedColorException;
import de.tukl.programmierpraktikum2020.p2.a2.GameMove;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomComputerPlayer implements ComputerPlayer {
    private final Random rand = new Random();
    private Graph<Color, Integer> graph;
    private int[] nodeIds;
    private GameMove move;
    private Color player;

    @Override
    public void initialize(Graph<Color, Integer> graph, GameMove move, Color player, int numPlayers) throws GraphException {
        this.graph = graph;
        this.nodeIds = graph.getNodeIds().stream().mapToInt(x -> x).sorted().toArray();
        this.move = move;
        this.player = player;
    }

    @Override
    public void makeMove() throws GraphException {
        // First try to set the color of a random node that does not yet have my color
        Set<Integer> attempted = new HashSet<>();
        while (attempted.size() < nodeIds.length) {
            int nodeId = nodeIds[rand.nextInt(nodeIds.length)];
            if (attempted.add(nodeId)) { // only if that node was not already attempted
                if (graph.getData(nodeId) != player) { // only if that not does not already have my color
                    try {
                        move.setColor(nodeId, player);
                        return; // successful -> we are done
                    } catch (ForcedColorException e) {
                        // make another try (while loop)
                    }
                }
            }
        }

        // There is no node that we can recolor. Find a random edge and increase its weight.
        while (true) { // loop to find a node with outgoing edges
            int fromId = nodeIds[rand.nextInt(nodeIds.length)]; // random node
            Set<Integer> outEdges = graph.getOutgoingNeighbors(fromId); // get outgoing edges
            if (!outEdges.isEmpty()) { // if there is at least one outgoing edge
                int toId = new ArrayList<>(outEdges).get(rand.nextInt(outEdges.size())); // pick a random one
                move.increaseWeight(fromId, toId);
                return; // we are done!
            }
        }
    }
}
