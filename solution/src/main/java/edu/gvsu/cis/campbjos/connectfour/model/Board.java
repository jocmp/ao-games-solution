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
        GRID_TYPE = new TypeToken<List<List<Integer>>>() {
        }.getType();
    }

    private final List<List<Integer>> grid;

    private Board(List<List<Integer>> grid) {
        this.grid = new ArrayList<>(grid.size());
        this.grid.addAll(grid);
    }

    static Board createFromJson(final String grid) {
        return new Board(new Gson().fromJson(grid, GRID_TYPE));
    }

    void placePiece(Player player, int column) {
        for (int row = ROW_SIZE - 1; row >= 0; row--) {
            if (isOpenSpace(row, column)) {
                grid.get(row).set(column, player.getPiece());
                return;
            }
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

    boolean isOpenSpace(int row, int column) throws IndexOutOfBoundsException {
        try {
            return at(row, column) == 0;
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Space is not open: " + e.getMessage());
        }
    }

    Board duplicate() {
        return createFromJson(new Gson().toJson(grid, GRID_TYPE));
    }

    @Override
    public String toString() {
        String text = "";
        for (int row = 0; row < ROW_SIZE; row++) {
            text += grid.get(row).toString() + "\n";
        }
        return text;
    }
}
