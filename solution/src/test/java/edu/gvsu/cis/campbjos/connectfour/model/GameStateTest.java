package edu.gvsu.cis.campbjos.connectfour.model;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createEmptyGrid;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class GameStateTest {


    private Gson gson;

    @Before
    public void setUp() {
        gson = new Gson();
    }

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