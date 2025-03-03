package org.velz.tennismatch.dao;

import org.hibernate.Session;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.Player;
import org.velz.tennismatch.util.HibernateUtil;

import java.util.Optional;

public class PlayerDao {


    public Optional<Player> findByName(String playerName) {
        String hql = "From Player where name = :playerName";
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Optional<Player> player = session.createQuery(hql, Player.class ).setParameter("playerName", playerName).uniqueResultOptional();
            session.getTransaction().commit();
            return player;
        }
    }

    public Optional<Player> save(Player player) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();
            return Optional.ofNullable(player);
        }
    }
}
