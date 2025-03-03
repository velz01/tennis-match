package org.velz.tennismatch.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.Player;
import org.velz.tennismatch.util.HibernateUtil;

public class MatchDao {

    public Match findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Match match = session.get(Match.class, name);
            session.getTransaction().commit();
            return match;
        }
    }
}
