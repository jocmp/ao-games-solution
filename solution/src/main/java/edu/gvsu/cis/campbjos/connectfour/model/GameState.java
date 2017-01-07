package edu.gvsu.cis.campbjos.connectfour.model;

public class GameState {

    private Board board;

    private GameState(Board board) {
        this.board = board;
    }

    public static GameState createFromJson(final String serializedBoard) {
        String string = serializedBoard;
        return new GameState(Board.createFromJson(serializedBoard));
    }

    public Board getBoard() {
        return board;
    }
}
