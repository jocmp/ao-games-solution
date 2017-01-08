package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static edu.gvsu.cis.campbjos.connectfour.model.Player.PLAYER_ONE_VALUE;
import static org.junit.Assert.assertNotEquals;

public class GameStateMoveTest {


    @Test
    public void dontGoToClosedColumn() {
        // @formatter:off
       String input =
               "[[0, 2, 0, 0, 0, 0, 0], " +
               "[0, 1, 0, 0, 0, 0, 0], " +
               "[0, 2, 0, 0, 0, 0, 0], " +
               "[0, 1, 0, 0, 0, 0, 0], " +
               "[0, 2, 0, 0, 0, 0, 0], " +
               "[0, 1, 0, 0, 0, 0, 0]]";
        // @formatter:on

        checkPlayerInput(PLAYER_ONE_VALUE, input);
    }

    private void checkPlayerInput(String playerValue, String jsonInput) {
        Board board = Board.createFromJson(jsonInput);
        Player player = new Player(playerValue);

        GameState gameState = new GameState(board, player);

        assertNotEquals(gameState.getMove(), 1);
    }
}
