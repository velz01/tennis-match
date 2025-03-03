package org.velz.tennismatch.mapper;

import org.velz.tennismatch.dto.NewMatchDto;

public class NewMatchDtoMapper {
    public NewMatchDto mapFromStringsToDto(String namePlayer1, String namePlayer2) {
        return NewMatchDto.builder()
                .player1(namePlayer1)
                .player2(namePlayer2)
                .build();
    }
}
