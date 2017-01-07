package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.HashSet;
import java.util.Set;

import static edu.gvsu.cis.campbjos.connectfour.model.Board.COLUMN_SIZE;
import static edu.gvsu.cis.campbjos.connectfour.model.Board.ROW_SIZE;

public class GameState {

    static final int PIECE_COUNT_TO_WIN = 4;

    private final Board board;
    private final Player currentPlayer;
    private final Player opponent;
    private final int winner;

    GameState(Board board, Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.opponent = Player.nextPlayer(currentPlayer);
        this.winner = checkForWinner();
    }

    public static GameState createFromJson(final String serializedBoard, final String playerValue) {
        return new GameState(
                Board.createFromJson(serializedBoard),
                new Player(playerValue));
    }

    boolean isWinner(Player player) {
        return winner == player.getPiece();
    }

    boolean isLoser(Player player) {
        return winner > 0 && !isWinner(player);
    }

    boolean isOver() {
        return winner > 0;
    }

    public int getMove() {
        return currentPlayer.runMinimax(this);
    }

    /**
     * Check from the bottom of the board to the top
     *
     * @return a list of available spaces
     */
    Set<Integer> getAvailableMoves() {
        Set<Integer> availableMoves = new HashSet<>();
        for (int row = ROW_SIZE - 1; row >= 0; row--) {
            for (int column = 0; column < COLUMN_SIZE; column++) {
                if (board.isOpenSpace(row, column)) {
                    availableMoves.add(column);
                }
            }
        }
        return availableMoves;
    }

    GameState makeMove(int column) {
        Board nextBoard = board.duplicate();
        nextBoard.placePiece(currentPlayer, column);
        GameState gameState = new GameState(nextBoard, opponent);
        return gameState;
    }

    int checkForWinner() {
        if (isVerticalWinner()) {
            return currentPlayer.getPiece();
        } else if (isLoser(currentPlayer)) {
            return opponent.getPiece();
        }
        return 0;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    private boolean isVerticalWinner() {
        int contiguousPieces = 0;
        for (int column = 0; column < COLUMN_SIZE; column++) {
            for (int row = 0; row < ROW_SIZE - 1; row++) {

                int currentPiece = board.at(row, column);
                int playerPiece = currentPlayer.getPiece();

                if (currentPiece != playerPiece) {
                    continue;
                }
                if (contiguousPieces == 0) {
                    contiguousPieces++;
                }
                if (board.at(row + 1, column) == playerPiece) {
                    contiguousPieces++;
                } else {
                    contiguousPieces = 0;
                }
            }
        }
        return contiguousPieces >= PIECE_COUNT_TO_WIN;
    }
}
