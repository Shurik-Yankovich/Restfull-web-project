package com.expexchangeservice.utils.hibernate;

import com.expexchangeservice.model.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateSessionFactoryUtil {

    @Autowired
    private static SessionFactory SESSION_FACTORY;
    private static Session SESSION;

    static {
        try {
            Configuration configuration = new Configuration().configure();
//            configuration.addPackage("com.expexchangeservice.model");
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(UserProfile.class);
            configuration.addAnnotatedClass(Theme.class);
            configuration.addAnnotatedClass(Section.class);
            configuration.addAnnotatedClass(Review.class);
            configuration.addAnnotatedClass(Lesson.class);
            configuration.addAnnotatedClass(Course.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            SESSION_FACTORY = configuration.buildSessionFactory(builder.build());
            SESSION = SESSION_FACTORY.openSession();
        } catch (Exception e) {
            System.out.println("Исключение!" + e);
        }
    }

    private HibernateSessionFactoryUtil() {
    }

    public static Session getSession() {
//        return SESSION_FACTORY.getCurrentSession();
        return SESSION;
    }
}
