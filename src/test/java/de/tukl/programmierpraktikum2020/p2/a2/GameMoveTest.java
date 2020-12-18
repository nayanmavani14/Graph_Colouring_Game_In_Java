package de.tukl.programmierpraktikum2020.p2.a2;

import de.tukl.programmierpraktikum2020.p2.a1.Graph;
import de.tukl.programmierpraktikum2020.p2.a1.GraphImpl;
import de.tukl.programmierpraktikum2020.p2.a1.InvalidNodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameMoveTest {
    @Test
    public void miscellaneous() throws Exception {
        GraphImpl<Color, Integer> Example1 = new GraphImpl<>();
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        Example1.addEdge(2, 0, 1);
        Example1.addEdge(2, 1, 2);
        Example1.addEdge(0, 1, 2);
        Example1.addEdge(1, 0, 3);
        GameMoveImpl Examp1 = new GameMoveImpl(Example1);
        assertEquals(Color.WHITE, Examp1.arrayindextocolor(7));
    }

    @Test
    public void miscellaneous2() throws Exception {
        GraphImpl<Color, Integer> Example1 = new GraphImpl<>();
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        GameMoveImpl Examp1 = new GameMoveImpl(Example1);
        Examp1.setColor(0, Color.BLUE);
        Examp1.setColor(1, Color.YELLOW);
        assertEquals(3, Examp1.nodecolortoarray(0));
        assertEquals(4, Examp1.nodecolortoarray(1));
        assertEquals(Color.WHITE, Examp1.arrayindextocolor(0));
        assertEquals(Color.BLUE, Examp1.arrayindextocolor(3));
        assertEquals(Color.YELLOW, Examp1.arrayindextocolor(4));
    }

    @Test
    public void Example7() throws Exception {
        GraphImpl<Color, Integer> Example1 = new GraphImpl<>();
        Example1.addNode(Color.WHITE);
        Example1.addEdge(0, 0, 1);
        GameMoveImpl Examp1 = new GameMoveImpl(Example1);
        Examp1.decreaseWeight(0,0);
        assertEquals(Color.WHITE, Example1.getData(0));
        assertThrows(NegativeWeightException.class, () -> Examp1.decreaseWeight(0,0));
    }

    @Test
    public void Example4() throws Exception {
        GraphImpl<Color, Integer> Example1 = new GraphImpl<>();
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        Example1.addEdge(0, 1, 1);
        GameMoveImpl Examp1 = new GameMoveImpl(Example1);
        Examp1.setColor(0, Color.RED);
        assertEquals(Color.RED, Example1.getData(0));
        assertEquals(Color.RED, Example1.getData(1));
        assertThrows(ForcedColorException.class, () -> Examp1.setColor(1, Color.GREEN));
    }

    @Test
    public void Example2() throws Exception {
        GraphImpl<Color, Integer> Example1 = new GraphImpl<>();
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        Example1.addEdge(0, 1, 1);
        Example1.addEdge(2, 1, 3);
        Example1.addEdge(2, 2, 3);
        Example1.addEdge(3, 1, 1);
        Example1.addEdge(3, 2, 1);
        GameMoveImpl Examp1 = new GameMoveImpl(Example1);
        Examp1.setColor(0, Color.RED);
        assertEquals(Color.RED, Example1.getData(0));
        assertEquals(Color.WHITE, Example1.getData(1));
        assertEquals(Color.WHITE, Example1.getData(2));
        assertEquals(Color.WHITE, Example1.getData(3));
        Examp1.setColor(2, Color.GREEN);
        assertEquals(Color.RED, Example1.getData(0));
        assertEquals(Color.GREEN, Example1.getData(1));
        assertEquals(Color.GREEN, Example1.getData(2));
        assertEquals(Color.WHITE, Example1.getData(3));
        Examp1.setColor(3, Color.RED);
        assertEquals(Color.RED, Example1.getData(0));
        assertEquals(Color.GREEN, Example1.getData(1));
        assertEquals(Color.GREEN, Example1.getData(2));
        assertEquals(Color.RED, Example1.getData(3));
        Examp1.decreaseWeight(2,1);
        assertEquals(Color.RED, Example1.getData(0));
        assertEquals(Color.GREEN, Example1.getData(1));
        assertEquals(Color.GREEN, Example1.getData(2));
        assertEquals(Color.RED, Example1.getData(3));
        Examp1.decreaseWeight(2,1);
        assertEquals(Color.RED, Example1.getData(0));
        assertEquals(Color.RED, Example1.getData(1));
        assertEquals(Color.GREEN, Example1.getData(2));
        assertEquals(Color.RED, Example1.getData(3));
    }

    @Test
    public void Example5() throws Exception {
        GraphImpl<Color, Integer> Example1 = new GraphImpl<>();
        Example1.addNode(Color.WHITE);
        Example1.addEdge(0, 0, 1);
        GameMoveImpl Examp1 = new GameMoveImpl(Example1);
        Examp1.setColor(0, Color.RED);
        assertEquals(Color.RED, Example1.getData(0));
        assertThrows(ForcedColorException.class, () -> Examp1.setColor(0, Color.GREEN));
    }

    @Test
    public void Example6() throws Exception {
        GraphImpl<Color, Integer> Example1 = new GraphImpl<>();
        Example1.addNode(Color.WHITE);
        Example1.addEdge(0, 0, 1);
        GameMoveImpl Examp1 = new GameMoveImpl(Example1);
        Examp1.setColor(0, Color.RED);
        assertEquals(Color.RED, Example1.getData(0));
        Examp1.decreaseWeight(0,0);
        assertEquals(Color.RED, Example1.getData(0));
        Examp1.setColor(0, Color.RED);
        assertEquals(Color.RED, Example1.getData(0));
        Examp1.setColor(0, Color.GREEN);
        assertEquals(Color.GREEN, Example1.getData(0));
    }

    @Test
    public void example() throws Exception {
        GraphImpl<Color, Integer> Example1 = new GraphImpl<>();
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        Example1.addNode(Color.WHITE);
        Example1.addEdge(2, 0, 1);
        Example1.addEdge(2, 1, 2);
        Example1.addEdge(0, 1, 2);
        Example1.addEdge(1, 0, 3);
        GameMoveImpl Examp1 = new GameMoveImpl(Example1);
        assertEquals(Color.WHITE, Example1.getData(0));
        assertEquals(Color.WHITE, Example1.getData(1));
        assertEquals(Color.WHITE, Example1.getData(2));
        Examp1.setColor(2, Color.RED);
        assertEquals(Color.WHITE, Example1.getData(0));
        assertEquals(Color.WHITE, Example1.getData(1));
        assertEquals(Color.RED, Example1.getData(2));
        Examp1.increaseWeight(2, 1);
        assertEquals(Color.RED, Example1.getData(0));
        assertEquals(Color.RED, Example1.getData(1));
        assertEquals(Color.RED, Example1.getData(2));
        Examp1.setColor(2, Color.RED);
        assertEquals(Color.RED, Example1.getData(0));
        assertEquals(Color.RED, Example1.getData(1));
        assertEquals(Color.RED, Example1.getData(2));
        Examp1.setColor(2, Color.GREEN);
        assertEquals(Color.GREEN, Example1.getData(0));
        assertEquals(Color.GREEN, Example1.getData(1));
        assertEquals(Color.GREEN, Example1.getData(2));
    }

}
