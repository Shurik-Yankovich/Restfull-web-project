package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Token;
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
}
