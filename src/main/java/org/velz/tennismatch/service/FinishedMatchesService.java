package org.velz.tennismatch.service;

import lombok.AllArgsConstructor;
import org.velz.tennismatch.dao.MatchDao;
import org.velz.tennismatch.dto.MatchDto;
import org.velz.tennismatch.mapper.MatchDtoMapper;
import org.velz.tennismatch.model.Match;

import java.util.List;


@AllArgsConstructor
public class FinishedMatchesService {

    private MatchDao matchDao;
    private MatchDtoMapper matchDtoMapper;


    public void persist(MatchDto matchDto) {
        Match match = matchDtoMapper.mapFromDtoToMatch(matchDto);
        matchDao.save(match);
    }


    public List<MatchDto> getFinishedMatches(int pageNumber, String playerName, int pageSize) {

        int offset = (pageNumber - 1) * pageSize;
        if (playerName == null) {
            return matchDao.findAllPaginated(offset, pageSize).stream().map(matchDtoMapper::mapFromMatchToDto).toList();
        }
        return matchDao.findByPlayerNamePaginated(offset, pageSize, playerName).stream().map(matchDtoMapper::mapFromMatchToDto).toList();

    }
}
