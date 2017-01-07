package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createEmptyGrid;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class GameStateTest {

    @Test
    public void createValidGameState() throws Exception {
        GameState gameState = GameState.createFromJson("[[]]", "player-one");

        assertNotNull(gameState);
    }

    @Test
    public void createGameWithEmptyBoard() throws Exception {
        GameState gameState = GameState.createFromJson(createEmptyGrid(), "player-one");

        assertTrue(gameState.getBoard().isEmpty());
    }

    @Test
    public void checkAvailableSpaces() throws Exception {
        Board board = Board.createFromJson(createEmptyGrid());
        Player playerOne = new Player("player-one");

        int lastColumn = Board.COLUMN_SIZE - 1;
        for (int row = 0; row < Board.ROW_SIZE; row++) {
            int currentRow = row;
            for (int column = 0; column < Board.COLUMN_SIZE - 1; column++) {
                board.placePiece(playerOne, row, column);
            }
        }
        GameState gameState = new GameState(board, playerOne);

        assertTrue(gameState.getAvailableMoves().contains(lastColumn));
    }

}