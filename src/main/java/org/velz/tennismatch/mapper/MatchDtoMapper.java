package org.velz.tennismatch.mapper;

import org.velz.tennismatch.dto.MatchDto;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.MatchScore;
import org.velz.tennismatch.model.Player;

public class MatchDtoMapper {
    public MatchDto mapFromMatchToDto(Match match) {
        return MatchDto.builder()
                .id(match.getId())
                .player1(match.getPlayer1())
                .player2(match.getPlayer2())
                .winner(match.getWinner())
                .matchScore(match.getMatchScore())
                .build();
    }
    public MatchDto mapFromPlayersToDto(Player player1, Player player2) {
        return MatchDto.builder()
                .player1(player1)
                .player2(player2)
                .matchScore(new MatchScore()) //???
                .build();
    }
}
