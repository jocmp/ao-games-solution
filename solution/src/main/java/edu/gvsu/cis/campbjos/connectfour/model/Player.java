package edu.gvsu.cis.campbjos.connectfour.model;

import static java.lang.String.format;

public class Player {

    private static final String PLAYER_ONE_VALUE = "player-one";
    private static final String PLAYER_TWO_VALUE = "player-two";

    public static final int PLAYER_ONE_PIECE = 1;
    public static final int PLAYER_TWO_PIECE = 2;

    private final int piece;

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

    int runMinimax(final GameState game, final int depth, int minScore, int maxScore) {
        return 0;
    }

    private int getScore(final GameState gameState, final int depth) {
        return 0;
    }

    int getPiece() {
        return piece;
    }
}
