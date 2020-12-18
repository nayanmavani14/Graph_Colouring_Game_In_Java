package de.tukl.programmierpraktikum2020.p2.a2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameMoveUnitTest {

    GraphImplMock<Color, Integer> graphMock;
    GameMoveImpl game;

    @BeforeEach
    public void initEach(){
        graphMock = new GraphImplMock<>();
        game = new GameMoveImpl(graphMock);
    }


    @Test
    public void shouldInitialiseGameBoard() throws Exception{
        assertEquals(Color.WHITE, graphMock.getData(0));
        assertEquals(Color.WHITE, graphMock.getData(1));
        assertEquals(Color.WHITE, graphMock.getData(2));
    }

    @Test
    public void shouldSetColorOfNodeToPlayerColor() throws Exception{
        game.setColor(2, Color.RED);
        assertEquals(Color.WHITE, graphMock.getData(0));
        assertEquals(Color.WHITE, graphMock.getData(1));
        assertEquals(Color.RED, graphMock.getData(2));
    }

    @Test
    public void shouldIncreaseWeightOfEdge() throws Exception{
        assertEquals(1, graphMock.getWeight(2,0));
        game.increaseWeight(2, 0);
        assertEquals(2, graphMock.getWeight(2,0));
    }

    @Test
    public void shouldDecreaseWeightOfEdge() throws Exception{
        assertEquals(1, graphMock.getWeight(2,0));
        game.decreaseWeight(2, 0);
        assertEquals(0, graphMock.getWeight(2,0));
    }

    @Test
    public void shouldThrowNegativeWeightExceptionIfDecreaseWeightFor0() throws Exception{
        assertEquals(1, graphMock.getWeight(2,0));
        game.decreaseWeight(2, 0);
        assertEquals(0, graphMock.getWeight(2,0));
        assertThrows(NegativeWeightException.class, () -> game.decreaseWeight(2, 0));
    }

    @Test
    public void shouldIncreaseEdgeWeightAndRecalculateColorForNode() throws Exception{
        game.setColor(2, Color.RED);
        game.increaseWeight(2, 1);
        assertEquals(Color.RED, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.RED, graphMock.getData(2));
    }

    @Test
    public void shouldDoNothingIfSettingSameColorToNode() throws Exception{
        game.setColor(2, Color.RED);
        game.increaseWeight(2, 1);
        game.setColor(2, Color.RED);
        assertEquals(Color.RED, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.RED, graphMock.getData(2));
    }

    @Test
    public void shouldThrowForcedColorExceptionIfTryToChangeAutofillColor() throws Exception{
        game.setColor(2, Color.RED);
        game.increaseWeight(2, 1);
        game.setColor(2, Color.RED);
        assertEquals(Color.RED, graphMock.getData(0));
        assertEquals(Color.RED, graphMock.getData(1));
        assertEquals(Color.RED, graphMock.getData(2));
        assertThrows(ForcedColorException.class, () -> game.setColor(1, Color.GREEN));
    }

    @Test
    public void shouldSetColorAlsoRecalculateColorOfNeighbouringNode() throws Exception{
        game.setColor(2, Color.RED);
        game.increaseWeight(2, 1);
        game.setColor(2, Color.GREEN);
        assertEquals(Color.GREEN, graphMock.getData(0));
        assertEquals(Color.GREEN, graphMock.getData(1));
        assertEquals(Color.GREEN, graphMock.getData(2));
    }
}
