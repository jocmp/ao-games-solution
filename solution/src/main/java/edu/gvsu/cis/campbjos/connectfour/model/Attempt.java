package edu.gvsu.cis.campbjos.connectfour.model;

import static edu.gvsu.cis.campbjos.connectfour.model.GameState.PIECE_COUNT_TO_WIN;

class Attempt {

    final boolean isWin;
    final int piece;
    final int contiguousCount;

    Attempt(int piece, int contiguousCount) {
        isWin = contiguousCount >= PIECE_COUNT_TO_WIN;
        this.piece = piece;
        this.contiguousCount = contiguousCount;
    }
}
