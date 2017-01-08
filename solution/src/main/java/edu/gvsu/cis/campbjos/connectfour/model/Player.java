package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Collections.max;
import static java.util.Map.Entry.comparingByKey;

public class Player {

    static final String PLAYER_ONE_VALUE = "player-one";
    static final String PLAYER_TWO_VALUE = "player-two";

    static final int PLAYER_ONE_PIECE = 1;
    static final int PLAYER_TWO_PIECE = 2;

    private final int piece;

    private int bestPossibleMove;

    Player(String playerValue) throws IllegalArgumentException {
        switch (playerValue) {
            case PLAYER_ONE_VALUE:
                piece = PLAYER_ONE_PIECE;
                break;
            case PLAYER_TWO_VALUE:
                piece = PLAYER_TWO_PIECE;
                break;
            default:
                throw new IllegalArgumentException(
                        format("New player value must be either \"%s\" or \"%s\"",
                                PLAYER_ONE_VALUE, PLAYER_TWO_VALUE));
        }
    }

    static Player nextPlayer(final Player currentPlayer) {
        if (currentPlayer.getPiece() == 1) {
            return new Player(PLAYER_TWO_VALUE);
        }
        return new Player(PLAYER_ONE_VALUE);
    }

    int runMinimax(final GameState game, final int depth,
                   final int lowerBound, final int upperBound) {
        if (game.isOver() || depth == 0) {
            return getScore(game);
        }
        int nextDepth = depth - 1;

        Map<Integer, Integer> possibleMoves = new HashMap<>();
        int currentMin = lowerBound;
        int currentMax = upperBound;
        for (int move : game.getAvailableMoves()) {
            int score = runMinimax(game.getChildState(move), nextDepth, currentMin, currentMax);
            if (game.getCurrentPlayer().getPiece() == piece) {
                possibleMoves.put(score, move);
                if (score > currentMin) {
                    currentMin = score;
                }
            } else {
                if (score < currentMax) {
                    currentMax = score;
                }
            }
            boolean isDiverged = currentMax < currentMin;
            if (isDiverged) {
                break;
            }
        }
        boolean isMinimizing = game.getCurrentPlayer().getPiece() != piece;
        if (isMinimizing) {
            return currentMax;
        }
        bestPossibleMove = max(possibleMoves.entrySet(), comparingByKey()).getValue();
        return currentMin;
    }

    int getBestPossibleMove() {
        return bestPossibleMove;
    }

    private int getScore(final GameState game) {
        if (game.isOver()) {
            int winner = game.getWinner();
            if (winner == 1) {
                return Integer.MAX_VALUE;
            }
            if (winner == 2) {
                return Integer.MIN_VALUE;
            }
            return 0;
        }
        return game.getAvailableMoves().size() - 1;
    }

    int getPiece() {
        return piece;
    }
}
