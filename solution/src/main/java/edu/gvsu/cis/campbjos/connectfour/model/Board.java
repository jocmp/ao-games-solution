package edu.gvsu.cis.campbjos.connectfour.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Board {

    static final int ROW_SIZE = 6;
    static final int COLUMN_SIZE = 7;
    private static final Type GRID_TYPE;

    static {
        GRID_TYPE = new TypeToken<List<List<Integer>>>() {
        }.getType();
    }

    private final List<List<Integer>> grid;

    private Board(List<List<Integer>> grid) {
        this.grid = new ArrayList<>(grid.size());
        this.grid.addAll(grid);
    }

    static Board createFromJson(final String grid) {
        // Serialize from type;
        return new Board(new Gson().fromJson(grid, GRID_TYPE));
    }

    boolean isEmpty() {
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int column = 0; column < COLUMN_SIZE; column++) {
                if (!isOpenSpace(row, column)) {
                    return false;
                }
            }
        }
        return true;
    }

    void placePiece(Player player, int row, int column) {
        if (isOpenSpace(row, column)) {
            grid.get(row).set(column, player.getNumber());
        }
    }

    int at(int row, int column) throws IndexOutOfBoundsException {
        boolean isRowInBounds = row < ROW_SIZE && row >= 0;
        boolean isColumnInBounds = column < COLUMN_SIZE && column >= 0;

        if (isColumnInBounds && isRowInBounds) {
            return grid.get(row).get(column);
        }
        throw new IndexOutOfBoundsException(
                format("row=%s column=%s is out of bounds!", row, column));
    }

    boolean isOpenSpace(int row, int column) {
        return at(row, column) == 0;
    }
}
