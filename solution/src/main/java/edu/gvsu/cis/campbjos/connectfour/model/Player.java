package edu.gvsu.cis.campbjos.connectfour.model;

import static java.lang.String.format;

public class Player {

    private static final String PLAYER_ONE_VALUE = "player-one";
    private static final String PLAYER_TWO_VALUE = "player-two";

    private final int number;

    public Player(String playerValue) throws IllegalArgumentException {
        switch (playerValue) {
            case PLAYER_ONE_VALUE:
                number = 1;
                break;
            case PLAYER_TWO_VALUE:
                number = 2;
                break;
            default:
                throw new IllegalArgumentException(
                        format("New player value must be either \"%s\" or \"%s\"",
                                PLAYER_ONE_VALUE, PLAYER_TWO_VALUE));
        }
    }

    int getNumber() {
        return number;
    }
}
