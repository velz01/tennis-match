package org.velz.tennismatch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.velz.tennismatch.enums.EPlayer;
import org.velz.tennismatch.enums.Points;
import org.velz.tennismatch.model.GameScore;

import static org.junit.jupiter.api.Assertions.*;

public class GameScoreTest {
    private GameScore gameScore;


    @BeforeEach
    public void prepare() {
        this.gameScore = new GameScore();
    }


    @Test
    @DisplayName("game is not finished when player1 win point when tie")
    public void winPointWhenTie() {
        makeTie();

        gameScore.increasePoints(EPlayer.PLAYER1);

        assertFalse(gameScore.winnerExists());
    }

    @Test
    @DisplayName("game is finished when player2 win 2 points when tie")
    public void winPointsWhenTie() {
        makeTie();

        gameScore.increasePoints(EPlayer.PLAYER2);
        gameScore.increasePoints(EPlayer.PLAYER2);

        assertTrue(gameScore.winnerExists());
    }

    private void makeTie() {
        Points[] score = gameScore.getScore();
        score[EPlayer.PLAYER1.getIndexPlayer()] = Points.THIRTY;
        score[EPlayer.PLAYER2.getIndexPlayer()] = Points.THIRTY;
        gameScore.increasePoints(EPlayer.PLAYER1);
        gameScore.increasePoints(EPlayer.PLAYER2);
    }

    @Test
    @DisplayName("game is finished when player1 win points when score is 40-0")
    public void increaseScoreWhenScoreIsForty() {
        Points[] score = gameScore.getScore();
        score[EPlayer.PLAYER1.getIndexPlayer()] = Points.FORTY;

        gameScore.increasePoints(EPlayer.PLAYER1);

        assertTrue(gameScore.winnerExists());
    }




}
