package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Before;
import org.junit.Test;

import static edu.gvsu.cis.campbjos.connectfour.model.GridHelper.createEmptyGrid;
import static java.util.Arrays.deepToString;
import static org.junit.Assert.*;

public class BoardTest {

    private int[][] grid;

    @Before
    public void setUp() throws Exception {
        grid = new int[Board.ROW_SIZE][Board.COLUMN_SIZE];
    }

    @Test
    public void createFromJson() throws Exception {
        String serializedGrid = createEmptyGrid();

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

        assertEquals(board.at(5, 6), playerOne);
    }

    @Test
    public void checkAtInvalidRowLowerBound() {
        int row = -1;
        int column = 0;

        checkAtInvalidIndex(row, column);
    }

    @Test
    public void checkAtInvalidColumnLowerBound() {
        int row = 0;
        int column = -1;

        checkAtInvalidIndex(row, column);
    }

    @Test
    public void checkAtInvalidRowUpperBound() {
        int row = Board.ROW_SIZE + 1;
        int column = Board.COLUMN_SIZE - 1;

        checkAtInvalidIndex(row, column);
    }

    @Test
    public void checkAtInvalidColumnUpperBound() {
        int row = Board.ROW_SIZE - 1;
        int column = Board.COLUMN_SIZE + 1;

        checkAtInvalidIndex(row, column);
    }


    private void checkAtInvalidIndex(int row, int column) {
        Board board = Board.createFromJson(createEmptyGrid());
        String expectedMessage = String.format("row=%s column=%s is out of bounds!", row, column);
        String actualErrorMessage = null;

        try {
            board.at(row, column);
        } catch (IndexOutOfBoundsException e) {
            actualErrorMessage = e.getMessage();
        }

        assertEquals(expectedMessage, actualErrorMessage);
    }


}