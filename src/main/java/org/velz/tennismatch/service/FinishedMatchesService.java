package org.velz.tennismatch.service;

import lombok.AllArgsConstructor;
import org.velz.tennismatch.dao.MatchDao;
import org.velz.tennismatch.model.Match;

import java.util.List;


@AllArgsConstructor
public class FinishedMatchesService {

    private MatchDao matchDao;


    public void persist(Match match) {
        matchDao.save(match);
    }


    public List<Match> getFinishedMatches(int pageNumber, String playerName, int pageSize) {

        int offset = (pageNumber - 1) * pageSize;
        if (playerName == null) {
            return matchDao.findAllPaginated(offset, pageSize);
        }
        return matchDao.findByPlayerNamePaginated(offset, pageSize, playerName);

    }
}
