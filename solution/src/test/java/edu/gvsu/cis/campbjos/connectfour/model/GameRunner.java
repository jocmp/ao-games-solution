package edu.gvsu.cis.campbjos.connectfour.model;

import static edu.gvsu.cis.campbjos.connectfour.model.GameState.PIECE_COUNT_TO_WIN;

public class GameRunner {

    static final long TIMEOUT_FOUR_MOVES = 12000 * PIECE_COUNT_TO_WIN;

    static Board playOnBoard(Board board, Player humanPlayer, int column) {
        Board nextBoard = board.duplicate();

        Player playerOne = new Player(Player.PLAYER_ONE_VALUE);
        GameState gameState = new GameState(nextBoard, playerOne);

        int playerOneMove = gameState.makeMove();

        board.placePiece(playerOne, playerOneMove);
        board.placePiece(humanPlayer, column);

        return nextBoard;
    }
}
