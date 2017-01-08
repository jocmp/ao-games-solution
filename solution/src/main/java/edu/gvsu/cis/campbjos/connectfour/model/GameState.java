package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.*;

import static edu.gvsu.cis.campbjos.connectfour.model.Board.COLUMN_SIZE;
import static edu.gvsu.cis.campbjos.connectfour.model.Board.ROW_SIZE;

public class GameState {

    static final int PIECE_COUNT_TO_WIN = 4;
    static final int DRAW = 3;

    private final Board board;
    private final Player currentPlayer;
    private final Player opponent;
    private final int winner;
    final int maximumPieceCount;

    private Set<Integer> availableMoves;

    GameState(Board board, Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.opponent = Player.nextPlayer(currentPlayer);
        Attempt attempt = checkForWinner();
        boolean hasWinner = attempt.contiguousCount >= PIECE_COUNT_TO_WIN;
        boolean isInconclusive = attempt.piece == 0 || attempt.piece == DRAW;
        if (hasWinner || isInconclusive) {
            winner = attempt.piece;
        } else {
            winner = 0;
        }
        maximumPieceCount = attempt.contiguousCount;
    }

    public static GameState createFromJson(final String serializedBoard, final String playerValue) {
        return new GameState(
                Board.createFromJson(serializedBoard),
                new Player(playerValue));
    }

    private Attempt checkForWinner() {
        List<Integer> wins = new ArrayList<>(4);
        List<Attempt> attempts = new ArrayList<>();

        attempts.add(checkVerticalWin());
        attempts.add(checkHorizontalWin());
        attempts.add(checkForwardDiagonalWin());
        attempts.add(checkReverseDiagonalWin());

        Optional<Attempt> winningAttempt =
                attempts.stream()
                        .filter(attempt -> attempt.isWin)
                        .findFirst();

        if (winningAttempt.isPresent()) {
            return winningAttempt.get();
        }
        if (isBoardFull()) {
            return new Attempt(DRAW, 0);
        }

        Optional<Attempt> intermediateAttempt =
                attempts.stream().filter(attempt -> attempt.piece == currentPlayer.getPiece())
                        .findFirst();
        if (intermediateAttempt.isPresent()) {
            return intermediateAttempt.get();
        } else {
            return new Attempt(0, 0);
        }
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

    int getWinner() {
        return winner;
    }

    public int getMove() {
        currentPlayer.runMinimax(this, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int bestMove = currentPlayer.getBestPossibleMove();
        if (!getAvailableMoves().contains(bestMove)) {
            return new ArrayList<>(getAvailableMoves()).get(0);
        }
        return bestMove;
    }

    /**
     * Check from the bottom of the board to the top
     *
     * @return a list of available spaces
     */
    Set<Integer> getAvailableMoves() {
        if (availableMoves != null) {
            return availableMoves;
        }
        availableMoves = new HashSet<>();
        for (int row = ROW_SIZE - 1; row >= 0; row--) {
            for (int column = 0; column < COLUMN_SIZE; column++) {
                if (board.isOpenSpace(row, column)) {
                    availableMoves.add(column);
                }
            }
        }
        return availableMoves;
    }

    GameState getChildState(int column) {
        Board nextBoard = board.duplicate();
        nextBoard.placePiece(currentPlayer, column);
        return new GameState(nextBoard, opponent);
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    private Attempt checkVerticalWin() {
        return checkStraightLineWin(false);
    }

    private Attempt checkHorizontalWin() {
        return checkStraightLineWin(true);
    }

    private Attempt checkStraightLineWin(boolean isHorizontal) {
        int contiguousPieces = 0;
        int pieceCountAtMost = 0;
        int rowBound;
        int columnBound;
        if (isHorizontal) {
            rowBound = ROW_SIZE;
            columnBound = COLUMN_SIZE - 1;
        } else {
            rowBound = ROW_SIZE - 1;
            columnBound = COLUMN_SIZE;
        }
        for (int row = 0; row < rowBound; row++) {
            for (int column = 0; column < columnBound; column++) {
                int currentPiece = board.at(row, column);
                int nextPiece;
                if (isHorizontal) {
                    nextPiece = board.at(row, column + 1);
                } else {
                    nextPiece = board.at(row + 1, column);
                }
                int nextSum = getSumOfPieces(contiguousPieces,
                        currentPiece,
                        nextPiece);
                if (currentPiece != 0) {
                    pieceCountAtMost = updatePieceCountAtMost(pieceCountAtMost, nextSum, contiguousPieces);
                }
                contiguousPieces = nextSum;

                if (contiguousPieces >= PIECE_COUNT_TO_WIN) {
                    return new Attempt(currentPlayer.getPiece(), contiguousPieces);
                }
            }
        }

        return new Attempt(currentPlayer.getPiece(), pieceCountAtMost);
    }

    private Attempt checkForwardDiagonalWin() {
        return checkDiagonalWin(true);
    }

    private Attempt checkReverseDiagonalWin() {
        return checkDiagonalWin(false);
    }

    private Attempt checkDiagonalWin(boolean includeForwardOffset) {
        int diagonalSum = ROW_SIZE + COLUMN_SIZE - 1;
        int pieceCountAtMost = 0;
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
                int nextSum = getSumOfPieces(contiguousPieces, piece, nextPiece);
                if (piece != 0) {
                    pieceCountAtMost = updatePieceCountAtMost(pieceCountAtMost, nextSum, contiguousPieces);
                }
                contiguousPieces = nextSum;

                if (contiguousPieces >= PIECE_COUNT_TO_WIN) {
                    return new Attempt(currentPlayer.getPiece(), contiguousPieces);
                }
            }
        }

        return new Attempt(currentPlayer.getPiece(), pieceCountAtMost);
    }

    private int updatePieceCountAtMost(final int pieceCountAtMost, final int nextSum, final int
            contiguousPieces) {
        if (nextSum == 0 && contiguousPieces > 0) {
            if (contiguousPieces > pieceCountAtMost) {
                return contiguousPieces;
            }
        }
        return pieceCountAtMost;
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
