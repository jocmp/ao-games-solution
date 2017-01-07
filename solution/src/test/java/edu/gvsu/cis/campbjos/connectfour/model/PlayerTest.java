package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private static final String PLAYER_ONE_ARG = "player-one";
    private static final String PLAYER_TWO_ARG = "player-two";

    @Test
    public void createPlayerOne() {
        Player playerOne = new Player(PLAYER_ONE_ARG);

        assertEquals(playerOne.getNumber(), 1);
    }

    @Test
    public void createPlayerTwo() {
        Player playerTwo = new Player(PLAYER_TWO_ARG);

        assertEquals(playerTwo.getNumber(), 2);
    }

    @Test
    public void createIllegalPlayer() {
        String expectedMessage = "New player value must be either \"player-one\" or \"player-two\"";
        String actualErrorMessage = null;
        try {
            new Player("stowaway");
        } catch (IllegalArgumentException e) {
            actualErrorMessage = e.getMessage();
        }
        assertEquals(expectedMessage, actualErrorMessage);
    }

    @Test
    public void createOpponentPlayerOne() {
        createOpponentForPlayer(PLAYER_ONE_ARG, 2);
    }

    @Test
    public void createOpponentPlayerTwo() {
        createOpponentForPlayer(PLAYER_TWO_ARG, 1);
    }

    private void createOpponentForPlayer(String playerArg, int expectedOpponentNumber) {
        Player currentPlayer = new Player(playerArg);

        Player opponent = Player.createOpponent(currentPlayer);

        assertEquals(opponent.getNumber(), expectedOpponentNumber);
    }
}