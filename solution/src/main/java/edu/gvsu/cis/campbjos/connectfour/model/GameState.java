package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static edu.gvsu.cis.campbjos.connectfour.model.Board.COLUMN_SIZE;
import static edu.gvsu.cis.campbjos.connectfour.model.Board.ROW_SIZE;
import static edu.gvsu.cis.campbjos.connectfour.model.Player.PLAYER_ONE_PIECE;
import static edu.gvsu.cis.campbjos.connectfour.model.Player.PLAYER_TWO_PIECE;

public class GameState {

    static final int PIECE_COUNT_TO_WIN = 4;
    private static final int DRAW = 3;
    private static final int STATIC_DEPTH = 5;

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

    /**
     *
     * @param serializedBoard
     * @param playerValue
     * @return
     */
    public static GameState createFromJson(final String serializedBoard, final String playerValue) {
        return new GameState(
                Board.createFromJson(serializedBoard),
                new Player(playerValue));
    }

    /**
     * @return Integer representation of players.
     * "3" represents a draw
     */
    private int checkForWinner() {
        List<Integer> wins = new ArrayList<>(4);

        boolean isPlayerOneWinner = false;
        boolean isPlayerTwoWinner = false;

        wins.add(checkVerticalWin());
        wins.add(checkHorizontalWin());
        wins.add(checkForwardDiagonalWin());
        wins.add(checkReverseDiagonalWin());

        for (int win : wins) {
            switch (win) {
                case PLAYER_ONE_PIECE:
                    isPlayerOneWinner = true;
                    break;
                case PLAYER_TWO_PIECE:
                    isPlayerTwoWinner = true;
                    break;
                default:
                    break;
            }
        }
        if (isPlayerOneWinner) {
            return PLAYER_ONE_PIECE;
        }
        if (isPlayerTwoWinner) {
            return PLAYER_TWO_PIECE;
        }
        if (isBoardFull()) {
            return DRAW;
        }

        return 0;
    }

    boolean isWinner(Player player) {
        return winner == player.getPiece();
    }

    boolean isOver() {
        return winner > 0;
    }


    private boolean isBoardFull() {
        return getAvailableMoves().isEmpty();
    }



    public int getMove() {
        return currentPlayer.runMinimax(this, STATIC_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
        return new GameState(nextBoard, opponent);
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    private int checkVerticalWin() {
        int contiguousPieces = 0;
        for (int row = 0; row < ROW_SIZE - 1; row++) {
            for (int column = 0; column < COLUMN_SIZE; column++) {
                int currentPiece = board.at(row, column);
                int nextPiece = board.at(row + 1, column);

                contiguousPieces = getSumOfPieces(contiguousPieces, currentPiece, nextPiece);

                if (contiguousPieces >= PIECE_COUNT_TO_WIN) {
                    return currentPiece;
                }
            }
        }
        return 0;
    }

    private int checkHorizontalWin() {
        int contiguousPieces = 0;
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int column = 0; column < COLUMN_SIZE - 1; column++) {
                int currentPiece = board.at(row, column);
                int nextPiece = board.at(row, column + 1);
                contiguousPieces = getSumOfPieces(contiguousPieces,
                        currentPiece,
                        nextPiece);

                if (contiguousPieces >= PIECE_COUNT_TO_WIN) {
                    return currentPiece;
                }
            }
        }

        return 0;
    }

    private int checkForwardDiagonalWin() {
        return checkDiagonalWin(true);
    }

    private int checkReverseDiagonalWin() {
        return checkDiagonalWin(false);
    }

    private int checkDiagonalWin(boolean includeForwardOffset) {
        // At most, there are 13 diagonals to iterate over
        int diagonalSum = ROW_SIZE + COLUMN_SIZE - 1;
        for (int diagonalSlice = 0; diagonalSlice < diagonalSum; diagonalSlice++) {

            int startOffset = getDiagonalStartOffset(diagonalSlice);
            int endOffset = getDiagonalEndOffset(diagonalSlice);

            int accessRow = diagonalSlice - startOffset;

            List<Integer> diagonalPieces = new ArrayList<>();
            while (accessRow >= endOffset) {
                int row;
                if (includeForwardOffset) {
                    row = ROW_SIZE - accessRow - 1;
                } else {
                    row = accessRow;
                }

                int column = diagonalSlice - accessRow;
                diagonalPieces.add(board.at(row, column));
                accessRow--;
            }
            int size = diagonalPieces.size();

            if (size < PIECE_COUNT_TO_WIN) {
                continue;
            }
            int contiguousPieces = 0;
            for (int index = 0; index < size - 1; index++) {
                int piece = diagonalPieces.get(index);
                int nextPiece = diagonalPieces.get(index + 1);
                contiguousPieces = getSumOfPieces(contiguousPieces, piece, nextPiece);

                if (contiguousPieces >= PIECE_COUNT_TO_WIN) {
                    return piece;
                }
            }
        }

        return 0;
    }

    private int getDiagonalStartOffset(int diagonalSlice) {
        if (diagonalSlice < ROW_SIZE) {
            return 0;
        }
        return diagonalSlice - ROW_SIZE + 1;
    }

    private int getDiagonalEndOffset(int diagonalSlice) {
        if (diagonalSlice < COLUMN_SIZE) {
            return 0;
        }
        return diagonalSlice - COLUMN_SIZE + 1;
    }

    private int getSumOfPieces(final int runningTotal,
                               final int currentPiece, final int nextPiece) {
        int contiguousPieces = runningTotal;
        int playerPiece = currentPlayer.getPiece();

        if (currentPiece == 0) {
            return contiguousPieces;
        }
        if (contiguousPieces == 0) {
            contiguousPieces++;
        }
        if (nextPiece == playerPiece) {
            contiguousPieces++;
        } else {
            contiguousPieces = 0;
        }

        return contiguousPieces;
    }
}
