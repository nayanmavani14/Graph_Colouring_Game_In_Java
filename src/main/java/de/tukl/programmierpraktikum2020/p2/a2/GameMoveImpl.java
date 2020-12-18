package de.tukl.programmierpraktikum2020.p2.a2;

import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.GraphException;

public class GameMoveImpl implements GameMove {
    Graph<Color, Integer> board;

    public GameMoveImpl(Graph<Color, Integer> board) {
        this.board = board;
        try {
            for (int vary : this.board.getNodeIds()) {
                this.board.setData(vary, Color.WHITE);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setColor(int nodeId, Color color) throws GraphException, ForcedColorException {
        int[] weightedcolors = new int[5];
        int totalweight = 0;
        for (int temp : this.board.getIncomingNeighbors(nodeId)) {
            int weightofedge = this.board.getWeight(temp, nodeId);
            totalweight += weightofedge;
            weightedcolors[nodecolortoarray(temp)] += weightofedge;
        }
        int index = 0; //to track the index
        for (int weights : weightedcolors) {
            if (this.board.getData(nodeId) != Color.WHITE && weights > totalweight/2 && color != arrayindextocolor(index)) {
                throw new ForcedColorException(nodeId, color);
            }
            index++;
        }
        this.board.setData(nodeId, color);
        updateBoard(nodeId);
    }

    @Override
    public void increaseWeight(int fromId, int toId) throws GraphException {
        this.board.setWeight(fromId, toId, this.board.getWeight(fromId, toId)+1);
        updateBoard(fromId);
    }

    @Override
    public void decreaseWeight(int fromId, int toId) throws GraphException, NegativeWeightException {
        if (this.board.getWeight(fromId, toId) == 0) {
            throw new NegativeWeightException(fromId, toId);
        } else {
            this.board.setWeight(fromId, toId, this.board.getWeight(fromId, toId)-1);
            updateBoard(fromId);
        }
    }

    public void updateBoard(int idToUpdate) {
        try {
            for (Integer integer : this.board.getOutgoingNeighbors(idToUpdate)) {
                int[] weightedcolors = new int[5];
                int totalweight = 0;
                for (int temp : this.board.getIncomingNeighbors(integer)) {
                    int weightofedge = this.board.getWeight(temp, integer);
                    totalweight += weightofedge;
                    weightedcolors[nodecolortoarray(temp)] += weightofedge;
                }
                int index = 0; //to track the index
                for (int weights : weightedcolors) {
                    if (weights > totalweight/2 && nodecolortoarray(integer) != index) {
                        this.board.setData(integer, arrayindextocolor(index));
                        updateBoard(integer);
                    }
                    index++;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int nodecolortoarray (int node) {
        try {
            switch (this.board.getData(node)) {
                case WHITE:
                    return 0;
                case RED:
                    return 1;
                case GREEN:
                    return 2;
                case BLUE:
                    return 3;
                case YELLOW:
                    return 4;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Color arrayindextocolor (int index) {
        try {
            switch (index) {
                case 0:
                    return Color.WHITE;
                case 1:
                    return Color.RED;
                case 2:
                    return Color.GREEN;
                case 3:
                    return Color.BLUE;
                case 4:
                    return Color.YELLOW;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return Color.WHITE;
    }
}
