package org.velz.tennismatch.servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.velz.tennismatch.dto.MatchDto;
import org.velz.tennismatch.exception.BadRequestException;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.service.FinishedMatchesService;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class FinishedMatchesServlet extends BaseServlet {
    public static final int PAGE_SIZE_BY_DEFAULT = 5;

    private FinishedMatchesService finishedMatchesService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.finishedMatchesService = (FinishedMatchesService) config.getServletContext().getAttribute("finishedMatchesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");

        int pageNumber;
        try {
            pageNumber = (page == null) ? 1 : Integer.parseInt(page);
        } catch (NumberFormatException exception) {
            throw new BadRequestException("Empty page");
        }

        String playerName = req.getParameter("filter_by_player_name");
        List<MatchDto> finishedMatches = finishedMatchesService.getFinishedMatches(pageNumber, playerName, PAGE_SIZE_BY_DEFAULT);

        if (finishedMatches.isEmpty()) {
            req.setAttribute("error", "This page doesn't exist, please go to the first page.");
        }

        req.setAttribute("matches", finishedMatches);
        req.setAttribute("pageNumber", pageNumber);
        req.setAttribute("filterName", playerName);


        req.setAttribute("pageSize", PAGE_SIZE_BY_DEFAULT);
        req.getRequestDispatcher("matches.jsp").forward(req, resp);
    }
}
