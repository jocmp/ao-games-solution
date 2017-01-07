package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.HashSet;
import java.util.Set;

import static edu.gvsu.cis.campbjos.connectfour.model.Board.COLUMN_SIZE;
import static edu.gvsu.cis.campbjos.connectfour.model.Board.ROW_SIZE;

class GameState {

    private final Board board;
    private final Player artificialPlayer;
    private final Player opponent;


    GameState(Board board, Player currentPlayer) {
        this.board = board;
        this.artificialPlayer = currentPlayer;
        this.opponent = Player.createOpponent(currentPlayer);
    }

    static GameState createFromJson(final String serializedBoard, final String playerValue) {
        return new GameState(
                Board.createFromJson(serializedBoard),
                new Player(playerValue));
    }

    Board getBoard() {
        return board;
    }

    /**
     * Check from the bottom of the board to the top
     *
     * @return a list of available spaces
     */
    Set<Integer> getAvailableMoves() {
        Set<Integer> availableMoves = new HashSet<>();
        for (int row = ROW_SIZE - 1; row >= 0; row--) {
            for (int column = 0; column < COLUMN_SIZE; column++) {
                if (board.isOpenSpace(row, column)) {
                    availableMoves.add(column);
                }
            }
        }
        return availableMoves;
    }

//    private boolean checkWinnerVertical
}
