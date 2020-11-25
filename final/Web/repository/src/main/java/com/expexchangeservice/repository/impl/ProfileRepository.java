package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.repository.interfaces.IProfileRepository;
import com.expexchangeservice.utils.HibernateSessionFactoryUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class ProfileRepository extends AbstractRepository<UserProfile> implements IProfileRepository {
    @Override
    public UserProfile findByUser(User user) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from UserProfile u where u.user =:user");
        query.setParameter("user", user);
        return (UserProfile) query.getSingleResult();
    }
}
