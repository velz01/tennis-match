package org.velz.tennismatch.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.velz.tennismatch.dto.MatchDto;
import org.velz.tennismatch.dto.NewMatchDto;
import org.velz.tennismatch.mapper.MatchDtoMapper;
import org.velz.tennismatch.mapper.NewMatchDtoMapper;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.service.NewMatchService;
import org.velz.tennismatch.service.OngoingMatchesService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends BaseServlet {
    private NewMatchService newMatchService;
    private NewMatchDtoMapper newMatchDtoMapper;
    private OngoingMatchesService ongoingMatchesService;
    private MatchDtoMapper matchDtoMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.newMatchService = (NewMatchService) config.getServletContext().getAttribute("newMatchService");
        this.newMatchDtoMapper = (NewMatchDtoMapper) config.getServletContext().getAttribute("newMatchDtoMapper");
        this.ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        this.matchDtoMapper = (MatchDtoMapper) config.getServletContext().getAttribute("matchDtoMapper");
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
        NewMatchDto newMatchDto = newMatchDtoMapper.mapFromPlayersNamesToDto(playerOne, playerTwo);
        MatchDto matchDto = newMatchService.createNewMatch(newMatchDto);
        Match match = matchDtoMapper.mapFromDtoToMatch(matchDto);
        UUID uuid = ongoingMatchesService.add(match);
        List<String> errors = validatePlayersNames(playerOne, playerTwo);


        req.setAttribute("errors", errors);
        if (!errors.isEmpty()) {
            req.getRequestDispatcher("new-match.jsp").forward(req, resp);
        } else {
            String redirectUrl = "/match-score" + "?uuid=" + uuid; //TODO: Refactor
            resp.sendRedirect(redirectUrl);
        }


    }

    private List<String> validatePlayersNames(String playerOne, String playerTwo) {
        List<String> errors = new ArrayList<>();

        if (playerOne.isEmpty() && playerTwo.isEmpty()) {
            errors.add("Players names should be not empty");

        }
        if (!playerOne.matches("[а-яА-яёЁa-zA-Z ]+") || !playerTwo.matches("[а-яА-ЯёЁa-zA-Z ]+")) {
            errors.add("Players names should be only letters");
        }
        if (playerOne.equalsIgnoreCase(playerTwo)) {
            errors.add("Players names should be unique");
        }
        if (playerOne.length() > 20 || playerTwo.length() > 20) {
            errors.add("Players names length should be <=20 letters");
        }
        return errors;
    }
}
