package org.velz.tennismatch.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.velz.tennismatch.exception.DatabaseException;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.Player;
import org.velz.tennismatch.util.HibernateUtil;

import java.util.Optional;

public class PlayerDao {
    private final SessionFactory sessionFactory;

    public PlayerDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Optional<Player> findByName(String playerName) {
        String hql = "From Player where name = :playerName";
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Player> player = session.createQuery(hql, Player.class).setParameter("playerName", playerName).uniqueResultOptional();
            session.getTransaction().commit();
            return player;
        } catch (HibernateException exception) {
            throw new DatabaseException("Database error");
        }
    }

    public void save(Player player) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();

        } catch (HibernateException exception) {
            throw new DatabaseException("Database error");
        }
    }
}
