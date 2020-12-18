package de.tukl.programmierpraktikum2020.p2.a1;

import java.util.*;

public class GraphImpl<D, W> implements Graph<D, W>{
    public HashMap<Integer, D> ListNodes = new HashMap<>();
    public List<HashMap<Integer, W>> OutgoingNodes = new LinkedList<>();
    int TotalNodes;

    @Override
    public int addNode(D data) {
        ListNodes.put(TotalNodes, data);
        OutgoingNodes.add(TotalNodes, new HashMap<>());
        TotalNodes ++;
        return  TotalNodes - 1;
    }

    @Override
    public D getData(int nodeId) throws InvalidNodeException {
        if (nodeId >= TotalNodes) {
            throw new InvalidNodeException(nodeId);
        } else {
            return ListNodes.get(nodeId);
        }
    }

    @Override
    public void setData(int nodeId, D data) throws InvalidNodeException {
        if (nodeId >= TotalNodes) {
            throw new InvalidNodeException(nodeId);
        } else {
            ListNodes.replace(nodeId, data);
        }
    }

    @Override
    public void addEdge(int fromId, int toId, W weight) throws InvalidNodeException, DuplicateEdgeException {
        if (fromId >= TotalNodes) {
            throw new InvalidNodeException(fromId);
        } else if (toId >= TotalNodes) {
            throw new InvalidNodeException(toId);
        } else if (OutgoingNodes.get(fromId).containsKey(toId)) {
            throw new DuplicateEdgeException(fromId, toId);
        } else {
            OutgoingNodes.get(fromId).put(toId, weight);
        }
    }

    @Override
    public W getWeight(int fromId, int toId) throws InvalidEdgeException {
        if (fromId >= TotalNodes || toId >= TotalNodes || !OutgoingNodes.get(fromId).containsKey(toId)) {
            throw new InvalidEdgeException(fromId, toId);
        } else {
            return OutgoingNodes.get(fromId).get(toId);
        }
    }

    @Override
    public void setWeight(int fromId, int toId, W weight) throws InvalidEdgeException {
        if (fromId >= TotalNodes || toId >= TotalNodes || !OutgoingNodes.get(fromId).containsKey(toId)) {
            throw new InvalidEdgeException(fromId, toId);
        } else {
            OutgoingNodes.get(fromId).replace(toId, weight);
        }
    }

    @Override
    public Set<Integer> getNodeIds() {
        Set<Integer> SetofNodes = new HashSet<>();
        ListNodes.forEach((a,b) -> SetofNodes.add(a) );
        return SetofNodes;
    }

    @Override
    public Set<Integer> getIncomingNeighbors(int nodeId) throws InvalidNodeException {
        if (nodeId >= TotalNodes) {
            throw new InvalidNodeException(nodeId);
        } else {
            Set<Integer> SetofNodes = new HashSet<>();
            for (int i = 0; i < OutgoingNodes.size(); i++) {
                if (OutgoingNodes.get(i).containsKey(nodeId)) {
                    SetofNodes.add(i);
                }
            }
            return SetofNodes;
        }
    }

    @Override
    public Set<Integer> getOutgoingNeighbors(int nodeId) throws InvalidNodeException {
        if (nodeId >= TotalNodes) {
            throw new InvalidNodeException(nodeId);
        } else {
            Set<Integer> SetofNodes = new HashSet<>();
            OutgoingNodes.get(nodeId).forEach((a,b) -> SetofNodes.add(a));
            return SetofNodes;
        }
    }

}
