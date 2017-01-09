package edu.gvsu.cis.campbjos.connectfour.model;

import static edu.gvsu.cis.campbjos.connectfour.model.Player.PLAYER_ONE_VALUE;
import static edu.gvsu.cis.campbjos.connectfour.model.Player.PLAYER_TWO_VALUE;

public class GameStateFactory {

    static GameState createGameStatePlayerOne(String jsonInput) {
        return createGameState(PLAYER_ONE_VALUE, jsonInput);
    }

    static GameState createGameStatePlayerTwo(String jsonInput) {
        return createGameState(PLAYER_TWO_VALUE, jsonInput);
    }

    private static GameState createGameState(String playerValue, String jsonInput) {
        Board board = Board.createFromJson(jsonInput);
        Player player = new Player(playerValue);
        return new GameState(board, player);
    }
}
