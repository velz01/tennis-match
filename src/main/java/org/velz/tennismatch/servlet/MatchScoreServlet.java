package org.velz.tennismatch.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.ongoingMatchesService = (OngoingMatchesService)config.getServletContext().getAttribute("ongoingMatchesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        Match match = ongoingMatchesService.get(uuid);

        req.setAttribute("player1", match.getPlayer1().getName());
        req.setAttribute("player2", match.getPlayer2().getName());
        req.getRequestDispatcher("match-score.jsp").forward(req,resp);
    }
}
