package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import com.expexchangeservice.utils.HibernateSessionFactoryUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepository extends AbstractRepository<Course> implements ICourseRepository {

    @Override
    public List<Course> findByQuery(String hqlQuery) {
        return HibernateSessionFactoryUtil.getSession().createQuery(hqlQuery).list();
    }
}
