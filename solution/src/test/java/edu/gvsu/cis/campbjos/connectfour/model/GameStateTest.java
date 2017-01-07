package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createEmptyGrid;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class GameStateTest {

    @Test
    public void createValidGameState() throws Exception {
        GameState gameState = GameState.createFromJson("[[]]");

        assertNotNull(gameState);
    }

    @Test
    public void createGameWithEmptyBoard() throws Exception {
        GameState gameState = GameState.createFromJson(createEmptyGrid());

        assertTrue(gameState.getBoard().isEmpty());
    }


}