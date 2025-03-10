package org.velz.tennismatch.model;

import lombok.Getter;
import lombok.ToString;
import org.velz.tennismatch.enums.EPlayer;


@Getter
@ToString
public class MatchScore {

    private int[] games;
    private int[] sets;

    private final GameScore gameScore;

    public MatchScore() {
        this.gameScore = new GameScore();
        this.games = new int[2];
        this.sets = new int[2];
    }

    public void increaseScore(EPlayer player) {
        gameScore.increasePoints(player);

        if (gameScore.checkWinner()) { //gameisOver?
            games[player.getIndexPlayer()]++;
        }
        if (setIsOver()) {  //increaseSetsifNeeds ?
            sets[player.getIndexPlayer()]++;
            resetGames();
        }

    }

    private void resetGames() {
        this.games = new int[2];
    }

    private boolean setIsOver() {
        return getGamesPlayer1() == 6 || getGamesPlayer2() == 6;
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

    public boolean matchIsFinished() {
        return sets[EPlayer.PLAYER1.getIndexPlayer()] == 2 || sets[EPlayer.PLAYER2.getIndexPlayer()] == 2;
    }
}
