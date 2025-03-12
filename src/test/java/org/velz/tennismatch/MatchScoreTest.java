package org.velz.tennismatch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.velz.tennismatch.enums.EPlayer;
import org.velz.tennismatch.enums.Points;
import org.velz.tennismatch.model.GameScore;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.MatchScore;
import org.velz.tennismatch.service.MatchScoreCalculationService;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScoreTest {
    private MatchScore matchScore;
    private Match match;
    private MatchScoreCalculationService matchScoreCalculationService;

    @BeforeEach
    public void prepare() {
        this.matchScoreCalculationService = new MatchScoreCalculationService();
        this.matchScore = new MatchScore();
        this.match = new Match();
    }

    @Test
    @DisplayName("Tie Break is exists when games score is 6-6")
    public void checkTieBreak() {
        makeTieBreak();

        assertTrue(matchScore.isTieBreak());
    }

    private void makeTieBreak() {
        int[] games = matchScore.getGames();
        games[EPlayer.PLAYER1.getIndexPlayer()] = 5;
        games[EPlayer.PLAYER2.getIndexPlayer()] = 5;

        for (int i = 0; i < 4; i++) {
            matchScore.increaseScore(EPlayer.PLAYER1);

        }
        for (int i = 0; i < 4; i++) {

            matchScore.increaseScore(EPlayer.PLAYER2);
        }

    }

    @Test
    @DisplayName("player 1 win set when tieBreak ")
    public void player1WinSetWhenTieBreak() {
        makeTieBreak();
        for (int i = 0; i < 7; i++) {
            matchScore.increaseScore(EPlayer.PLAYER1);
        }
        int[] sets = matchScore.getSets();
        assertEquals(1, sets[EPlayer.PLAYER1.getIndexPlayer()]);
    }

    @Test
    @DisplayName("player 2 win set when tieBreak ")
    public void player2WinSetWhenTieBreak() {
        makeTieBreak();
        for (int i = 0; i < 7; i++) {
            matchScore.increaseScore(EPlayer.PLAYER2);
        }
        int[] sets = matchScore.getSets();
        assertEquals(1, sets[EPlayer.PLAYER2.getIndexPlayer()]);
    }
    @Test
    @DisplayName("player 1 get point and didn't win set when tieBreak and tieBreakScore is 6-6")
    public void player1WinSetWhenTieBreakAndSpecificScore() {
        makeTieBreak();
        matchScore.getGameScore().setPlayer1TieBreakPoints(6);
        matchScore.getGameScore().setPlayer2TieBreakPoints(6);


        matchScore.increaseScore(EPlayer.PLAYER1);

        int[] sets = matchScore.getSets();
        assertEquals(0, sets[EPlayer.PLAYER1.getIndexPlayer()]);
    }
//    @Test
//    @DisplayName("player1 is winner when he gets last point before 2 sets")
//    public void player1IsWinner() {
//        int[] sets = matchScore.getSets();
//        int[] games = matchScore.getGames();
//
//        sets[EPlayer.PLAYER1.getIndexPlayer()] = 1;
//        games[EPlayer.PLAYER1.getIndexPlayer()] = 5;
//
//        matchScoreCalculationService.updateScore(match,EPlayer.PLAYER1.name());
//        matchScoreCalculationService.updateScore(match,EPlayer.PLAYER1.name());
//        matchScoreCalculationService.updateScore(match,EPlayer.PLAYER1.name());
//        matchScoreCalculationService.updateScore(match,EPlayer.PLAYER1.name());
//
//
//        assertNotNull(match.getWinner());
//    }


}
