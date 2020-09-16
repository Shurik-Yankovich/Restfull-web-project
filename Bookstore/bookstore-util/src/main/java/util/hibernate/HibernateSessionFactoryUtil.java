package util.hibernate;

import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static SessionFactory SESSION_FACTORY;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Book.class);
            configuration.addAnnotatedClass(Bookshelf.class);
            configuration.addAnnotatedClass(Request.class);
            configuration.addAnnotatedClass(Customer.class);
            configuration.addAnnotatedClass(Order.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            SESSION_FACTORY = configuration.buildSessionFactory(builder.build());
        } catch (Exception e) {
            System.out.println("Исключение!" + e);
        }
    }

    private HibernateSessionFactoryUtil() {}

    public static Session getSession() {
        return SESSION_FACTORY.openSession();
    }
}
