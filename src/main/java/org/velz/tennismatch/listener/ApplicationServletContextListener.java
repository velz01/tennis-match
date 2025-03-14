package org.velz.tennismatch.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import org.velz.tennismatch.dao.MatchDao;
import org.velz.tennismatch.dao.PlayerDao;
import org.velz.tennismatch.mapper.MatchDtoMapper;
import org.velz.tennismatch.service.FinishedMatchesService;
import org.velz.tennismatch.service.MatchScoreCalculationService;
import org.velz.tennismatch.service.NewMatchService;
import org.velz.tennismatch.service.OngoingMatchesService;
import org.velz.tennismatch.util.HibernateUtil;

@WebListener
public class ApplicationServletContextListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent event) {
        HibernateUtil.initDatabase();
        ServletContext servletContext = event.getServletContext();

        MatchDao matchDao = new MatchDao();
        PlayerDao playerDao = new PlayerDao();
        MatchDtoMapper matchDtoMapper = new MatchDtoMapper();

        servletContext.setAttribute("matchDao", matchDao);
        servletContext.setAttribute("playerDao", playerDao);
        servletContext.setAttribute("matchDtoMapper", matchDtoMapper);
        servletContext.setAttribute("newMatchService", new NewMatchService(playerDao, matchDtoMapper));
        servletContext.setAttribute("matchScoreCalculationService", new MatchScoreCalculationService());
        servletContext.setAttribute("finishedMatchesService", new FinishedMatchesService(matchDao, matchDtoMapper));
        servletContext.setAttribute("ongoingMatchesService", new OngoingMatchesService());
    }

}
