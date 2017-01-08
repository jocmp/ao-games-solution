package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;


import static edu.gvsu.cis.campbjos.connectfour.model.GameState.createFromJson;
import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createEmptyGrid;
import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createFullBoard;
import static org.junit.Assert.*;


public class GameStateTest {

    private static final String PLAYER_ONE_VALUE = "player-one";

    @Test
    public void createValidGameState() throws Exception {
        GameState gameState = createFromJson(createEmptyGrid(), PLAYER_ONE_VALUE);

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
    public void stopIfColumnOneIsFull() {
        Board board = Board.createFromJson(createEmptyGrid());
        Player player = new Player(PLAYER_ONE_VALUE);
        Player opponent = Player.nextPlayer(player);

        int columnOne = 1;
        for (int i = 0; i < 3; i++) {
            board.placePiece(player, columnOne);
        }
        board.placePiece(opponent, columnOne);
        board.placePiece(player, columnOne);
        board.placePiece(player, columnOne);

        GameState gameSate = new GameState(board, player);

        assertFalse(gameSate.getAvailableMoves().contains(columnOne));
    }
}