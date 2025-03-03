package org.velz.tennismatch.mapper;

import org.velz.tennismatch.dto.MatchDto;
import org.velz.tennismatch.model.Match;

public class MatchMapper {
    public Match mapFromDtoToMatch(MatchDto matchDto) {
        return Match.builder()
                .player1(matchDto.player1())
                .player2(matchDto.player2())
                .matchScore(matchDto.matchScore())
                .build();
    }
}
