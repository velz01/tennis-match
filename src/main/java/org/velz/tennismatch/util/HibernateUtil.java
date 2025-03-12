package org.velz.tennismatch.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.velz.tennismatch.exception.DatabaseException;
import org.velz.tennismatch.exception.ResourceNotFoundException;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.Player;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@UtilityClass
public class HibernateUtil {
    private static final String Config_Hibernate = "hibernate.cfg.xml";
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();


    private SessionFactory buildSessionFactory() {

        return new Configuration()
                .configure(Config_Hibernate)
                .addAnnotatedClass(Match.class)
                .addAnnotatedClass(Player.class)
                .buildSessionFactory();

    }

    public static void initDatabase()  {
        try (InputStream resource = HibernateUtil.class.getClassLoader().getResourceAsStream("data.sql")) {
            byte[] bytes = Objects.requireNonNull(resource).readAllBytes();
            String sql = new String(bytes);
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            throw new DatabaseException("Database error");
        }
        catch (IOException exception) {
            throw new ResourceNotFoundException("sql data is not found");
        }
    }
}
