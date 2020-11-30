package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.repository.interfaces.IUserRepository;
import com.expexchangeservice.utils.hibernate.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserRepository extends AbstractRepository<User> implements IUserRepository {

    @Override
    public User findByUsername(String username) {
//        Session session = HibernateSessionFactoryUtil.getSession();
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<User> cr = cb.createQuery(User.class);
//        Root<User> root = cr.from(User.class);
//        cr.select(root).where(cb.equal(root.get("username"), username));
//
//        Query<User> query = session.createQuery(cr);
//        return query.getSingleResult();
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from User u where u.username =:name");
        query.setParameter("name", username);
//        Query query = HibernateSessionFactoryUtil.getSession()
//                .createQuery("select u from " + User.class.getName() + " u where u.username = " + username)
//            .setMaxResults(1);
        return (User) query.uniqueResult();
    }
}
