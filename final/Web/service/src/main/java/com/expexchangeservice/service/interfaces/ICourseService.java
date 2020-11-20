package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.*;
import com.expexchangeservice.model.enums.Type;
import com.expexchangeservice.model.exception.DBException;

import java.time.LocalDate;
import java.util.List;

public interface ICourseService {

    void addCourse(Course course) throws DBException;
    void editCourse(Course course) throws DBException;
    void deleteCourse(Integer courseId) throws DBException;
    Course getCourseById(Integer courseId) throws DBException;
    List<Course> getAll() throws DBException;
    List<Course> getCoursesOnTheDate(LocalDate date) throws DBException;
    List<Course> getCoursesAfterDate(LocalDate date) throws DBException;
    List<Course> getCoursesForMember(UserProfile member) throws DBException;
    List<Course> getCoursesOnTheSection(Section section) throws DBException;
    List<Course> getCoursesForTheProfessor(UserProfile professor) throws DBException;
    List<Course> getCoursesByType(Type lessonType) throws DBException;
    void addMemberToTheCourse(Integer courseId, UserProfile userProfile) throws DBException;
    void addReview(Integer courseId, Review review) throws DBException;
}
