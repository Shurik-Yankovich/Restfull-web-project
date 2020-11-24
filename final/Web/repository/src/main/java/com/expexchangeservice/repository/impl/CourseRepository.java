package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import com.expexchangeservice.utils.HibernateSessionFactoryUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CourseRepository extends AbstractRepository<Course> implements ICourseRepository {

    @Override
    public List<Course> findByDate(LocalDate date) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Course u where u.dateStart =:date");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Course> findAfterDate(LocalDate date) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Course u where u.dateStart >:date");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Course> findBySection(Section section) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Course u where u.section =:section");
        query.setParameter("section", section);
        return query.getResultList();
    }

    @Override
    public List<Course> findByType(Type type) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Course u where u.type =:type");
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    public List<Course> findByProfessor(UserProfile professor) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Course u where u.professor =:profile");
        query.setParameter("profile", professor);
        return query.getResultList();
    }
}
