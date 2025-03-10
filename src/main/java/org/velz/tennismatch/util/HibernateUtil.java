package org.velz.tennismatch.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
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

    public static void initDatabase() throws IOException { //временно, потом в catch пробрасывать runtime
        try (InputStream resource = HibernateUtil.class.getClassLoader().getResourceAsStream("data.sql")) {
            byte[] bytes = Objects.requireNonNull(resource).readAllBytes();
            String sql = new String(bytes);
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
