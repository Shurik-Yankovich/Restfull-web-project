package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRepository extends AbstractRepository<User> implements IUserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findByUsername(String username) {
            Query query = sessionFactory.getCurrentSession()
            .createQuery("select u from " + User.class.getName() + " u where u.username = " + username)
            .setMaxResults(1);
        User entity = (User) query.uniqueResult();
        return entity;
    }
}
