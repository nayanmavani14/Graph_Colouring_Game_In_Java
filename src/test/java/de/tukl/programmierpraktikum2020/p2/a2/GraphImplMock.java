package de.tukl.programmierpraktikum2020.p2.a2;

import de.tukl.programmierpraktikum2020.p2.a1.DuplicateEdgeException;
import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.InvalidEdgeException;
import de.tukl.programmierpraktikum2020.p2.a1.InvalidNodeException;

import java.util.HashSet;
import java.util.Set;

public class GraphImplMock<D, W> implements Graph<D, W> {

    Color node0 = Color.WHITE;
    Color node1 = Color.WHITE;
    Color node2 = Color.WHITE;

    int edge0to1 = 2;
    int edge1to0 = 3;
    int edge2to0 = 1;
    int edge2to1 = 2;

    @Override
    public int addNode(Object data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public D getData(int nodeId) throws InvalidNodeException {
        Color result;
        switch (nodeId){
            case 0:
                result = node0;
                break;
            case 1:
                result = node1;
                break;
            case 2:
                result = node2;
                break;
            default:
                throw new InvalidNodeException(nodeId);
        }
        return (D) result;
    }

    @Override
    public void setData(int nodeId, D data) throws InvalidNodeException {
        switch (nodeId){
            case 0:
                 node0 = (Color) data;
                break;
            case 1:
                node1 = (Color) data;
                break;
            case 2:
                node2 = (Color) data;
                break;
            default:
                throw new InvalidNodeException(nodeId);
        }
    }

    @Override
    public void addEdge(int fromId, int toId, Object weight) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public W getWeight(int fromId, int toId) throws InvalidEdgeException {
        Integer weight;
        if(fromId == 0 && toId == 1){
            weight = edge0to1;
        }
        else if(fromId == 1 && toId == 0){
            weight = edge1to0;
        }
        else if(fromId == 2 && toId == 0){
            weight = edge2to0;
        }
        else if(fromId == 2 && toId == 1){
            weight = edge2to1;
        }
        else{
            throw new InvalidEdgeException(fromId, toId);
        }
        return (W) weight;
    }

    @Override
    public void setWeight(int fromId, int toId, W weight) throws InvalidEdgeException {
        if(fromId == 0 && toId == 1){
           edge0to1 = (int) weight;
        }
        else if(fromId == 1 && toId == 0){
            edge1to0 = (int) weight;
        }
        else if(fromId == 2 && toId == 0){
            edge2to0 = (int) weight;
        }
        else if(fromId == 2 && toId == 1){
            edge2to1 = (int) weight;
        }
        else{
            throw new InvalidEdgeException(fromId, toId);
        }
    }

    @Override
    public Set<Integer> getNodeIds() {
        Set<Integer> s = new HashSet<>();
        s.add(0);
        s.add(1);
        s.add(2);
        return s;
    }

    @Override
    public Set<Integer> getIncomingNeighbors(int nodeId) throws InvalidNodeException {
        Set<Integer> s = new HashSet<>();
        if(nodeId == 0){
            s.add(1);
            s.add(2);
            return s;
        }
        if(nodeId == 1){
            s.add(0);
            s.add(2);
            return s;
        }
        if(nodeId == 2){
            return s;
        }
        else{
            throw new InvalidNodeException(nodeId);
        }
    }

    @Override
    public Set<Integer> getOutgoingNeighbors(int nodeId) throws InvalidNodeException {
        Set<Integer> s = new HashSet<>();
        if(nodeId == 0){
            s.add(1);
        }
        else if(nodeId == 1){
            s.add(0);
        }
        else if(nodeId == 2){
            s.add(0);
            s.add(1);
        }
        else{
            throw new InvalidNodeException(nodeId);
        }
        return s;
    }
}
