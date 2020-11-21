package com.expexchangeservice.controller.config;

import com.expexchangeservice.model.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ApplicationConfig {
//    @Bean
//    public SessionFactory sessionFactory() {
//        SessionFactory sessionFactory;
//        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
//        configuration.addAnnotatedClass(Course.class);
//        configuration.addAnnotatedClass(Lesson.class);
//        configuration.addAnnotatedClass(Review.class);
//        configuration.addAnnotatedClass(Section.class);
//        configuration.addAnnotatedClass(Theme.class);
//        configuration.addAnnotatedClass(User.class);
//        configuration.addAnnotatedClass(UserProfile.class);
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//        sessionFactory = configuration.buildSessionFactory(builder.build());
//        return sessionFactory;
//    }

}
