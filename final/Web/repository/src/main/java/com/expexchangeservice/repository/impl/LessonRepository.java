package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.repository.interfaces.ILessonRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LessonRepository extends AbstractRepository<Lesson> implements ILessonRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Lesson> findByQuery(String hqlQuery) {
        return sessionFactory.getCurrentSession().createQuery(hqlQuery).list();
    }
}
