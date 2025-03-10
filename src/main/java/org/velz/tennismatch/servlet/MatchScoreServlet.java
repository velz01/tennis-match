package org.velz.tennismatch.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.velz.tennismatch.exception.MatchNotFoundException;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.service.FinishedMatchesService;
import org.velz.tennismatch.service.MatchScoreCalculationService;
import org.velz.tennismatch.service.OngoingMatchesService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesService finishedMatchesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        this.matchScoreCalculationService = (MatchScoreCalculationService) config.getServletContext().getAttribute("matchScoreCalculationService");
        this.finishedMatchesService = (FinishedMatchesService) config.getServletContext().getAttribute("finishedMatchesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        Match match = getMatch(uuid);

        if (match.getWinner() != null) {
            ongoingMatchesService.delete(uuid);
        }
        //TODO: при обновлении страницы уже удаленого матча сделать редирект на /matches (возможно подписать что матч уже был удален и необходимо выйти или редирект)
        //в catch написать что такого матча не существует возможно он был уже удален и предложить редирект
        req.setAttribute("player1", match.getPlayer1().getName());
        req.setAttribute("player2", match.getPlayer2().getName());
        req.setAttribute("winner", match.getWinner());
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

            finishedMatchesService.persist(match);
        }

        resp.sendRedirect("/match-score" + "?uuid=" + uuid);
    }

    private Match getMatch(UUID uuid) {
        Optional<Match> matchOptional = ongoingMatchesService.get(uuid);
        if (matchOptional.isEmpty()) {
            throw new MatchNotFoundException();
        }
        return matchOptional.get();
    }
}
