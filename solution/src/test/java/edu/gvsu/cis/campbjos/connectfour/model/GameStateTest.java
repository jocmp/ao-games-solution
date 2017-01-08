package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static edu.gvsu.cis.campbjos.connectfour.model.GameState.PIECE_COUNT_TO_WIN;
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

    @Test
    public void checkForVerticalWin() {
        Board board = Board.createFromJson(createEmptyGrid());
        Player player = new Player(PLAYER_ONE_VALUE);

        for (int i = 0; i < PIECE_COUNT_TO_WIN; i++) {
            board.placePiece(player, 0);
        }

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }

    @Test
    public void checkForHorizontalWin() {
        Board board = Board.createFromJson(createEmptyGrid());
        Player player = new Player(PLAYER_ONE_VALUE);

        for (int column = 0; column < PIECE_COUNT_TO_WIN; column++) {
            board.placePiece(player, column);
        }

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }

    @Test
    public void checkForReverseDiagonalWin() {
        // @formatter:off
        String input =
                "[[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 0, 0, 0]," +
                "[1, 0, 0, 1, 0, 0, 0]," +
                "[1, 1, 1, 0, 0, 0, 0]," +
                "[2, 1, 1, 0, 0, 0, 0]," +
                "[1, 2, 2, 1, 0, 2, 0]]";
        // @formatter:on

        Board board = Board.createFromJson(input);
        Player player = new Player(PLAYER_ONE_VALUE);

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }

    @Test
    public void checkForReverseDiagonalInCenter() {
        // @formatter:off
        String input =
                "[[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 1, 0, 0]," +
                "[1, 0, 0, 1, 0, 0, 0]," +
                "[1, 1, 1, 0, 0, 0, 0]," +
                "[2, 1, 1, 0, 0, 0, 0]," +
                "[0, 2, 2, 1, 0, 2, 0]]";
        // @formatter:on
        Board board = Board.createFromJson(input);
        Player player = new Player(PLAYER_ONE_VALUE);

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }

    @Test
    public void checkForReverseDiagonalPlayerTwoWin() {
        // @formatter:off
        String input =
                "[[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 1, 0, 0]," +
                "[1, 0, 0, 1, 0, 0, 2]," +
                "[1, 1, 0, 0, 0, 2, 0]," +
                "[2, 1, 1, 0, 2, 0, 0]," +
                "[0, 2, 2, 2, 0, 2, 0]]";
        // @formatter:on
        Board board = Board.createFromJson(input);
        Player player = new Player("player-two");

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }


    /**
     *
     //  diagonal

     board.placePiece(player, 0);
     board.placePiece(opponent, 0);
     board.placePiece(player, 0);
     board.placePiece(opponent, 1);
     board.placePiece(player, 0);
     board.placePiece(opponent, 1);
     board.placePiece(player, 1);
     board.placePiece(opponent, 2);
     board.placePiece(player, 2);
     board.placePiece(opponent, 5);
     board.placePiece(player, 3);
     */
}