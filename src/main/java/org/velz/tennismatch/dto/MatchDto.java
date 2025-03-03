package org.velz.tennismatch.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import org.velz.tennismatch.model.MatchScore;
import org.velz.tennismatch.model.Player;

@Builder
public record MatchDto(Long id, Player player1, Player player2, Player winner, MatchScore matchScore){

}
