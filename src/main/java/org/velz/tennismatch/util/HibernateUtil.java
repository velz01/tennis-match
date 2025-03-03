package org.velz.tennismatch.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.velz.tennismatch.model.Match;
import org.velz.tennismatch.model.Player;

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

}
