package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerBestAttemptTest {

    private static final int THREE_CONTIGUOUS_PIECES = 3;
    private static final int WINNING_MOVE = 10;

    private Player playerOne;

    @Before
    public void setUp() throws Exception {
        playerOne = new Player(Player.PLAYER_ONE_VALUE);
    }

    @Test
    public void updateWithNonZeroValue() {
        final int twoContiguousPieces = 2;
        playerOne.updateBestPossibleMove(twoContiguousPieces);

        assertEquals(playerOne.getBestAttempt(), twoContiguousPieces);
    }

    @Test
    public void updateWithLowerValue() {
        playerOne.updateBestPossibleMove(WINNING_MOVE);
        playerOne.updateBestPossibleMove(THREE_CONTIGUOUS_PIECES);

        assertEquals(playerOne.getBestAttempt(), WINNING_MOVE);
    }

    @Test
    public void updateWithHigherValue() {
        playerOne.updateBestPossibleMove(THREE_CONTIGUOUS_PIECES);
        playerOne.updateBestPossibleMove(WINNING_MOVE);

        assertEquals(playerOne.getBestAttempt(), WINNING_MOVE);
    }

}
