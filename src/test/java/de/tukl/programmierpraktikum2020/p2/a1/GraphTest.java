package de.tukl.programmierpraktikum2020.p2.a1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    GraphImpl<String, Integer> map = new GraphImpl<>();
    @Test
    public void example() {
        map.addNode("Nodenumbah1");
        assertEquals(1, map.addNode("second"));
    }
    @Test
    public void example2() throws Exception {
        map.addNode("Nodenumbah1");
        map.addNode("Node42numbah1");
        map.addNode("Nodenumb2ah1");
        map.addNode("Noden43umbah1");
        assertEquals("Nodenumbah1", map.getData(0));
        assertEquals("Noden43umbah1", map.getData(3));
        Assertions.assertThrows(InvalidNodeException.class, () -> map.getData(7));
    }
    @Test
    public void example3() throws Exception {
        map.addNode("Nodenumbah1");
        map.setData(0, "numba2");
        assertEquals("numba2", map.getData(0));
        Assertions.assertThrows(InvalidNodeException.class, () -> map.setData(7, "lol"));
    }
    @Test
    public void example4() throws Exception {
        map.addNode("Nodenumbah1");
        map.addNode("Node42numbah1");
        map.addEdge(0,1, 5);
        Assertions.assertThrows(InvalidNodeException.class, () -> map.addEdge(7, 4,3));
        Assertions.assertThrows(InvalidNodeException.class, () -> map.addEdge(0, 4,3));
        Assertions.assertThrows(DuplicateEdgeException.class, () -> map.addEdge(0, 1,7));
    }
    @Test
    public void example5() throws Exception {
        map.addNode("Nodenumbah1");
        map.addNode("Node42numbah1");
        map.addNode("Node42n3umbah1");
        map.addEdge(0,1, 5);
        assertEquals(5, map.getWeight(0, 1));
        Assertions.assertThrows(InvalidEdgeException.class, () -> map.getWeight(0, 6));
        Assertions.assertThrows(InvalidEdgeException.class, () -> map.getWeight(8, 6));
        Assertions.assertThrows(InvalidEdgeException.class, () -> map.getWeight(0, 2    ));
    }
    @Test
    public void example6() throws Exception {
        map.addNode("Nodenumbah1");
        map.addNode("Node42numbah1");
        map.addNode("Node42n3umbah1");
        map.addEdge(0,1, 5);
        map.setWeight(0, 1, 8);
        assertEquals(8, map.getWeight(0, 1));
        Assertions.assertThrows(InvalidEdgeException.class, () -> map.setWeight(0, 6,32));
        Assertions.assertThrows(InvalidEdgeException.class, () -> map.setWeight(8, 6,3));
        Assertions.assertThrows(InvalidEdgeException.class, () -> map.setWeight(0, 2,6));
    }
    @Test
    public void example7()  {
        map.addNode("Nodenumbah1");
        map.addNode("Node42numbah1");
        map.addNode("Node42n3umbah1");
        Set<Integer> SetofNodes = new HashSet<>();
        SetofNodes.add(0);
        SetofNodes.add(1);
        SetofNodes.add(2);
        assertEquals(SetofNodes, map.getNodeIds());
    }
    @Test
    public void example8() throws Exception {
        map.addNode("Fabian");
        map.addNode("Christ43oph");
        map.addNode("Chr2istoph");
        map.addNode("Chris2toph");
        map.addNode("Christ53oph");
        map.addNode("Christ123oph");
        map.addEdge(0,1, 4);
        map.addEdge(2,1, 4);
        map.addEdge(2,3, 4);
        map.addEdge(2,5, 4);
        map.addEdge(3,1, 4);
        map.addEdge(4,1, 4);
        Assertions.assertThrows(InvalidNodeException.class, () -> map.getOutgoingNeighbors(7));
        Set<Integer> SetofNodes = new HashSet<>();
        SetofNodes.add(1);
        SetofNodes.add(3);
        SetofNodes.add(5);
        assertEquals(SetofNodes, map.getOutgoingNeighbors(2));
    }
    @Test
    public void example9() throws Exception {
        map.addNode("Fabian");
        map.addNode("Christ43oph");
        map.addNode("Chr2istoph");
        map.addNode("Chris2toph");
        map.addNode("Christ53oph");
        map.addNode("Christ123oph");
        map.addEdge(0,1, 4);
        map.addEdge(2,1, 4);
        map.addEdge(2,3, 4);
        map.addEdge(2,5, 4);
        map.addEdge(3,1, 4);
        map.addEdge(4,1, 4);
        Assertions.assertThrows(InvalidNodeException.class, () -> map.getIncomingNeighbors(7));
        Set<Integer> SetofNodes = new HashSet<>();
        SetofNodes.add(0);
        SetofNodes.add(2);
        SetofNodes.add(3);
        SetofNodes.add(4);
        assertEquals(SetofNodes, map.getIncomingNeighbors(1));
    }
}
