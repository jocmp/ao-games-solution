package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.deepToString;
import static org.junit.Assert.*;

public class BoardTest {

    private int[][] grid;

    @Before
    public void initializeGrid() {
        grid = new int[Board.ROW_SIZE][Board.COLUMN_SIZE];
    }

    @Test
    public void createFromJson() throws Exception {
        String serializedGrid = GridHelper.createEmptyGrid();

        Board board = Board.createFromJson(serializedGrid);

        assertNotNull(board);
    }

    @Test
    public void isEmpty() throws Exception {
        String serializedGrid = deepToString(grid);

        Board board = Board.createFromJson(serializedGrid);

        assertTrue(board.isEmpty());
    }

    @Test
    public void isNotEmpty() throws Exception {
        grid[5][0] = 2;

        Board board = Board.createFromJson(deepToString(grid));

        assertFalse(board.isEmpty());
    }

    @Test
    public void hasEntryAtRow() throws Exception {
        int playerOne = 1;
        grid[5][6] = playerOne;

        Board board = Board.createFromJson(deepToString(grid));

        assertEquals(board.at(5, 6).intValue(), playerOne);
    }

}