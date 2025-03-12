package org.velz.tennismatch.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.velz.tennismatch.enums.EPlayer;
import org.velz.tennismatch.enums.Points;

import java.util.Objects;

@ToString
@Getter
public class GameScore {
    private static final int INDEX_PLAYER1 = 0;
    private static final int INDEX_PLAYER2 = 1;
    private static final String FIRST_PLAYER = "player1";
    private static final String SECOND_PLAYER = "player2";

    @Setter
    private int player1TieBreakPoints;
    @Setter
    private int player2TieBreakPoints;


    private Points[] score;

    private boolean isTie;

    private boolean isAdvantagePlayer1;

    private boolean isAdvantagePlayer2;

    private String winner;

    public GameScore() {
        resetGame();
    }


    public void increasePoints(EPlayer player) {
        if (isTie) {
            handleTie(player);
        }

        if (player == EPlayer.PLAYER1) {
            if (getScorePlayer1() != Points.FORTY) {
                score[INDEX_PLAYER1] = score[INDEX_PLAYER1].next();
            } else if (!isTie) {
                winner = FIRST_PLAYER;
            }
        }

        if (player == EPlayer.PLAYER2) {
            if (getScorePlayer2() != Points.FORTY) {
                score[INDEX_PLAYER2] = score[INDEX_PLAYER2].next();
            } else if (!isTie) {
                winner = SECOND_PLAYER;
            }
        }

        if (checkTie()) {
            isTie = true;

        }

    }

    private void handleTie(EPlayer player) {
        if (player == EPlayer.PLAYER1) {
            if (isAdvantagePlayer2) { //TODO: Вынести в отдельный метод
                isAdvantagePlayer2 = false;
            } else if (isAdvantagePlayer1) {
                winner = FIRST_PLAYER;
            } else {
                isAdvantagePlayer1 = true;
            }

        }
        if (player == EPlayer.PLAYER2) {
            if (isAdvantagePlayer1) {
                isAdvantagePlayer1 = false;
            } else if (isAdvantagePlayer2) {
                winner = SECOND_PLAYER;
            } else {
                isAdvantagePlayer2 = true;
            }
        }

//        if (isAdvantagePlayer1) {
//            winner = FIRST_PLAYER;
//        }
//
//        if (isAdvantagePlayer2) {
//            winner = SECOND_PLAYER;
//        }
    }


    private boolean checkTie() {
        return getScorePlayer1() == Points.FORTY && getScorePlayer2() == Points.FORTY;
    }


    public Points getScorePlayer1() {
        return score[INDEX_PLAYER1];
    }

    public Points getScorePlayer2() {
        return score[INDEX_PLAYER2];
    }

    public boolean checkWinner() {
        if (winnerExists()) {
            resetGame();
            return true;
        }
        return false;
    }

    private void resetGame() {
        this.player1TieBreakPoints = 0;
        this.player2TieBreakPoints = 0;
        this.winner = "";
        this.isAdvantagePlayer1 = false;
        this.isAdvantagePlayer2 = false;
        this.isTie = false;
        this.score = new Points[]{Points.ZERO, Points.ZERO};
    }

    public boolean winnerExists() {
        return !winner.isEmpty();
    }

    public void increaseTieBreakPoints(EPlayer player) {
        if (player == EPlayer.PLAYER1) {
            player1TieBreakPoints++;
        }

        if (player == EPlayer.PLAYER2) {
            player2TieBreakPoints++;
        }

        if ((player1TieBreakPoints >= 7 || player2TieBreakPoints >= 7)
                && Math.abs(player1TieBreakPoints - player2TieBreakPoints) >= 2) {
            winner = player.toString();
        }
    }
}

