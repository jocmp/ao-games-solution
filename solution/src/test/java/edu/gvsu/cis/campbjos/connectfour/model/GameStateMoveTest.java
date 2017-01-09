package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

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
        GameState gameState = GameStateFactory.createGameStatePlayerOne(input);

        assertNotEquals(gameState.makeMove(), 1);
    }
}
