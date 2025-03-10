package org.velz.tennismatch.model;

import lombok.Getter;
import lombok.ToString;
import org.velz.tennismatch.enums.EPlayer;
import org.velz.tennismatch.enums.Points;

import java.util.Objects;
@ToString
public class GameScore {
    private static final int INDEX_PLAYER1 = 0;
    private static final int INDEX_PLAYER2 = 1;
    private static final String FIRST_PLAYER = "player1";
    private static final String SECOND_PLAYER = "player2";

    private Points[] score;
    @Getter
    private boolean isTie;
    @Getter
    private boolean isAdvantagePlayer1;
    @Getter
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
            if (getScorePlayer1() != Points.FORTY ) {
                score[INDEX_PLAYER1] = score[INDEX_PLAYER1].next();
            } else if ( !isTie) {
                winner = FIRST_PLAYER;
            }
        }

        if (player == EPlayer.PLAYER2) {
            if (getScorePlayer2() != Points.FORTY) {
                score[INDEX_PLAYER2] = score[INDEX_PLAYER2].next();
            } else if ( !isTie){
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
            }
            else {
                isAdvantagePlayer1 = true;
            }

        }
        if (player == EPlayer.PLAYER2) {
            if (isAdvantagePlayer1) {
                isAdvantagePlayer1 = false;
            } else if (isAdvantagePlayer2) {
                winner = SECOND_PLAYER;
            }
            else {
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
        this.winner = "";
        this.isAdvantagePlayer1 = false;
        this.isAdvantagePlayer2 = false;
        this.isTie = false;
        this.score = new Points[]{Points.ZERO, Points.ZERO};
    }

    public boolean winnerExists() {
        return !winner.isEmpty();
    }

}

