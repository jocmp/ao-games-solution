package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void createPlayerOne() {
        Player playerOne = new Player("player-one");

        assertEquals(playerOne.getNumber(), 1);
    }

    @Test
    public void createPlayerTwo() {
        Player playerTwo = new Player("player-two");

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
}