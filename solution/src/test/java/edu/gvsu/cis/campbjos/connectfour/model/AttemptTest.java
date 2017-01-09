package edu.gvsu.cis.campbjos.connectfour.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AttemptTest {

    @Test
    public void createWinningAttempt() {
        int contiguousCount = 4;

        Attempt attempt = createAttempt(contiguousCount);

        assertTrue(attempt.isWin);
    }

    @Test
    public void createWinningAttemptWithLargeCount() {
        int contiguousCount = 6;

        Attempt attempt = createAttempt(contiguousCount);

        assertTrue(attempt.isWin);
    }

    @Test
    public void createInProgressAttempt() {
        int contiguousCount = 2;

        Attempt attempt = createAttempt(contiguousCount);

        assertFalse(attempt.isWin);
    }

    private Attempt createAttempt(int contiguousCount) {
        int piece = 1;

        return new Attempt(piece, contiguousCount);
    }
}
