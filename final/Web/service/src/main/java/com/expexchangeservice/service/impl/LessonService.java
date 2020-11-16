package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.repository.interfaces.ILessonRepository;
import com.expexchangeservice.service.interfaces.ILessonService;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LessonService implements ILessonService {

    private ILessonRepository lessonRepository;
    private SessionFactory sessionFactory;

    @Autowired
    public LessonService(ILessonRepository lessonRepository, SessionFactory sessionFactory) {
        this.lessonRepository = lessonRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addLesson(Lesson lesson) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            lesson = lessonRepository.create(lesson);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось записать объект класса " + Lesson.class.getName() + " в базу данных!");
        }
    }

    @Override
    public void changeLesson(Lesson lesson) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            lessonRepository.update(lesson);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось внести изменения в объект класса " + Lesson.class.getName() + " в базе данных!");
        }
    }

    @Override
    public void deleteLesson(Integer lessonId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            lessonRepository.delete(lessonId);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось удалить объект класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public Lesson getLessonById(Integer lessonId) throws DBException {
        Transaction transaction = null;
        Lesson lesson = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            lesson = lessonRepository.read(lessonId);
            transaction.commit();
            return lesson;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось удалить объект класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Lesson> getAll() throws DBException {
        Transaction transaction = null;
        List<Lesson> lessons = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            lessons = lessonRepository.readAll();
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Lesson> getLessonsOnTheDate(LocalDate date) throws DBException {
        String hqlQuery = "select u from " + Lesson.class.getName() + " u where u.date = " + date;
        return getLessonsByQuery(hqlQuery);
    }

    @Override
    public List<Lesson> getLessonsAfterDate(LocalDate date) throws DBException {
        String hqlQuery = "";
        return getLessonsByQuery(hqlQuery);
    }

    @Override
    public List<Lesson> getLessonsForMember(UserProfile member) throws DBException {
        String hqlQuery = "";
        return getLessonsByQuery(hqlQuery);
    }

    @Override
    public List<Lesson> getLessonsOnTheTheme(Theme theme) throws DBException {
        String hqlQuery = "select u from " + Lesson.class.getName() + " u where u.theme = " + theme;
        return getLessonsByQuery(hqlQuery);
    }

    @Override
    public List<Lesson> getLessonsForTheProfessor(UserProfile professor) throws DBException {
        String hqlQuery = "select u from " + Lesson.class.getName() + " u where u.professor = " + professor;
        return getLessonsByQuery(hqlQuery);
    }

    @Override
    public List<Lesson> getLessonsByType(Type lessonType) throws DBException {
        String hqlQuery = "select u from " + Lesson.class.getName() + " u where u.type = " + lessonType;
        return getLessonsByQuery(hqlQuery);
    }

    private List<Lesson> getLessonsByQuery(String query) throws DBException {
        Transaction transaction = null;
        List<Lesson> lessons = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            lessons = lessonRepository.findByQuery(query);
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось обработать запрос " + query + " в базе данных!");
        }
    }

    @Override
    public void addMemberToTheLesson(Integer lessonId, UserProfile userProfile) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            Lesson lesson = lessonRepository.read(lessonId);
//            lesson.getReviews().add();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    public void addReview(Integer lessonId, Review review) throws DBException {
        Transaction transaction = null;
        try {
            transaction = sessionFactory.getCurrentSession().beginTransaction();
            Lesson lesson = lessonRepository.read(lessonId);
            lesson.getReviews().add(review);
            lessonRepository.update(lesson);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }
}
