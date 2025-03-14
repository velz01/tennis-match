package org.velz.tennismatch.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.velz.tennismatch.enums.EPlayer;
import org.velz.tennismatch.enums.Points;

@ToString
@Getter
public class GameScore {
    public static final int MIN_TIEBREAK_POINTS = 7;
    public static final int MIN_LEAD = 2;

    @Setter
    private int player1TieBreakPoints;
    @Setter
    private int player2TieBreakPoints;


    private Points[] points;

    private boolean isDeuce;

    private EPlayer advantagePlayer;



    private EPlayer winner;

    public GameScore() {
        resetGame();
    }


    public void updatePoints(EPlayer player) {

        if (isDeuce) {
            handleDeuce(player);
        } else {
            processPoints(player);
        }



    }

    private void processPoints(EPlayer player) {
        if (getScorePlayer(player) != Points.FORTY) {
            increasePoints(player);
        } else if (!isDeuce) {
            declareWinner(player);
        }


    }

    private void handleDeuce(EPlayer currentPlayer) {

        if (advantagePlayer == null) {
            advantagePlayer = currentPlayer;
        } else if (advantagePlayer == currentPlayer) {
            declareWinner(currentPlayer);
            advantagePlayer = null;
        } else {
            advantagePlayer = null;
        }

    }

    private void increasePoints(EPlayer player) {
        int indexPlayer = player.getIndexPlayer();
        points[indexPlayer] = points[indexPlayer].next();

        if (checkDeuce()) {
            isDeuce = true;
        }
    }



    private void declareWinner(EPlayer player) {
        winner = player;
    }

    private boolean checkDeuce() {
        return getScorePlayer1() == Points.FORTY && getScorePlayer2() == Points.FORTY;
    }


    public Points getScorePlayer(EPlayer player) {
        return points[player.getIndexPlayer()];
    }

    public Points getScorePlayer2() {
        return points[EPlayer.PLAYER2.getIndexPlayer()];
    }

    public Points getScorePlayer1() {
        return points[EPlayer.PLAYER1.getIndexPlayer()];
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
        this.winner = null;
        this.isDeuce = false;
        this.points = new Points[]{Points.ZERO, Points.ZERO};
    }

    public boolean winnerExists() {
        return winner != null;
    }

    public void updateTieBreakPoints(EPlayer player) {
        if (player == EPlayer.PLAYER1) {
            player1TieBreakPoints++;
        }

        if (player == EPlayer.PLAYER2) {
            player2TieBreakPoints++;
        }

        if ((player1TieBreakPoints >= MIN_TIEBREAK_POINTS || player2TieBreakPoints >= MIN_TIEBREAK_POINTS)
                && Math.abs(player1TieBreakPoints - player2TieBreakPoints) >= MIN_LEAD) {
            declareWinner(player);
        }
    }
}

