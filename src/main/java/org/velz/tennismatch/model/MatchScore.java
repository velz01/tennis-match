package org.velz.tennismatch.model;

import lombok.Getter;
import lombok.ToString;
import org.velz.tennismatch.enums.EPlayer;


@Getter
@ToString
public class MatchScore {

    private int[] games;
    private int[] sets;

    private boolean isTieBreak;
    private final GameScore gameScore;

    public MatchScore() {
        this.gameScore = new GameScore();
        this.games = new int[2];
        this.sets = new int[2];
        this.isTieBreak = false;
    }

    public void updateScore(EPlayer player) {

        if (isTieBreak) {
            gameScore.updateTieBreakPoints(player);
        } else {
            gameScore.updatePoints(player);
        }

        if (gameScore.checkWinner()) {
            incrementGames(player);
        }

        if (checkTieBreak()) {
            this.isTieBreak = true;
        }

        if (isSetOver()) {
            completeSet(player);
        }
    }

    private void incrementGames(EPlayer player) {
        games[player.getIndexPlayer()]++;
    }

    private void completeSet(EPlayer player) {
        sets[player.getIndexPlayer()]++;
        resetGames();
    }


    private boolean checkTieBreak() {
        return getGamesPlayer1() == 6 && getGamesPlayer2() == 6;
    }

    private void resetGames() {
        this.games = new int[2];
        this.isTieBreak = false;
    }

    private boolean isSetOver() {
        return ((Math.abs(getGamesPlayer1() - getGamesPlayer2()) >= 2) && (getGamesPlayer1() == 6 || getGamesPlayer2() == 6))
                || (getGamesPlayer1() == 7 || getGamesPlayer2() == 7);
    }

    public int getGamesPlayer1() {
        return games[EPlayer.PLAYER1.getIndexPlayer()];
    }
    public int getGamesPlayer2() {
        return games[EPlayer.PLAYER2.getIndexPlayer()];
    }
    public int getSetsPlayer1() {
        return sets[EPlayer.PLAYER1.getIndexPlayer()];
    }
    public int getSetsPlayer2() {
        return sets[EPlayer.PLAYER2.getIndexPlayer()];
    }
    public int getPointsPlayer1() {
        return gameScore.getScorePlayer1().getPoints();
    }
    public int getPointsPlayer2() {
        return gameScore.getScorePlayer2().getPoints();
    }
    public int getTieBreakPointsPlayer1() {
        return gameScore.getPlayer1TieBreakPoints();
    }
    public int getTieBreakPointsPlayer2() {
        return gameScore.getPlayer2TieBreakPoints();
    }

    public boolean matchIsFinished() {
        return sets[EPlayer.PLAYER1.getIndexPlayer()] == 2 || sets[EPlayer.PLAYER2.getIndexPlayer()] == 2;
    }
}
