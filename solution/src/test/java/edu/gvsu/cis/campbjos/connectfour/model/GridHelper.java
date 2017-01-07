package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.Arrays;

import static edu.gvsu.cis.campbjos.connectfour.model.Board.COLUMN_SIZE;
import static edu.gvsu.cis.campbjos.connectfour.model.Board.ROW_SIZE;

/**
 * Helper class to generate the grids
 * needed to populate a board
 */
class GridHelper {

    static String createEmptyGrid() {
        return Arrays.deepToString(new int[ROW_SIZE][COLUMN_SIZE]);
    }

    static String createFullBoard() {
        int[][] grid = new int[ROW_SIZE][COLUMN_SIZE];
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int column = 0; column < COLUMN_SIZE; column++) {
                grid[row][column] = 1;
            }
        }
        return Arrays.deepToString(grid);
    }
}
