package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static edu.gvsu.cis.campbjos.connectfour.model.GameState.PIECE_COUNT_TO_WIN;
import static edu.gvsu.cis.campbjos.connectfour.model.GameStateFactory.createGameStatePlayerOne;
import static edu.gvsu.cis.campbjos.connectfour.model.GameStateFactory.createGameStatePlayerTwo;
import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createEmptyGrid;
import static edu.gvsu.cis.campbjos.connectfour.model.Player.PLAYER_ONE_VALUE;
import static edu.gvsu.cis.campbjos.connectfour.model.Player.PLAYER_TWO_VALUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests winning states for players one and two based on the
 * {@link GameState} class.
 */
public class WinningStateTest {

    @Test
    public void verticalWin() {
        Board board = Board.createFromJson(createEmptyGrid());
        Player player = new Player(PLAYER_ONE_VALUE);

        for (int i = 0; i < PIECE_COUNT_TO_WIN; i++) {
            board.placePiece(player, 0);
        }

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }

    @Test
    public void horizontalWin() {
        Board board = Board.createFromJson(createEmptyGrid());
        Player player = new Player(PLAYER_ONE_VALUE);

        for (int column = 0; column < PIECE_COUNT_TO_WIN; column++) {
            board.placePiece(player, column);
        }

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }

    @Test
    public void forwardDiagonalWin() {
        Board board = Board.createFromJson(createEmptyGrid());
        Player player = new Player(PLAYER_ONE_VALUE);
        Player opponent = Player.nextPlayer(player);

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

        GameState gameState = new GameState(board, player);

        assertTrue(gameState.isWinner(player));
    }

    @Test
    public void forwardDiagonalPlayerOneWin() {
        // @formatter:off
        String input =
                "[[0, 0, 0, 0, 0, 0, 0]," +
                "[1, 0, 0, 0, 0, 0, 0]," +
                "[2, 1, 0, 0, 0, 0, 0]," +
                "[1, 2, 1, 0, 0, 0, 0]," +
                "[2, 2, 2, 1, 0, 0, 0]," +
                "[1, 1, 2, 1, 0, 0, 0]]";
        // @formatter:on
        checkPlayerOneWin(input);
    }

    @Test
    public void verticalPlayerOneWithThreePieces() {
        // @formatter:off
        String input =
                "[[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 0, 0, 0]," +
                "[1, 1, 1, 0, 0, 0, 0]]";
        // @formatter:on
        Board board = Board.createFromJson(input);
        Player player = new Player(PLAYER_ONE_VALUE);

        GameState gameState = new GameState(board, player);

        assertFalse(gameState.isWinner(player));
    }

    @Test
    public void reverseDiagonalPlayerOneWin() {
        // @formatter:off
        String input =
                "[[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 0, 0, 0]," +
                "[1, 0, 0, 1, 0, 0, 0]," +
                "[1, 1, 1, 0, 0, 0, 0]," +
                "[2, 1, 1, 0, 0, 0, 0]," +
                "[1, 2, 2, 1, 0, 2, 0]]";
        // @formatter:on
        checkPlayerOneWin(input);
    }

    @Test
    public void reverseDiagonalInCenter() {
        // @formatter:off
        String input =
                "[[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 1, 0, 0]," +
                "[1, 0, 0, 1, 0, 0, 0]," +
                "[1, 1, 1, 0, 0, 0, 0]," +
                "[2, 1, 1, 0, 0, 0, 0]," +
                "[0, 2, 2, 1, 0, 2, 0]]";
        // @formatter:on
        checkPlayerOneWin(input);
    }

    @Test
    public void reverseDiagonalPlayerTwoWin() {
        // @formatter:off
        String input =
                "[[0, 0, 0, 0, 0, 0, 0]," +
                "[0, 0, 0, 0, 1, 0, 0]," +
                "[1, 0, 0, 1, 0, 0, 2]," +
                "[1, 1, 0, 0, 0, 2, 0]," +
                "[2, 1, 1, 0, 2, 0, 0]," +
                "[0, 2, 2, 2, 0, 2, 0]]";
        // @formatter:on
        checkPlayerTwoWin(input);
    }

    private void checkPlayerOneWin(String jsonInput) {
        GameState gameState = createGameStatePlayerOne(jsonInput);

        assertTrue(gameState.isWinner(new Player(PLAYER_ONE_VALUE)));
    }

    private void checkPlayerTwoWin(String jsonInput) {
        GameState gameState = createGameStatePlayerTwo(jsonInput);

        assertTrue(gameState.isWinner(new Player(PLAYER_TWO_VALUE)));
    }
}
