package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.HashSet;
import java.util.Set;

import static edu.gvsu.cis.campbjos.connectfour.model.Board.COLUMN_SIZE;
import static edu.gvsu.cis.campbjos.connectfour.model.Board.ROW_SIZE;

public class GameState {

    private Board board;

    private GameState(Board board) {
        this.board = board;
    }

    public static GameState createFromJson(final String serializedBoard) {
        return new GameState(Board.createFromJson(serializedBoard));
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Check from the bottom of the board to the top
     *
     * @return a list of available spaces
     */
    Set<Integer> getAvailableMoves() {
        int lastRow = ROW_SIZE - 1;
        Set<Integer> availableMoves = new HashSet<>();
        for (int row = ROW_SIZE; row >= 0; row--) {
            for (int column = 0; column < COLUMN_SIZE; column++) {
                if (board.isOpenSpace(row, column)) {
                    availableMoves.add(column);
                }
            }
        }
        return availableMoves;
    }
}
