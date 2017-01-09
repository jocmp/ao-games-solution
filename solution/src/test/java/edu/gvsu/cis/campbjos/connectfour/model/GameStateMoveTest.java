package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static edu.gvsu.cis.campbjos.connectfour.model.Board.ROW_SIZE;
import static edu.gvsu.cis.campbjos.connectfour.model.GameStateFactory.createGameStatePlayerOne;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameStateMoveTest {

    @Test(timeout = 10000 /* milliseconds */)
    public void doesNotMoveToClosedColumn() {
        // @formatter:off
       String input =
               "[[0, 2, 0, 0, 0, 0, 0], " +
                "[0, 1, 0, 0, 0, 0, 0], " +
                "[0, 2, 0, 0, 0, 0, 0], " +
                "[0, 1, 0, 0, 0, 0, 0], " +
                "[0, 2, 0, 0, 0, 0, 0], " +
                "[0, 1, 0, 0, 0, 0, 0]]";
        // @formatter:on
        GameState gameState = createGameStatePlayerOne(input);

        assertNotEquals(gameState.makeMove(), 1);
    }

    @Test(timeout = 10000)
    public void takesLastPossibleMoveInDraw() {
        List<List<Integer>> grid = new ArrayList<>();
        int rowLimit = ROW_SIZE / 2;

        for (int i = 0; i < rowLimit; i++) {
            grid.add(asList(1, 1, 2, 2, 1, 1, 2));
            grid.add(asList(2, 2, 1, 1, 2, 2, 1));
        }

        grid.get(0).set(6, 0);

        GameState gameState = createGameStatePlayerOne(grid.toString());

        assertEquals(gameState.makeMove(), 6);
    }
}
