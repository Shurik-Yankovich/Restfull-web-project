package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.utils.HibernateSessionFactoryUtil;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository<User> implements IUserRepository {

    @Override
    public User findByUsername(String username) {
            Query query = HibernateSessionFactoryUtil.getSession()
            .createQuery("select u from " + User.class.getName() + " u where u.username = " + username)
            .setMaxResults(1);
        User entity = (User) query.uniqueResult();
        return entity;
    }
}
