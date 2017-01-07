package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Map.Entry.comparingByValue;

public class Player {

    private static final String PLAYER_ONE_VALUE = "player-one";
    private static final String PLAYER_TWO_VALUE = "player-two";

    private final int piece;

    Player(String playerValue) throws IllegalArgumentException {
        switch (playerValue) {
            case PLAYER_ONE_VALUE:
                piece = 1;
                break;
            case PLAYER_TWO_VALUE:
                piece = 2;
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

    int runMinimax(GameState game) {
        if (game.isOver()) {
            return score(game);
        }
        Map<Integer, Integer> scores = new HashMap<>();
        Set<Integer> availableMoves = game.getAvailableMoves();

        for (Integer move : availableMoves) {
            final GameState availableGameState = game.makeMove(move);
            scores.put(move, runMinimax(availableGameState));
        }
        if (game.getCurrentPlayer().getPiece() == getPiece()) {
            return Collections.max(scores.entrySet(), comparingByValue()).getKey();
        } else {
            return Collections.min(scores.entrySet(), comparingByValue()).getKey();
        }
    }

    int score(GameState gameState) {
        if (gameState.isWinner(this)) {
            return 10;
        }
        if (gameState.isLoser(this)) {
            return 10;
        }
        return 0;
    }

    int getPiece() {
        return piece;
    }
}
