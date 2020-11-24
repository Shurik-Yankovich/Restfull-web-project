package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.model.exception.DBException;
import com.expexchangeservice.repository.interfaces.ILessonRepository;
import com.expexchangeservice.service.interfaces.ILessonService;
import com.expexchangeservice.service.interfaces.IReviewService;
import com.expexchangeservice.utils.HibernateSessionFactoryUtil;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LessonService implements ILessonService {

    private ILessonRepository lessonRepository;
    private IReviewService reviewService;

    @Autowired
    public LessonService(ILessonRepository lessonRepository, IReviewService reviewService) {
        this.lessonRepository = lessonRepository;
        this.reviewService = reviewService;
    }

    @Override
    public void addLesson(LessonDto lessonDto) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Lesson lesson = convertDtoToLesson(new Lesson(), lessonDto);
            lesson.setPrice(100);
            lessonRepository.create(lesson);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось записать объект класса " + Lesson.class.getName() + " в базу данных!");
        }
    }

    @Override
    public void changeLesson(int lessonId, LessonDto lessonDto) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Lesson lesson = lessonRepository.read(lessonId);
            lesson = convertDtoToLesson(lesson,lessonDto);
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
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Lesson lesson = lessonRepository.read(lessonId);
            lessonRepository.delete(lesson);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось удалить объект класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public Lesson getLessonById(Integer lessonId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Lesson lesson = lessonRepository.read(lessonId);
            transaction.commit();
            return lesson;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объект класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Lesson> getAll() throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            List<Lesson> lessons = lessonRepository.readAll();
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Lesson> getLessonsOnTheDate(LocalDate date) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            List<Lesson> lessons = lessonRepository.findByDate(date);
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Lesson> getLessonsAfterDate(LocalDate date) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            List<Lesson> lessons = lessonRepository.findAfterDate(date);
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Lesson> getLessonsOnTheTheme(Theme theme) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            List<Lesson> lessons = lessonRepository.findByTheme(theme);
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Lesson> getLessonsForTheProfessor(UserProfile professor) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            List<Lesson> lessons = lessonRepository.findByUserProfile(professor);
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

    @Override
    public List<Lesson> getLessonsByType(Type lessonType) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            List<Lesson> lessons = lessonRepository.findByType(lessonType);
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось получить объекты класса " + Lesson.class.getName() + " из базы данных!");
        }
    }

//    private List<Lesson> getLessonsByQuery(String query) throws DBException {
//        Transaction transaction = null;
//        List<Lesson> lessons = null;
//        try {
//            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
//            lessons = lessonRepository.findByQuery(query);
//            transaction.commit();
//            return lessons;
//        } catch (Exception e) {
//            transaction.rollback();
//            throw new DBException("Не удалось обработать запрос " + query + " в базе данных!");
//        }
//    }

//    @Override
//    public void addMemberToTheLesson(Integer lessonId, UserProfile userProfile) throws DBException {
//        Transaction transaction = null;
//        try {
//            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
//            Lesson lesson = lessonRepository.read(lessonId);
//            lesson.getMembers().add(userProfile);
//            lessonRepository.update(lesson);
//            transaction.commit();
//        } catch (Exception e) {
//            transaction.rollback();
//            throw new DBException("Не удалось добавить участника к " + Lesson.class.getName() + " в базе данных!");
//        }
//    }

    @Override
    public void addReview(Integer lessonId, Review review) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            reviewService.addReview(review);
            Lesson lesson = lessonRepository.read(lessonId);
            if (lesson.getReviews() == null) {
                lesson.setReviews(new HashSet<Review>());
            }
            lesson.getReviews().add(review);
            lessonRepository.update(lesson);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
            throw new DBException("Не удалось добавить отзыв на " + Lesson.class.getName() + " в базе данных!");
        }
    }

    @Override
    public Set<Review> getReviewOnTheLesson(Integer lessonId) throws DBException {
        Transaction transaction = null;
        try {
            transaction = HibernateSessionFactoryUtil.getSession().beginTransaction();
            Lesson lesson = lessonRepository.read(lessonId);
            transaction.commit();
            return lesson.getReviews();
        } catch (Exception e) {
            transaction.rollback();
            throw new DBException("Не удалось добавить отзыв на " + Lesson.class.getName() + " в базе данных!");
        }
    }

    private Lesson convertDtoToLesson(Lesson lesson, LessonDto lessonDto) {
        lesson.setId(lessonDto.getId());
        lesson.setTheme(lessonDto.getTheme());
        lesson.setProfessor(lessonDto.getProfessor());
        lesson.setType(lessonDto.getType());
        lesson.setDate(lessonDto.getDate());
        return lesson;
    }
}
