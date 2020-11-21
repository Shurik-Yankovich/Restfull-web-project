package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.repository.interfaces.ILessonRepository;
import com.expexchangeservice.utils.HibernateSessionFactoryUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LessonRepository extends AbstractRepository<Lesson> implements ILessonRepository {

    @Override
    public List<Lesson> findByQuery(String hqlQuery) {
        return HibernateSessionFactoryUtil.getSession().createQuery(hqlQuery).list();
    }
}
