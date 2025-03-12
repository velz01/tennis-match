package org.velz.tennismatch.service;

import lombok.AllArgsConstructor;
import org.velz.tennismatch.dao.MatchDao;
import org.velz.tennismatch.dao.PlayerDao;
import org.velz.tennismatch.dto.MatchDto;

import org.velz.tennismatch.mapper.MatchDtoMapper;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.MatchScore;
import org.velz.tennismatch.model.Player;

import java.util.Optional;

@AllArgsConstructor
public class NewMatchService {
    private PlayerDao playerDao;
    private MatchDtoMapper matchDtoMapper;

    public MatchDto createNewMatch(String playerOne, String playerTwo) {
        Optional<Player> optPlayer1 = playerDao.findByName(playerOne);
        Optional<Player> optPlayer2 = playerDao.findByName(playerTwo);
        Player player1;
        Player player2;

        if (optPlayer1.isEmpty()) {
            player1 = Player.builder().name(playerOne).build();
            playerDao.save(player1);
        } else {
            player1 = optPlayer1.get();
        }

        if (optPlayer2.isEmpty()) {
            player2 = Player.builder().name(playerTwo).build();
            playerDao.save(player2);
        }else {
            player2 = optPlayer2.get();
        }
        return matchDtoMapper.mapFromPlayersToDto(player1, player2);
    }

}
