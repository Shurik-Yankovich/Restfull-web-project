package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepository extends AbstractRepository<Course> implements ICourseRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Course> findByQuery(String hqlQuery) {
        return sessionFactory.getCurrentSession().createQuery(hqlQuery).list();
    }
}
