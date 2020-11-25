package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.repository.interfaces.ILessonRepository;
import com.expexchangeservice.utils.hibernate.HibernateSessionFactoryUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
public class LessonRepository extends AbstractRepository<Lesson> implements ILessonRepository {

    @Override
    public List<Lesson> findByDate(LocalDate date) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Lesson u where u.date =:date");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Lesson> findAfterDate(LocalDate date) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Lesson u where u.date >:date");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Lesson> findByTheme(Theme theme) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Lesson u where u.theme =:theme");
        query.setParameter("theme", theme);
        return query.getResultList();
    }

    @Override
    public List<Lesson> findByType(Type type) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Lesson u where u.type =:type");
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    public List<Lesson> findByProfessor(UserProfile professor) {
        Query query = HibernateSessionFactoryUtil.getSession()
                .createQuery("select u from Lesson u where u.professor =:profile");
        query.setParameter("profile", professor);
        return query.getResultList();
    }
}
