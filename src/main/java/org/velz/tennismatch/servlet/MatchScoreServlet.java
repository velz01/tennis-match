package org.velz.tennismatch.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.velz.tennismatch.dto.MatchDto;
import org.velz.tennismatch.exception.BadRequestException;
import org.velz.tennismatch.exception.ResourceNotFoundException;
import org.velz.tennismatch.mapper.MatchDtoMapper;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.service.FinishedMatchesService;
import org.velz.tennismatch.service.MatchScoreCalculationService;
import org.velz.tennismatch.service.OngoingMatchesService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends BaseServlet {
    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesService finishedMatchesService;
    private MatchDtoMapper matchDtoMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        this.matchScoreCalculationService = (MatchScoreCalculationService) config.getServletContext().getAttribute("matchScoreCalculationService");
        this.finishedMatchesService = (FinishedMatchesService) config.getServletContext().getAttribute("finishedMatchesService");
        this.matchDtoMapper = (MatchDtoMapper) config.getServletContext().getAttribute("matchDtoMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        if (uuid.toString().isEmpty()) {
            throw new BadRequestException("uuid is empty");
        }

        Match match = getMatch(uuid);

        if (match.getWinner() != null) {
            ongoingMatchesService.delete(uuid);
        }

        req.setAttribute("match", match);
        req.setAttribute("uuid", uuid);
        req.setAttribute("matchScore", match.getMatchScore());
        req.getRequestDispatcher("match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        String player = req.getParameter("player");

        Match match = getMatch(uuid);
        matchScoreCalculationService.updateScore(match, player);

        if (match.getWinner() != null) {
            MatchDto matchDto = matchDtoMapper.mapFromMatchToDto(match);
             finishedMatchesService.persist(matchDto);
        }

        resp.sendRedirect("/match-score" + "?uuid=" + uuid);
    }

    private Match getMatch(UUID uuid) {
        Optional<Match> matchOptional = ongoingMatchesService.get(uuid);
        if (matchOptional.isEmpty()) {
            throw new ResourceNotFoundException("Match is not found");

        }
        return matchOptional.get();
    }
}
