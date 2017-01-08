package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.max;

class Player {

    private static final int STATIC_DEPTH = 5;

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
        if (game.isOver() || depth == STATIC_DEPTH) {
            return getScore(game, depth);
        }

        int nextDepth = depth + 1;
        boolean isMaximizing = game.getCurrentPlayer().getPiece() == piece;
        List<Result> possibleResults = new ArrayList<>();

        if (isMaximizing) {
            int alpha = lowerBound;
            for (int move : game.getAvailableMoves()) {
                int score = runMinimax(game.getChildState(move), nextDepth, alpha, upperBound);
                possibleResults.add(new Result(score, move));
                if (score > alpha) {
                    alpha = score;
                }
                if (alpha > upperBound) {
                    return upperBound;
                }
            }
            bestPossibleMove = max(possibleResults, (lhs, rhs) -> lhs.score - rhs.score).move;
            return alpha;
        } else {
            int beta = upperBound;
            for (int move : game.getAvailableMoves()) {
                int score = runMinimax(game.getChildState(move), nextDepth, lowerBound, beta);
                if (score < beta) {
                    beta = score; // minimum upper bound
                }
                if (beta < lowerBound) {
                    return lowerBound;
                }
            }
            return beta;
        }
    }

    int getBestPossibleMove() {
        return bestPossibleMove;
    }

    private int getScore(final GameState game, int depth) {
        int score = game.maximumPieceCount;
        if (game.isOver()) {
            int winner = game.getWinner();
            if (winner == piece) {
                score = 10 - depth;
            } else if (winner != GameState.DRAW) {
                score = depth - 10;
            } else {
                score = 0;
            }
        }
        return score;
    }

    int getPiece() {
        return piece;
    }
}
