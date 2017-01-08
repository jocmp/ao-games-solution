package edu.gvsu.cis.campbjos.connectfour.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

class Board {

    static final int ROW_SIZE = 6;
    static final int COLUMN_SIZE = 7;
    private static final Type GRID_TYPE;

    static {
        // Grid type must be created in order to serialize
        // the Json commandline board
        GRID_TYPE = new TypeToken<List<List<Integer>>>() {
        }.getType();
    }

    private final List<List<Integer>> grid;

    /**
     * Constructor allows for retention of {@link Integer}
     * references without assigning the instance grid to the grid
     * pass in as our argument. While this example is trivial, methods
     * should not be allowed to modify the data they are provided.
     */
    private Board(List<List<Integer>> grid) {
        this.grid = new ArrayList<>(grid.size());
        this.grid.addAll(grid);
    }

    /**
     * Serialize the commandline board using a factory method.
     * Rationale from Joshua Bloch's <em>Effective Java</em>:
     * "One advantage of static factory methods is that, unlike constructors,
     * they have names."
     *
     * @param grid serialized grid in Json format
     * @return board with corresponding grid
     */
    static Board createFromJson(final String grid) {
        // Serialize from type
        return new Board(new Gson().fromJson(grid, GRID_TYPE));
    }

    /**
     * Given a valid coordinate, place piece in grid
     *
     * @param player player with piece
     * @param column requested column
     */
    void placePiece(Player player, int column) {
        for (int row = ROW_SIZE - 1; row >= 0; row--) {
            if (isOpenSpace(row, column)) {
                grid.get(row).set(column, player.getPiece());
                return;
            }
        }
    }

    /**
     * Provides abstraction away from accessing 2D array.
     * In general, further abstraction away from a data structure
     * which is provided here (e.g. {@link #grid}, means more opportunity
     * to swap out the underlying data structure of {@link Board} if needed
     *
     * @param row    access row
     * @param column access column
     * @return Given piece at the coordinate
     * @throws IndexOutOfBoundsException on invalid coordinate
     */
    int at(int row, int column) throws IndexOutOfBoundsException {
        boolean isRowInBounds = row < ROW_SIZE && row >= 0;
        boolean isColumnInBounds = column < COLUMN_SIZE && column >= 0;

        if (isColumnInBounds && isRowInBounds) {
            return grid.get(row).get(column);
        }
        throw new IndexOutOfBoundsException(
                format("row=%s column=%s is out of bounds!", row, column));
    }

    /**
     * @param row    access row
     * @param column access column
     * @return
     * @throws IndexOutOfBoundsException propagate exception from {@link #at}
     */
    boolean isOpenSpace(int row, int column) throws IndexOutOfBoundsException {
        try {
            return at(row, column) == 0;
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(
                    "Space is not open: " + e.getMessage());
        }
    }

    /**
     * Given a board, serialize and return a new board. By serializing
     * the board before returning a new instance, it ensures that the new
     * board contains no references to the old one.
     *
     * @return duplicate board with matching {@link #grid}
     */
    Board duplicate() {
        return createFromJson(new Gson().toJson(grid, GRID_TYPE));
    }

    /**
     * @return a formatted board in the same form
     * as it's input from {@link #createFromJson(String)}
     */
    @Override
    public String toString() {
        String text = "";
        for (int row = 0; row < ROW_SIZE; row++) {
            text += grid.get(row).toString() + "\n";
        }
        return text;
    }
}
