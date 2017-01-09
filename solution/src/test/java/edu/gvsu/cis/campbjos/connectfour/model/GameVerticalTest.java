package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static edu.gvsu.cis.campbjos.connectfour.model.GameState.PIECE_COUNT_TO_WIN;
import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createEmptyGrid;
import static edu.gvsu.cis.campbjos.connectfour.model.Player.PLAYER_ONE_VALUE;
import static org.junit.Assert.assertFalse;

public class GameVerticalTest {

    private static final long TIMEOUT_FOUR_MOVES = 12000 * PIECE_COUNT_TO_WIN;

    @Test(timeout = TIMEOUT_FOUR_MOVES)
    public void playGameToVerticalWin() {
        final Board board = Board.createFromJson(createEmptyGrid());
        final Player playerOne = new Player(PLAYER_ONE_VALUE);
        final Player playerTwo = Player.nextPlayer(playerOne);
        final int firstColumn = 0;

        GameState gameState;

        for (int move = 0; move < PIECE_COUNT_TO_WIN; move++) {
            gameState = new GameState(board, playerOne);
            int playerOneMove = gameState.makeMove();
            board.placePiece(playerOne, playerOneMove);

            board.placePiece(playerTwo, firstColumn);
        }

        gameState = new GameState(board, playerOne);
        
        assertFalse(gameState.isWinner(playerTwo));
    }

}
