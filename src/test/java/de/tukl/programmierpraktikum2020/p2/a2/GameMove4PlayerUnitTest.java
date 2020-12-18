package de.tukl.programmierpraktikum2020.p2.a2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameMove4PlayerUnitTest {

    GraphImpl4NodeMock<Color, Integer> graphMock;
    GameMoveImpl game;

    @BeforeEach
    public void initEach(){
        graphMock = new GraphImpl4NodeMock<>();
        game = new GameMoveImpl(graphMock);
    }


    @Test
    public void shouldWorkAsExample3() throws Exception{
        assertEquals(Color.WHITE, graphMock.getData(0));
        assertEquals(Color.WHITE, graphMock.getData(1));
        assertEquals(Color.WHITE, graphMock.getData(2));
        assertEquals(Color.WHITE, graphMock.getData(3));

        game.setColor(3, Color.RED);
        assertEquals(Color.WHITE, graphMock.getData(0));
        assertEquals(Color.WHITE, graphMock.getData(1));
        assertEquals(Color.WHITE, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        game.setColor(2, Color.GREEN);
        assertEquals(Color.WHITE, graphMock.getData(0));
        assertEquals(Color.WHITE, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        game.setColor(1, Color.BLUE);
        assertEquals(Color.WHITE, graphMock.getData(0));
        assertEquals(Color.BLUE, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        game.setColor(1, Color.YELLOW);
        assertEquals(Color.WHITE, graphMock.getData(0));
        assertEquals(Color.YELLOW, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        game.setColor(0, Color.RED);
        assertEquals(Color.RED, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        assertEquals(2, graphMock.getWeight(2,2));
        game.increaseWeight(2, 2);
        assertEquals(3, graphMock.getWeight(2,2));
        assertEquals(Color.RED, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        game.setColor(3, Color.BLUE);
        assertEquals(Color.RED, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.BLUE, graphMock.getData(3));

        game.setColor(0, Color.YELLOW);
        assertEquals(Color.YELLOW, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.BLUE, graphMock.getData(3));

        game.setColor(3, Color.RED);
        assertEquals(Color.YELLOW, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        assertEquals(2, graphMock.getWeight(2,1));
        game.increaseWeight(2, 1);
        assertEquals(3, graphMock.getWeight(2,1));
        assertEquals(Color.YELLOW, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        game.setColor(1, Color.BLUE);
        assertEquals(Color.YELLOW, graphMock.getData(0));
        assertEquals(Color.BLUE, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        game.setColor(3, Color.YELLOW);
        assertEquals(Color.YELLOW, graphMock.getData(0));
        assertEquals(Color.BLUE, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.YELLOW, graphMock.getData(3));

        game.setColor(3, Color.RED);
        assertEquals(Color.YELLOW, graphMock.getData(0));
        assertEquals(Color.BLUE, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        assertEquals(3, graphMock.getWeight(2,1));
        game.increaseWeight(2, 1);
        assertEquals(4, graphMock.getWeight(2,1));
        assertEquals(Color.YELLOW, graphMock.getData(0));
        assertEquals(Color.GREEN, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));

        // additional tests
        assertThrows(ForcedColorException.class, () -> game.setColor(2, Color.RED));

        assertEquals(3, graphMock.getWeight(2,2));
        game.decreaseWeight(2, 2);
        game.decreaseWeight(2, 2);
        game.decreaseWeight(2, 2);
        assertEquals(0, graphMock.getWeight(2,2));

        assertThrows(NegativeWeightException.class, () -> game.decreaseWeight(2, 2));

        game.setColor(2, Color.RED);
        assertEquals(Color.YELLOW, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.RED, graphMock.getData(2));
        assertEquals(Color.RED, graphMock.getData(3));



    }
}
