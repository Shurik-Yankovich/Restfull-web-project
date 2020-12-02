package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Token;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.repository.interfaces.ITokenRepository;
import com.expexchangeservice.utils.hibernate.HibernateSessionFactoryUtil;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository extends AbstractRepository<Token> implements ITokenRepository {
    @Override
    public Token findByValue(String value) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Token u where u.value =:key");
        query.setParameter("key", value);
        return (Token) query.uniqueResult();
    }

    @Override
    public void deleteByUser(User user) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("delete from Token u where u.user =:user");
        query.setParameter("user", user);
        query.executeUpdate();
    }
}
