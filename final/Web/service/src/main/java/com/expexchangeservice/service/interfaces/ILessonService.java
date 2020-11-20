package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Review;
import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.model.exception.DBException;

import java.time.LocalDate;
import java.util.List;

public interface ILessonService {

    void addLesson(Lesson lesson) throws DBException;
    void changeLesson(Lesson lesson) throws DBException;
    void deleteLesson(Integer lessonId) throws DBException;
    Lesson getLessonById(Integer lessonId) throws DBException;
    List<Lesson> getAll() throws DBException;
    List<Lesson> getLessonsOnTheDate(LocalDate date) throws DBException;
    List<Lesson> getLessonsAfterDate(LocalDate date) throws DBException;
    List<Lesson> getLessonsForMember(UserProfile member) throws DBException;
    List<Lesson> getLessonsOnTheTheme(Theme theme) throws DBException;
    List<Lesson> getLessonsForTheProfessor(UserProfile professor) throws DBException;
    List<Lesson> getLessonsByType(Type lessonType) throws DBException;
    void addMemberToTheLesson(Integer lessonId, UserProfile userProfile) throws DBException;
    void addReview(Integer lessonId, Review review) throws DBException;
}