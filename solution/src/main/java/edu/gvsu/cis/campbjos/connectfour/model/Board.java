package edu.gvsu.cis.campbjos.connectfour.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Board {

    private static final Type GRID_TYPE;

    static {
        GRID_TYPE = new TypeToken<List<List<Integer>>>() {
        }.getType();
    }

    public static final int COLUMN_SIZE = 7;
    public static final int ROW_SIZE = 6;

    private final List<List<Integer>> grid;

    private Board(List<List<Integer>> grid) {
        this.grid = new ArrayList<>(grid.size());
        this.grid.addAll(grid);
    }

    public static Board createFromJson(final String grid) {
        // Serialize from type;
        return new Board(new Gson().fromJson(grid, GRID_TYPE));
    }

    public boolean isEmpty() {
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int column = 0; column < COLUMN_SIZE; column++) {
                if (!at(row, column).equals(0)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Integer at(int row, int column) throws IndexOutOfBoundsException {
        boolean isColumnInBounds = column < COLUMN_SIZE && column >= 0;
        boolean isRowInBounds = row < ROW_SIZE && column >= 0;

        if (!(isColumnInBounds && isRowInBounds)) {
            throw new IndexOutOfBoundsException(
                    format("column=%s row=%s is out of bounds!", column, row));
        }

        return grid.get(row).get(column);
    }
}
