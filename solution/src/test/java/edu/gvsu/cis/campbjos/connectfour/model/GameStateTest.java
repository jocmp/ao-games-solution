package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createEmptyGrid;
import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createFullBoard;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class GameStateTest {

    private static final String PLAYER_ONE_VALUE = "player-one";

    @Test
    public void createValidGameState() throws Exception {
        GameState gameState = GameState.createFromJson("[[]]", PLAYER_ONE_VALUE);

        assertNotNull(gameState);
    }

    @Test
    public void checkAvailableSpaces() throws Exception {
        Board board = Board.createFromJson(createEmptyGrid());
        Player playerOne = new Player(PLAYER_ONE_VALUE);

        int lastColumn = Board.COLUMN_SIZE - 1;
        for (int row = 0; row < Board.ROW_SIZE; row++) {
            for (int column = 0; column < Board.COLUMN_SIZE - 1; column++) {
                board.placePiece(playerOne, column);
            }
        }
        GameState gameState = new GameState(board, playerOne);

        assertTrue(gameState.getAvailableMoves().contains(lastColumn));
    }

    @Test
    public void checkAvailableSpacesOnFullBoard() {
        Board board = Board.createFromJson(createFullBoard());
        Player player = new Player(PLAYER_ONE_VALUE);

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.getAvailableMoves().isEmpty());
    }

    @Test
    public void checkForVerticalWin() {
        Board board = Board.createFromJson(createEmptyGrid());
        Player player = new Player(PLAYER_ONE_VALUE);

        for (int i = 0; i < GameState.PIECE_COUNT_TO_WIN; i++) {
            board.placePiece(player, 0);
        }

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }

    @Test
    public void checkForHorizontalWin() {
        Board board = Board.createFromJson(createEmptyGrid());
        Player player = new Player(PLAYER_ONE_VALUE);

        for (int column = 0; column < GameState.PIECE_COUNT_TO_WIN; column++) {
            board.placePiece(player, column);
        }

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }

}