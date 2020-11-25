package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.repository.interfaces.IUserProfileRepository;
import com.expexchangeservice.utils.hibernate.HibernateSessionFactoryUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class UserProfileRepository extends AbstractRepository<UserProfile> implements IUserProfileRepository {
    @Override
    public UserProfile findByUser(User user) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from UserProfile u where u.user =:user");
        query.setParameter("user", user);
        return (UserProfile) query.getSingleResult();
    }
}
