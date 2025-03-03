package org.velz.tennismatch.dto;

import lombok.Builder;

@Builder
public record NewMatchDto(String player1, String player2) {
}
