package org.velz.tennismatch.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.velz.tennismatch.dao.MatchDao;
import org.velz.tennismatch.dao.PlayerDao;
import org.velz.tennismatch.mapper.MatchDtoMapper;
import org.velz.tennismatch.mapper.MatchMapper;
import org.velz.tennismatch.mapper.NewMatchDtoMapper;
import org.velz.tennismatch.service.FinishedMatchesPersistenceService;
import org.velz.tennismatch.service.MatchScoreCalculationService;
import org.velz.tennismatch.service.NewMatchService;
import org.velz.tennismatch.service.OngoingMatchesService;

@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();

        MatchDao matchDao = new MatchDao();
        PlayerDao playerDao = new PlayerDao();
        servletContext.setAttribute("matchDao", matchDao);
        servletContext.setAttribute("playerDao", playerDao);
        MatchDtoMapper matchDtoMapper = new MatchDtoMapper();
        servletContext.setAttribute("matchDtoMapper", matchDtoMapper);

        servletContext.setAttribute("newMatchService", new NewMatchService(playerDao, matchDtoMapper));
        servletContext.setAttribute("matchScoreCalculationService", new MatchScoreCalculationService());
        servletContext.setAttribute("finishedMatchesPersistenceService", new FinishedMatchesPersistenceService());
        servletContext.setAttribute("ongoingMatchesService", new OngoingMatchesService());
        servletContext.setAttribute("newMatchDtoMapper", new NewMatchDtoMapper());
        servletContext.setAttribute("matchMapper", new MatchMapper());



    }

}
