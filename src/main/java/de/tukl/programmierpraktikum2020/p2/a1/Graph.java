package de.tukl.programmierpraktikum2020.p2.a1;

import java.util.Set;

/**
 * An interface for directed graphs.
 *
 * @param <D> Type for the data that is stored in the nodes.
 * @param <W> Type for the weight that is stored in the edges.
 */
public interface Graph<D, W> {
    /**
     * Add a new node to the graph.
     *
     * @param data the data that should be stored in the new node
     * @return a unique identifier for the created node
     */
    int addNode(D data);

    /**
     * Get the data that is stored in a node.
     *
     * @param nodeId identifier of the node to read
     * @return the data for the node
     * @throws InvalidNodeException if nodeId is invalid
     */
    D getData(int nodeId) throws InvalidNodeException;

    /**
     * Set the data that is stored in a node.
     *
     * @param nodeId identifier of the node to update
     * @param data   the updated data for the node
     * @throws InvalidNodeException if nodeId is invalid
     */
    void setData(int nodeId, D data) throws InvalidNodeException;

    /**
     * Add a new directed edge to the graph.
     *
     * @param fromId identifier of the originating node
     * @param toId   identifier of the target node
     * @param weight the weight for the edge
     * @throws InvalidNodeException   if fromId or toId are invalid
     * @throws DuplicateEdgeException if the graph already contains an edge from node fromId to node toId
     */
    void addEdge(int fromId, int toId, W weight) throws InvalidNodeException, DuplicateEdgeException;

    /**
     * Get the weight of the given edge.
     *
     * @param fromId identifier of the originating node
     * @param toId   identifier of the target node
     * @return the weight of the given edge
     * @throws InvalidEdgeException if the graph does not contain an edge from fromId to toId or the ids are invalid
     */
    W getWeight(int fromId, int toId) throws InvalidEdgeException;

    /**
     * Set the weight of an existing edge.
     *
     * @param fromId identifier of the originating node
     * @param toId   identifier of the target node
     * @param weight the updated weight for the edge
     * @throws InvalidEdgeException if the graph does not contain an edge from fromId to toId or the ids are invalid
     */
    void setWeight(int fromId, int toId, W weight) throws InvalidEdgeException;

    /**
     * Get the identifiers of all nodes that are present in the graph.
     *
     * @return a set of identifiers
     */
    Set<Integer> getNodeIds();

    /**
     * Get the identifiers of all nodes that have an edge to the given node.
     *
     * @param nodeId identifier of the target node
     * @return the identifiers of all originating nodes
     * @throws InvalidNodeException if nodeId is invalid
     */
    Set<Integer> getIncomingNeighbors(int nodeId) throws InvalidNodeException;

    /**
     * Get the identifiers of all nodes that the given node has an edge to.
     *
     * @param nodeId identifier of the originating node
     * @return the identifiers of all target nodes
     * @throws InvalidNodeException if nodeId is invalid
     */
    Set<Integer> getOutgoingNeighbors(int nodeId) throws InvalidNodeException;
}
