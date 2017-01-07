package edu.gvsu.cis.campbjos.connectfour.model;

import java.util.Arrays;

/**
 * Helper class to generate the grids
 * needed to populate a board
 */
public class GridHelper {

    protected static String createEmptyGrid() {
        return Arrays.deepToString(new int[6][7]);
    }

}
