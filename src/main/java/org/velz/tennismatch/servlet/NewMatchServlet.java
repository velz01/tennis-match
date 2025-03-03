package org.velz.tennismatch.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.velz.tennismatch.dto.MatchDto;
import org.velz.tennismatch.dto.NewMatchDto;
import org.velz.tennismatch.mapper.MatchMapper;
import org.velz.tennismatch.mapper.NewMatchDtoMapper;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.service.NewMatchService;
import org.velz.tennismatch.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private NewMatchService newMatchService;
    private NewMatchDtoMapper newMatchDtoMapper;
    private OngoingMatchesService ongoingMatchesService;
    private MatchMapper matchMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.newMatchService = (NewMatchService) config.getServletContext().getAttribute("newMatchService");
        this.newMatchDtoMapper = (NewMatchDtoMapper) config.getServletContext().getAttribute("newMatchDtoMapper");
        this.ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        this.matchMapper = (MatchMapper) config.getServletContext().getAttribute("matchMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("new-match.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerOne = req.getParameter("playerOne");
        String playerTwo = req.getParameter("playerTwo");
        //TODO: Validation for players
        NewMatchDto newMatchDto = newMatchDtoMapper.mapFromStringsToDto(playerOne, playerTwo);
        MatchDto matchDto = newMatchService.createNewMatch(newMatchDto);
        Match match = matchMapper.mapFromDtoToMatch(matchDto);
        UUID uuid = ongoingMatchesService.add(match);

        String redirectUrl = "/match-score" + "?uuid=" + uuid; //TODO: Refactor
        resp.sendRedirect(redirectUrl);

    }
}
