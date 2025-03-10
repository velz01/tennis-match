package org.velz.tennismatch.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.Player;
import org.velz.tennismatch.util.HibernateUtil;

import java.util.List;

public class MatchDao {
private final SessionFactory sessionFactory;

    public MatchDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Match findByName(int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Match match = session.get(Match.class, id);
            session.getTransaction().commit();
            return match;
        }
    }

    public Match save(Match match) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(match);
            session.getTransaction().commit();
            return match;
        }
    }

    public List<Match> findAllPaginated(int offset, int pageSize) {


        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Match> matches = session.createQuery("FROM Match", Match.class)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize)
                    .list();
            session.getTransaction().commit();
            return matches;
        }

    }

    public List<Match> findByPlayerNamePaginated(int offset, int pageSize, String playerName) {

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            String nameForQuery = "%" + playerName + "%";
            List<Match> matches = session.createQuery("FROM Match where player1.name ILIKE :name or player2.name ILIKE :name", Match.class)
                    .setParameter("name", nameForQuery)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize)
                    .list();
            session.getTransaction().commit();
            return matches;
        }
    }
}
