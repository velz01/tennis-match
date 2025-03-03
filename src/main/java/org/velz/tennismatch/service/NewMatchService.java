package org.velz.tennismatch.service;

import lombok.AllArgsConstructor;
import org.velz.tennismatch.dao.MatchDao;
import org.velz.tennismatch.dao.PlayerDao;
import org.velz.tennismatch.dto.MatchDto;
import org.velz.tennismatch.dto.NewMatchDto;
import org.velz.tennismatch.mapper.MatchDtoMapper;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.MatchScore;
import org.velz.tennismatch.model.Player;

import java.util.Optional;

@AllArgsConstructor
public class NewMatchService {
    private PlayerDao playerDao;
    private MatchDtoMapper matchDtoMapper;

    public MatchDto createNewMatch(NewMatchDto newMatchDto) {
        Optional<Player> optPlayer1 = playerDao.findByName(newMatchDto.player1());
        Optional<Player> optPlayer2 = playerDao.findByName(newMatchDto.player2());
        Player player1;
        Player player2;

        if (optPlayer1.isEmpty()) {
            player1 = Player.builder().name(newMatchDto.player1()).build();
            playerDao.save(player1);
        } else {
            player1 = optPlayer1.get();
        }

        if (optPlayer2.isEmpty()) {
            player2 = Player.builder().name(newMatchDto.player2()).build();
            playerDao.save(player2);
        }else {
            player2 = optPlayer2.get();
        }
        return matchDtoMapper.mapFromPlayersToDto(player1, player2);
    }

}
