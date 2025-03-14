package org.velz.tennismatch.service;


import lombok.AllArgsConstructor;
import org.velz.tennismatch.enums.EPlayer;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.MatchScore;

@AllArgsConstructor
public class MatchScoreCalculationService {


    public void updateScore(Match match, String player) {
        EPlayer ePlayerValue = getEPlayerValue(player);
        MatchScore matchScore = match.getMatchScore();
        matchScore.updateScore(ePlayerValue);

        if (matchScore.matchIsFinished()) {
            match.setWinner(ePlayerValue == EPlayer.PLAYER1 ? match.getPlayer1() : match.getPlayer2());
        }

    }


    private static EPlayer getEPlayerValue(String player) {
        return EPlayer.valueOf(player.toUpperCase());
    }
}
