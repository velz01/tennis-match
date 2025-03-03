package org.velz.tennismatch.model;

public class MatchScore {
    private static final int INDEX_PLAYER1 = 0;
    private static final int INDEX_PLAYER2 = 1;

    private final int[] score;
    private final int[] games;
    private final int[] sets;

    public MatchScore() {
        this.score = new int[]{0,0};
        this.games = new int[]{0,0};
        this.sets = new int[]{0,0};
    }

    public int getScorePlayer1() {
        return score[INDEX_PLAYER1];
    }

    public int getScorePlayer2() {
        return score[INDEX_PLAYER2];
    }
}
