package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.repository.interfaces.ICourseRepository;
import com.expexchangeservice.service.interfaces.ICourseService;
import com.expexchangeservice.utils.HibernateSessionFactoryUtil;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService implements ICourseService {

    private ICourseRepository courseRepository;

    @Autowired
    public CourseService(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void addCourse(Course course) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            courseRepository.create(course);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось записать объект класса " + Course.class.getName() + " в базу данных!");
        }
    }

    @Override
    public void editCourse(Course course) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            courseRepository.update(course);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось внести изменения в объект класса " + Course.class.getName() + " в базе данных!");
        }
    }

    @Override
    public void deleteCourse(Integer courseId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            courseRepository.delete(courseId);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось удалить объект класса " + Course.class.getName() + " из базы данных!");
        }
    }

    @Override
    public Course getCourseById(Integer courseId) throws DBException {
        Transaction transaction = null;
        Course course = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            course = courseRepository.read(courseId);
            transaction.commit();
            return course;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объект класса " + Course.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Course> getAll() throws DBException {
        Transaction transaction = null;
        List<Course> courses = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            courses = courseRepository.readAll();
            transaction.commit();
            return courses;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Course.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Course> getCoursesOnTheDate(LocalDate date) throws DBException {
        String hqlQuery = "select u from " + Course.class.getName() + " u where u.date = " + date;
        return getCoursesByQuery(hqlQuery);
    }

    @Override
    public List<Course> getCoursesAfterDate(LocalDate date) throws DBException {
        String hqlQuery = "";
        return getCoursesByQuery(hqlQuery);
    }

    @Override
    public List<Course> getCoursesForMember(UserProfile member) throws DBException {
        String hqlQuery = "";
        return getCoursesByQuery(hqlQuery);
    }

    @Override
    public List<Course> getCoursesOnTheSection(Section section) throws DBException {
        String hqlQuery = "select u from " + Course.class.getName() + " u where u.theme = " + section;
        return getCoursesByQuery(hqlQuery);
    }

    @Override
    public List<Course> getCoursesForTheProfessor(UserProfile professor) throws DBException {
        String hqlQuery = "select u from " + Course.class.getName() + " u where u.professor = " + professor;
        return getCoursesByQuery(hqlQuery);
    }

    @Override
    public List<Course> getCoursesByType(Type lessonType) throws DBException {
        String hqlQuery = "select u from " + Course.class.getName() + " u where u.type = " + lessonType;
        return getCoursesByQuery(hqlQuery);
    }

    private List<Course> getCoursesByQuery(String query) throws DBException {
        Transaction transaction = null;
        List<Course> courses = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            courses = courseRepository.findByQuery(query);
            transaction.commit();
            return courses;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось обработать запрос " + query + " в базе данных!");
        }
    }

    @Override
    public void addMemberToTheCourse(Integer courseId, UserProfile userProfile) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Course course = courseRepository.read(courseId);
            course.getMembers().add(userProfile);
            courseRepository.update(course);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось добавить участника к " + Course.class.getName() + " в базе данных!");
        }
    }

    @Override
    public void addReview(Integer courseId, Review review) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Course course = courseRepository.read(courseId);
            course.getReviews().add(review);
            courseRepository.update(course);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось добавить отзыв на " + Course.class.getName() + " в базе данных!");
        }
    }
}
