package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.Course;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.model.enums.Type;

import java.time.LocalDate;
import java.util.Set;

public interface ICourseService {

    void addCourse(Course course);
    void editCourse(Course course);
    void deleteCourse(Integer primaryKey);
    Set<Course> getAll();
    Set<Course> getCoursesOnTheDate(LocalDate date);
    Set<Course> getCoursesAfterDate(LocalDate date);
    Set<Course> getCoursesForMember(UserProfile member);
    Set<Course> getCoursesOnTheTheme(Section section);
    Set<Course> getCoursesForTheProfessor(UserProfile professor);
    Set<Course> getCoursesByType(Type lessonType);
    void addMemberToTheCourse(Lesson lesson, UserProfile userProfile);
}
